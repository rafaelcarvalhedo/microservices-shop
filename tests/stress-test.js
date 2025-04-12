import http from 'k6/http';
import { check, sleep } from 'k6';
import { randomIntBetween } from 'https://jslib.k6.io/k6-utils/1.2.0/index.js';

export const options = {
  stages: [
    { duration: '1m', target: 10 },  // Ramp-up para 10 usuários
    { duration: '3m', target: 10 },  // Mantém 10 usuários por 3 minutos
    { duration: '1m', target: 20 },  // Aumenta para 20 usuários
    { duration: '3m', target: 20 },  // Mantém 20 usuários por 3 minutos
    { duration: '1m', target: 0 },   // Ramp-down
  ],
  thresholds: {
    http_req_duration: ['p(95)<500'], // 95% das requisições devem completar em menos de 500ms
    http_req_failed: ['rate<0.01'],   // Taxa de falha deve ser menor que 1%
  },
};

const BASE_URL = 'http://localhost:8080';
const PEDIDOS_URL = BASE_URL + '/api/pedidos';
const PAGAMENTOS_URL = BASE_URL + '/api/pagamentos';
const ENVIOS_URL = BASE_URL + '/api/envios';

export default function () {
  // Cenário 1: Fluxo completo de sucesso
  const pedido = criarPedido();
  const pedidoId = pedido.id;
  
  // Aguarda processamento do pedido
  sleep(1);
  
  // Processa pagamento
  processarPagamento(pedidoId, 'APROVADO');
  
  // Aguarda processamento do pagamento
  sleep(1);
  
  // Inicia preparação do envio
  iniciarEnvio(pedidoId);
  
  // Aguarda preparação
  sleep(1);
  
  // Inicia transporte
  iniciarTransporte(pedidoId);
  
  // Aguarda transporte
  sleep(1);
  
  // Finaliza entrega
  finalizarEntrega(pedidoId);
  
  // Cenário 2: Pagamento rejeitado
  const pedido2 = criarPedido();
  processarPagamento(pedido2.id, 'REJEITADO');
  
  // Cenário 3: Cancelamento durante preparação
  const pedido3 = criarPedido();
  processarPagamento(pedido3.id, 'APROVADO');
  iniciarEnvio(pedido3.id);
  cancelarEnvio(pedido3.id);
  
  // Cenário 4: Cancelamento durante transporte
  const pedido4 = criarPedido();
  processarPagamento(pedido4.id, 'APROVADO');
  iniciarEnvio(pedido4.id);
  iniciarTransporte(pedido4.id);
  cancelarEnvio(pedido4.id);
  
  // Verifica consistência dos estados
  verificarEstados(pedidoId);
  verificarEstados(pedido2.id);
  verificarEstados(pedido3.id);
  verificarEstados(pedido4.id);
}

function criarPedido() {
  const payload = {
    clienteId: randomIntBetween(1, 1000),
    itens: [
      {
        produtoId: randomIntBetween(1, 100),
        quantidade: randomIntBetween(1, 5),
        valorUnitario: randomIntBetween(10, 100)
      }
    ],
    dataCriacao: new Date().toISOString()
  };

  const response = http.post(PEDIDOS_URL, JSON.stringify(payload), {
    headers: { 'Content-Type': 'application/json' },
  });

  check(response, {
    'criação do pedido status 201': (r) => r.status === 201,
    'pedido criado com status PENDENTE': (r) => JSON.parse(r.body).status === 'PENDENTE',
  });

  return JSON.parse(response.body);
}

function processarPagamento(pedidoId, status) {
  const payload = {
    pedidoId: pedidoId,
    status: status,
    metodoPagamento: 'CARTAO_CREDITO',
    dataProcessamento: new Date().toISOString()
  };

  const response = http.post(PAGAMENTOS_URL, JSON.stringify(payload), {
    headers: { 'Content-Type': 'application/json' },
  });

  check(response, {
    'processamento de pagamento status 200': (r) => r.status === 200,
    'pagamento processado': (r) => JSON.parse(r.body).status === status,
  });
}

function iniciarEnvio(pedidoId) {
  const payload = {
    pedidoId: pedidoId,
    status: 'EM_PREPARACAO',
    dataInicioPreparacao: new Date().toISOString()
  };

  const response = http.post(ENVIOS_URL, JSON.stringify(payload), {
    headers: { 'Content-Type': 'application/json' },
  });

  check(response, {
    'início de envio status 200': (r) => r.status === 200,
    'envio em preparação': (r) => JSON.parse(r.body).status === 'EM_PREPARACAO',
  });
}

function iniciarTransporte(pedidoId) {
  const payload = {
    pedidoId: pedidoId,
    status: 'EM_TRANSITO',
    dataInicioTransporte: new Date().toISOString()
  };

  const response = http.put(ENVIOS_URL + '/' + pedidoId, JSON.stringify(payload), {
    headers: { 'Content-Type': 'application/json' },
  });

  check(response, {
    'início de transporte status 200': (r) => r.status === 200,
    'envio em trânsito': (r) => JSON.parse(r.body).status === 'EM_TRANSITO',
  });
}

function finalizarEntrega(pedidoId) {
  const payload = {
    pedidoId: pedidoId,
    status: 'ENTREGUE',
    dataEntrega: new Date().toISOString()
  };

  const response = http.put(ENVIOS_URL + '/' + pedidoId, JSON.stringify(payload), {
    headers: { 'Content-Type': 'application/json' },
  });

  check(response, {
    'finalização de entrega status 200': (r) => r.status === 200,
    'envio entregue': (r) => JSON.parse(r.body).status === 'ENTREGUE',
  });
}

function cancelarEnvio(pedidoId) {
  const payload = {
    pedidoId: pedidoId,
    status: 'CANCELADO',
    motivo: 'CANCELAMENTO_SOLICITADO',
    dataCancelamento: new Date().toISOString()
  };

  const response = http.put(ENVIOS_URL + '/' + pedidoId, JSON.stringify(payload), {
    headers: { 'Content-Type': 'application/json' },
  });

  check(response, {
    'cancelamento de envio status 200': (r) => r.status === 200,
    'envio cancelado': (r) => JSON.parse(r.body).status === 'CANCELADO',
  });
}

function verificarEstados(pedidoId) {
  const response = http.get(PEDIDOS_URL + '/' + pedidoId);

  check(response, {
    'consulta de pedido status 200': (r) => r.status === 200,
    'estados consistentes': (r) => {
      const pedido = JSON.parse(r.body);
      const statusPagamento = pedido.statusPagamento;
      const statusEnvio = pedido.statusEnvio;
      const status = pedido.status;

      // Regras de consistência
      if (status === 'CANCELADO') {
        return statusPagamento === 'REJEITADO' || statusEnvio === 'CANCELADO';
      }
      if (status === 'PENDENTE') {
        return statusPagamento === 'PENDENTE' && statusEnvio === 'PENDENTE';
      }
      if (status === 'PAGO') {
        return statusPagamento === 'APROVADO' && statusEnvio === 'PENDENTE';
      }
      if (status === 'EM_PREPARACAO') {
        return statusPagamento === 'APROVADO' && statusEnvio === 'EM_PREPARACAO';
      }
      if (status === 'EM_TRANSITO') {
        return statusPagamento === 'APROVADO' && statusEnvio === 'EM_TRANSITO';
      }
      if (status === 'ENTREGUE') {
        return statusPagamento === 'APROVADO' && statusEnvio === 'ENTREGUE';
      }
      return false;
    },
  });
} 
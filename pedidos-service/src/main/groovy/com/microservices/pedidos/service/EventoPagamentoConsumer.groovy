package com.microservices.pedidos.service

import com.microservices.pedidos.model.Pedido
import com.microservices.pedidos.repository.PedidoRepository
import com.microservices.pagamento.model.EventoPagamento
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class EventoPagamentoConsumer {

    @Autowired
    PedidoRepository pedidoRepository

    @KafkaListener(topics = "pagamentos", groupId = "pedidos-group")
    @Transactional
    void consumirEventoPagamento(EventoPagamento evento) {
        Pedido pedido = pedidoRepository.findById(evento.pedidoId)
                .orElseThrow { new RuntimeException("Pedido não encontrado: ${evento.pedidoId}") }

        pedido.statusPagamento = evento.status
        pedido.ultimoPagamentoId = evento.id

        // Atualizar status do pedido baseado no status do pagamento
        if (evento.status == 'APROVADO') {
            pedido.status = 'PAGO'
        } else if (evento.status == 'REJEITADO') {
            pedido.status = 'CANCELADO'
        }

        pedidoRepository.save(pedido)
    }
} 
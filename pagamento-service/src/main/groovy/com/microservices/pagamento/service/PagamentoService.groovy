package com.microservices.pagamento.service

import EventoPagamento
import com.microservices.pagamento.model.Pagamento
import com.microservices.pagamento.repository.PagamentoRepository
import groovy.transform.CompileStatic
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@CompileStatic
class PagamentoService {

    @Autowired
    PagamentoRepository pagamentoRepository

    @Autowired
    KafkaTemplate<String, EventoPagamento> kafkaTemplate

    @Transactional
    Pagamento criarPagamento(Pagamento pagamento) {
        Pagamento pagamentoSalvo = pagamentoRepository.save(pagamento)
        enviarEventoPagamento(pagamentoSalvo)
        pagamentoSalvo
    }

    Pagamento buscarPagamentoPorId(Long id) {
        pagamentoRepository.findById(id)
                .orElseThrow { new RuntimeException("Pagamento não encontrado com ID: $id") }
    }

    Pagamento buscarPagamentoPorPedido(Long pedidoId) {
        pagamentoRepository.findByPedidoId(pedidoId)
                .orElseThrow { new RuntimeException("Pagamento não encontrado para o pedido: $pedidoId") }
    }

    @Transactional
    Pagamento atualizarStatusPagamento(Long id, String novoStatus) {
        Pagamento pagamento = buscarPagamentoPorId(id)
        pagamento.status = novoStatus
        Pagamento pagamentoAtualizado = pagamentoRepository.save(pagamento)
        enviarEventoPagamento(pagamentoAtualizado)
        pagamentoAtualizado
    }

    private void enviarEventoPagamento(Pagamento pagamento) {
        EventoPagamento evento = new EventoPagamento(
                id: pagamento.id,
                pedidoId: pagamento.pedidoId,
                valor: pagamento.valor,
                status: pagamento.status,
                dataCriacao: pagamento.dataCriacao,
                metodoPagamento: pagamento.metodoPagamento
        )
        kafkaTemplate.send("pagamentos", evento)
    }
} 
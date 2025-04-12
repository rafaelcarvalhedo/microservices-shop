package com.microservices.pedidos.service

import com.microservices.commoms.kafka.evento.EventoEnvio
import com.microservices.pedidos.model.Pedido
import com.microservices.pedidos.repository.PedidoRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class EventoEnvioConsumer {

    @Autowired
    PedidoRepository pedidoRepository

    @KafkaListener(topics = "envios", groupId = "pedidos-group")
    @Transactional
    void consumirEventoEnvio(EventoEnvio evento) {
        Pedido pedido = pedidoRepository.findById(evento.pedidoId)
                .orElseThrow { new RuntimeException("Pedido não encontrado: ${evento.pedidoId}") }

        pedido.statusEnvio = evento.status
        pedido.ultimoEnvioId = evento.id
        pedido.codigoRastreamento = evento.codigoRastreamento

        // Atualizar status do pedido baseado no status do envio
        if (evento.status == 'ENTREGUE') {
            pedido.status = 'FINALIZADO'
        } else if (evento.status == 'CANCELADO') {
            pedido.status = 'CANCELADO'
        }

        pedidoRepository.save(pedido)
    }
} 
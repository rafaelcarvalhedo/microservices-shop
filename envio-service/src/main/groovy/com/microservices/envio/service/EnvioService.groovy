package com.microservices.envio.service

import com.microservices.commoms.kafka.evento.EventoEnvio
import com.microservices.envio.model.Envio

import com.microservices.envio.repository.EnvioRepository
import groovy.transform.CompileStatic
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@CompileStatic
class EnvioService {

    @Autowired
    EnvioRepository envioRepository

    @Autowired
    KafkaTemplate<String, EventoEnvio> kafkaTemplate

    @Transactional
    Envio criarEnvio(Envio envio) {
        Envio envioSalvo = envioRepository.save(envio)
        enviarEventoEnvio(envioSalvo)
        envioSalvo
    }

    Envio buscarEnvioPorId(Long id) {
        envioRepository.findById(id)
                .orElseThrow { new RuntimeException("Envio não encontrado com ID: $id") }
    }

    Envio buscarEnvioPorPedido(Long pedidoId) {
        envioRepository.findByPedidoId(pedidoId)
                .orElseThrow { new RuntimeException("Envio não encontrado para o pedido: $pedidoId") }
    }

    @Transactional
    Envio atualizarStatusEnvio(Long id, String novoStatus) {
        Envio envio = buscarEnvioPorId(id)
        envio.status = novoStatus
        Envio envioAtualizado = envioRepository.save(envio)
        enviarEventoEnvio(envioAtualizado)
        envioAtualizado
    }

    @Transactional
    Envio atualizarRastreamento(Long id, String codigoRastreamento) {
        Envio envio = buscarEnvioPorId(id)
        envio.codigoRastreamento = codigoRastreamento
        Envio envioAtualizado = envioRepository.save(envio)
        enviarEventoEnvio(envioAtualizado)
        envioAtualizado
    }

    private void enviarEventoEnvio(Envio envio) {
        EventoEnvio evento = new EventoEnvio(
                id: envio.id,
                pedidoId: envio.pedidoId,
                status: envio.status,
                codigoRastreamento: envio.codigoRastreamento,
                dataCriacao: envio.dataCriacao,
                dataEntregaPrevista: envio.dataEntregaPrevista,
                transportadora: envio.transportadora
        )
        kafkaTemplate.send("envios", evento)
    }
} 
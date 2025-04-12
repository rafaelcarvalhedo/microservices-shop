package com.microservices.pedidos.model

import groovy.transform.CompileStatic

import java.time.LocalDateTime

@CompileStatic
class EventoEnvio {
    Long id
    Long pedidoId
    String status
    String codigoRastreamento
    LocalDateTime dataCriacao
    LocalDateTime dataEntregaPrevista
    String transportadora
} 
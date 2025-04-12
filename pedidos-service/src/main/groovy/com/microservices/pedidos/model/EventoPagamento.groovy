package com.microservices.pedidos.model

import groovy.transform.CompileStatic

import java.time.LocalDateTime

@CompileStatic
class EventoPagamento {
    Long id
    Long pedidoId
    BigDecimal valor
    String status
    LocalDateTime dataCriacao
    String metodoPagamento
} 
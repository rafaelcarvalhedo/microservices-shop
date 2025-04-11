package com.microservices.pedidos.model

import groovy.transform.CompileStatic

@CompileStatic
class EventoPagamento {
    Long id
    Long pedidoId
    String status
    String metodoPagamento
} 
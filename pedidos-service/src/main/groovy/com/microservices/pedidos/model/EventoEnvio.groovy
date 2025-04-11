package com.microservices.pedidos.model

import groovy.transform.CompileStatic

@CompileStatic
class EventoEnvio {
    Long id
    Long pedidoId
    String status
    String codigoRastreamento
} 
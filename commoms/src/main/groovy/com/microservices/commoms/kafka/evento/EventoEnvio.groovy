package com.microservices.commoms.kafka.evento

import java.time.LocalDateTime

class EventoEnvio {
    Long id
    Long pedidoId
    String status
    String codigoRastreamento
    LocalDateTime dataCriacao
    LocalDateTime dataEntregaPrevista
    String transportadora
} 
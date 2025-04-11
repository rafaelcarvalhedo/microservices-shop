package com.microservices.envio.model

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
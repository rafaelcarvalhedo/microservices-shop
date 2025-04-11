package com.microservices.pagamento.model

import java.time.LocalDateTime

class EventoPagamento {
    Long id
    Long pedidoId
    BigDecimal valor
    String status
    LocalDateTime dataCriacao
    String metodoPagamento
} 
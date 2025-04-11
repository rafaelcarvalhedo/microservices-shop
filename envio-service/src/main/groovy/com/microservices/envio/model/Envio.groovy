package com.microservices.envio.model

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "envios")
class Envio {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id

    @Column(nullable = false)
    Long pedidoId

    @Column(nullable = false)
    String status

    @Column(nullable = false)
    String enderecoEntrega

    @Column
    String codigoRastreamento

    @Column
    String transportadora

    @Column(nullable = false)
    LocalDateTime dataCriacao

    @Column
    LocalDateTime dataAtualizacao

    @Column
    LocalDateTime dataEntregaPrevista

    @Column
    LocalDateTime dataEntregaRealizada

    @PrePersist
    void prePersist() {
        dataCriacao = LocalDateTime.now()
        if (status == null) {
            status = 'PENDENTE'
        }
    }

    @PreUpdate
    void preUpdate() {
        dataAtualizacao = LocalDateTime.now()
    }
} 
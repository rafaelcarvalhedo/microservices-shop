package com.microservices.envio.model

import groovy.transform.CompileStatic
import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "envios")
@CompileStatic
class Envio {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
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
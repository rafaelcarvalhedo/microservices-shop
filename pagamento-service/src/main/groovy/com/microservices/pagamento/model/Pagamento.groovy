package com.microservices.pagamento.model

import groovy.transform.CompileStatic
import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "pagamentos")
@CompileStatic
class Pagamento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id

    @Column(nullable = false)
    Long pedidoId

    @Column(nullable = false)
    BigDecimal valor

    @Column(nullable = false)
    String status

    @Column(nullable = false)
    String metodoPagamento

    @Column
    String numeroCartao

    @Column
    String codigoSeguranca

    @Column
    String nomeTitular

    @Column
    String validadeCartao

    @Column(nullable = false)
    LocalDateTime dataCriacao

    @Column
    LocalDateTime dataAtualizacao

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
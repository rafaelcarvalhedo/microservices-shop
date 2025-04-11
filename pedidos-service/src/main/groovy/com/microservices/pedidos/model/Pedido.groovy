package com.microservices.pedidos.model

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "pedidos")
class Pedido {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id

    @Column(nullable = false)
    String clienteId

    @Column(nullable = false)
    BigDecimal valorTotal

    @Column(nullable = false)
    String status

    @Column(nullable = false)
    LocalDateTime dataCriacao

    @Column
    String statusPagamento

    @Column
    Long ultimoPagamentoId

    @Column
    String statusEnvio

    @Column
    Long ultimoEnvioId

    @Column
    String codigoRastreamento

    @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL, orphanRemoval = true)
    List<ItemPedido> itens = []

    @PrePersist
    void prePersist() {
        if (dataCriacao == null) {
            dataCriacao = LocalDateTime.now()
        }
        if (status == null) {
            status = 'PENDENTE'
        }
        if (valorTotal == null) {
            valorTotal = 0.0
        }
        if (statusPagamento == null) {
            statusPagamento = 'PENDENTE'
        }
        if (statusEnvio == null) {
            statusEnvio = 'PENDENTE'
        }
    }
} 
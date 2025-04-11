package com.microservices.pedidos.model

import jakarta.persistence.*
import java.math.BigDecimal

@Entity
@Table(name = "itens_pedido")
class ItemPedido {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pedido_id", nullable = false)
    Pedido pedido

    @Column(nullable = false)
    String produtoId

    @Column(nullable = false)
    Integer quantidade

    @Column(nullable = false)
    BigDecimal precoUnitario

    @PrePersist
    @PreUpdate
    void validarDados() {
        if (quantidade == null || quantidade <= 0) {
            throw new IllegalArgumentException("Quantidade deve ser maior que zero")
        }
        if (precoUnitario == null || precoUnitario <= 0) {
            throw new IllegalArgumentException("Preço unitário deve ser maior que zero")
        }
    }
} 
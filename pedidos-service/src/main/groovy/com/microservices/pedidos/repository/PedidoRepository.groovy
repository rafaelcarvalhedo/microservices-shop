package com.microservices.pedidos.repository

import com.microservices.pedidos.model.Pedido
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface PedidoRepository extends JpaRepository<Pedido, Long> {
} 
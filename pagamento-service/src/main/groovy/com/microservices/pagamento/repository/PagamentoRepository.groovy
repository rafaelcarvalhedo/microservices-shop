package com.microservices.pagamento.repository

import com.microservices.pagamento.model.Pagamento
import org.springframework.data.jpa.repository.JpaRepository

interface PagamentoRepository extends JpaRepository<Pagamento, Long> {
    Optional<Pagamento> findByPedidoId(Long pedidoId)
} 
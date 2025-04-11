package com.microservices.envio.repository

import com.microservices.envio.model.Envio
import org.springframework.data.jpa.repository.JpaRepository
import java.util.Optional

interface EnvioRepository extends JpaRepository<Envio, Long> {
    Optional<Envio> findByPedidoId(Long pedidoId)
} 
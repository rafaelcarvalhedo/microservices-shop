package com.microservices.envio.controller

import com.microservices.envio.model.Envio
import com.microservices.envio.service.EnvioService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/envios")
class EnvioController {

    @Autowired
    EnvioService envioService

    @PostMapping
    ResponseEntity<Envio> criarEnvio(@RequestBody Envio envio) {
        ResponseEntity.ok(envioService.criarEnvio(envio))
    }

    @GetMapping("/{id}")
    ResponseEntity<Envio> buscarEnvioPorId(@PathVariable Long id) {
        ResponseEntity.ok(envioService.buscarEnvioPorId(id))
    }

    @GetMapping("/pedido/{pedidoId}")
    ResponseEntity<Envio> buscarEnvioPorPedido(@PathVariable Long pedidoId) {
        ResponseEntity.ok(envioService.buscarEnvioPorPedido(pedidoId))
    }

    @PutMapping("/{id}/status")
    ResponseEntity<Envio> atualizarStatusEnvio(
            @PathVariable Long id,
            @RequestParam String novoStatus) {
        ResponseEntity.ok(envioService.atualizarStatusEnvio(id, novoStatus))
    }

    @PutMapping("/{id}/rastreamento")
    ResponseEntity<Envio> atualizarRastreamento(
            @PathVariable Long id,
            @RequestParam String codigoRastreamento) {
        ResponseEntity.ok(envioService.atualizarRastreamento(id, codigoRastreamento))
    }
} 
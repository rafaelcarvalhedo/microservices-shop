package com.microservices.pedidos.controller

import com.microservices.pedidos.model.Pedido
import com.microservices.pedidos.service.PedidoService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/pedidos")
class PedidoController {

    @Autowired
    PedidoService pedidoService

    @PostMapping
    ResponseEntity<Pedido> criarPedido(@RequestBody Pedido pedido) {
        ResponseEntity.ok(pedidoService.criarPedido(pedido))
    }

    @GetMapping("/{id}")
    ResponseEntity<Pedido> buscarPedidoPorId(@PathVariable Long id) {
        ResponseEntity.ok(pedidoService.buscarPedidoPorId(id))
    }

    @GetMapping
    ResponseEntity<List<Pedido>> listarTodosPedidos() {
        ResponseEntity.ok(pedidoService.listarTodosPedidos())
    }

    @PutMapping("/{id}")
    ResponseEntity<Pedido> atualizarPedido(@PathVariable Long id, @RequestBody Pedido pedido) {
        ResponseEntity.ok(pedidoService.atualizarPedido(id, pedido))
    }

    @DeleteMapping("/{id}")
    ResponseEntity<Void> deletarPedido(@PathVariable Long id) {
        pedidoService.deletarPedido(id)
        ResponseEntity.noContent().build()
    }

    @PutMapping("/{id}/status")
    ResponseEntity<Pedido> atualizarStatusPedido(
            @PathVariable Long id,
            @RequestParam String novoStatus) {
        ResponseEntity.ok(pedidoService.atualizarStatusPedido(id, novoStatus))
    }
} 
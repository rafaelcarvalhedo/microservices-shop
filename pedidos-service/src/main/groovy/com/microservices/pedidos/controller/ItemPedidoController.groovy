package com.microservices.pedidos.controller

import com.microservices.pedidos.model.ItemPedido
import com.microservices.pedidos.service.ItemPedidoService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/pedidos/{pedidoId}/itens")
class ItemPedidoController {

    @Autowired
    ItemPedidoService itemPedidoService

    @PostMapping
    ResponseEntity<ItemPedido> adicionarItem(
            @PathVariable Long pedidoId,
            @RequestBody ItemPedido itemPedido) {
        ResponseEntity.ok(itemPedidoService.adicionarItem(pedidoId, itemPedido))
    }

    @GetMapping
    ResponseEntity<List<ItemPedido>> listarItensDoPedido(@PathVariable Long pedidoId) {
        ResponseEntity.ok(itemPedidoService.listarItensDoPedido(pedidoId))
    }

    @GetMapping("/{itemId}")
    ResponseEntity<ItemPedido> buscarItemPorId(
            @PathVariable Long pedidoId,
            @PathVariable Long itemId) {
        ResponseEntity.ok(itemPedidoService.buscarItemPorId(pedidoId, itemId))
    }

    @PutMapping("/{itemId}")
    ResponseEntity<ItemPedido> atualizarItem(
            @PathVariable Long pedidoId,
            @PathVariable Long itemId,
            @RequestBody ItemPedido itemPedido) {
        ResponseEntity.ok(itemPedidoService.atualizarItem(pedidoId, itemId, itemPedido))
    }

    @DeleteMapping("/{itemId}")
    ResponseEntity<Void> removerItem(
            @PathVariable Long pedidoId,
            @PathVariable Long itemId) {
        itemPedidoService.removerItem(pedidoId, itemId)
        ResponseEntity.noContent().build()
    }
} 
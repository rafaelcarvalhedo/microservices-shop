package com.microservices.pedidos.controller

import com.microservices.pedidos.model.ItemPedido
import com.microservices.pedidos.service.ItemPedidoService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping('/api/pedidos/{pedidoId}/itens')
class ItemPedidoController {

    private final ItemPedidoService itemPedidoService

    ItemPedidoController(ItemPedidoService itemPedidoService) {
        this.itemPedidoService = itemPedidoService
    }

    @PostMapping
    ResponseEntity<ItemPedido> adicionarItem(@PathVariable Long pedidoId, @RequestBody ItemPedido itemPedido) {
        ItemPedido itemSalvo = itemPedidoService.adicionarItem(pedidoId, itemPedido)
        return ResponseEntity.ok(itemSalvo)
    }

    @GetMapping
    ResponseEntity<List<ItemPedido>> listarItens(@PathVariable Long pedidoId) {
        List<ItemPedido> itens = itemPedidoService.listarItensPorPedido(pedidoId)
        return ResponseEntity.ok(itens)
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

    @DeleteMapping('/{itemId}')
    ResponseEntity<Void> removerItem(@PathVariable Long pedidoId, @PathVariable Long itemId) {
        itemPedidoService.removerItem(pedidoId, itemId)
        return ResponseEntity.noContent().build()
    }
} 
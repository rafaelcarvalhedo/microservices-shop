package com.microservices.pedidos.service

import com.microservices.pedidos.model.ItemPedido
import com.microservices.pedidos.model.Pedido
import com.microservices.pedidos.repository.ItemPedidoRepository
import com.microservices.pedidos.repository.PedidoRepository
import groovy.transform.CompileStatic
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@CompileStatic
class ItemPedidoService {

    @Autowired
    ItemPedidoRepository itemPedidoRepository

    @Autowired
    PedidoRepository pedidoRepository

    @Autowired
    PedidoService pedidoService

    @Transactional
    ItemPedido criarItemPedido(ItemPedido itemPedido) {
        return itemPedidoRepository.save(itemPedido)
    }

    @Transactional(readOnly = true)
    List<ItemPedido> listarItensPorPedido(Long pedidoId) {
        return itemPedidoRepository.findByPedidoId(pedidoId)
    }

    @Transactional
    void deletarItemPedido(Long id) {
        itemPedidoRepository.deleteById(id)
    }

    ItemPedido adicionarItem(Long pedidoId, ItemPedido itemPedido) {
        def pedido = pedidoService.buscarPedidoPorId(pedidoId)
        itemPedido.pedido = pedido
        def itemSalvo = itemPedidoRepository.save(itemPedido)
        pedidoService.calcularValorTotal(pedido)
        pedidoRepository.save(pedido)
        itemSalvo
    }

    List<ItemPedido> listarItensDoPedido(Long pedidoId) {
        def pedido = pedidoService.buscarPedidoPorId(pedidoId)
        pedido.itens
    }

    ItemPedido buscarItemPorId(Long pedidoId, Long itemId) {
        def item = itemPedidoRepository.findById(itemId)
                .orElseThrow { new RuntimeException("Item não encontrado com ID: $itemId") }
        
        if (item.pedido.id != pedidoId) {
            throw new RuntimeException("Item $itemId não pertence ao pedido $pedidoId")
        }
        item
    }

    ItemPedido atualizarItem(Long pedidoId, Long itemId, ItemPedido itemAtualizado) {
        def item = buscarItemPorId(pedidoId, itemId)
        item.quantidade = itemAtualizado.quantidade
        item.precoUnitario = itemAtualizado.precoUnitario
        item.produtoId = itemAtualizado.produtoId
        
        def itemSalvo = itemPedidoRepository.save(item)
        pedidoService.calcularValorTotal(item.pedido)
        pedidoRepository.save(item.pedido)
        itemSalvo
    }

    void removerItem(Long pedidoId, Long itemId) {
        def item = buscarItemPorId(pedidoId, itemId)
        def pedido = item.pedido
        itemPedidoRepository.delete(item)
        pedidoService.calcularValorTotal(pedido)
        pedidoRepository.save(pedido)
    }
} 
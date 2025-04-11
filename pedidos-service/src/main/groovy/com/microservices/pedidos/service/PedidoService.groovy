package com.microservices.pedidos.service

import com.microservices.pedidos.model.Pedido
import com.microservices.pedidos.repository.PedidoRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class PedidoService {

    @Autowired
    PedidoRepository pedidoRepository

    Pedido criarPedido(Pedido pedido) {
        pedido.dataCriacao = new Date()
        pedido.status = 'PENDENTE'
        calcularValorTotal(pedido)
        pedidoRepository.save(pedido)
    }

    Pedido buscarPedidoPorId(Long id) {
        pedidoRepository.findById(id)
                .orElseThrow { new RuntimeException("Pedido não encontrado com ID: $id") }
    }

    List<Pedido> listarTodosPedidos() {
        pedidoRepository.findAll()
    }

    Pedido atualizarPedido(Long id, Pedido pedidoAtualizado) {
        def pedido = buscarPedidoPorId(id)
        pedido.clienteId = pedidoAtualizado.clienteId
        pedido.itens = pedidoAtualizado.itens
        calcularValorTotal(pedido)
        pedidoRepository.save(pedido)
    }

    void deletarPedido(Long id) {
        def pedido = buscarPedidoPorId(id)
        pedidoRepository.delete(pedido)
    }

    Pedido atualizarStatusPedido(Long id, String novoStatus) {
        def pedido = buscarPedidoPorId(id)
        pedido.status = novoStatus
        pedidoRepository.save(pedido)
    }

    private void calcularValorTotal(Pedido pedido) {
        pedido.valorTotal = pedido.itens.sum { it.quantidade * it.precoUnitario } ?: 0.0
    }
} 
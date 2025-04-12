package com.microservices.pedidos.service

import com.microservices.pedidos.model.Pedido
import com.microservices.pedidos.repository.PedidoRepository
import groovy.transform.CompileStatic
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
@CompileStatic
class PedidoService {

    @Autowired
    PedidoRepository pedidoRepository

    Pedido criarPedido(Pedido pedido) {
        pedido.dataCriacao = LocalDateTime.now()
        pedido.status = 'PENDENTE'
        pedido.itens.forEach {
            it.setPedido(pedido)
        }
        calcularValorTotal(pedido)
       return  pedidoRepository.save(pedido)
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

    void calcularValorTotal(Pedido pedido) {
        pedido.valorTotal = pedido.itens
                .stream()
                .mapToDouble(item ->
                    item.getPrecoUnitario().multiply(
                        BigDecimal.valueOf(item.getQuantidade())
                    ).doubleValue()
                ).sum()
    }
} 
package com.microservices.pagamento.controller

import com.microservices.pagamento.model.Pagamento
import com.microservices.pagamento.service.PagamentoService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/pagamentos")
class PagamentoController {

    @Autowired
    PagamentoService pagamentoService

    @PostMapping
    ResponseEntity<Pagamento> criarPagamento(@RequestBody Pagamento pagamento) {
        ResponseEntity.ok(pagamentoService.criarPagamento(pagamento))
    }

    @GetMapping("/{id}")
    ResponseEntity<Pagamento> buscarPagamentoPorId(@PathVariable Long id) {
        ResponseEntity.ok(pagamentoService.buscarPagamentoPorId(id))
    }

    @GetMapping("/pedido/{pedidoId}")
    ResponseEntity<Pagamento> buscarPagamentoPorPedido(@PathVariable Long pedidoId) {
        ResponseEntity.ok(pagamentoService.buscarPagamentoPorPedido(pedidoId))
    }

    @PutMapping("/{id}/status")
    ResponseEntity<Pagamento> atualizarStatusPagamento(
            @PathVariable Long id,
            @RequestParam String novoStatus) {
        ResponseEntity.ok(pagamentoService.atualizarStatusPagamento(id, novoStatus))
    }
} 
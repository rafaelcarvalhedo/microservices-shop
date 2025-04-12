# Matriz de Equivalência de Estados

## Estados do Pedido
| Estado do Pedido | Estado do Pagamento | Estado do Envio | Descrição |
|-----------------|---------------------|----------------|-----------|
| PENDENTE | PENDENTE | - | Pedido criado, aguardando pagamento |
| PAGO | APROVADO | PENDENTE | Pagamento aprovado, aguardando preparação do envio |
| EM_PREPARACAO | APROVADO | EM_PREPARACAO | Pagamento aprovado, envio em preparação |
| EM_TRANSITO | APROVADO | EM_TRANSITO | Pagamento aprovado, envio em trânsito |
| ENTREGUE | APROVADO | ENTREGUE | Pagamento aprovado, envio finalizado |
| CANCELADO | CANCELADO | CANCELADO | Pedido cancelado em qualquer etapa |

## Estados do Pagamento
| Estado do Pagamento | Estado do Pedido | Estado do Envio | Descrição |
|---------------------|-----------------|----------------|-----------|
| PENDENTE | PENDENTE | - | Pagamento aguardando processamento |
| APROVADO | PAGO | PENDENTE | Pagamento aprovado, liberando envio |
| RECUSADO | CANCELADO | - | Pagamento recusado, cancelando pedido |
| CANCELADO | CANCELADO | CANCELADO | Pagamento cancelado, cancelando pedido e envio |

## Estados do Envio
| Estado do Envio | Estado do Pedido | Estado do Pagamento | Descrição |
|----------------|-----------------|---------------------|-----------|
| PENDENTE | PAGO | APROVADO | Aguardando início da preparação |
| EM_PREPARACAO | EM_PREPARACAO | APROVADO | Envio em preparação |
| EM_TRANSITO | EM_TRANSITO | APROVADO | Envio em trânsito |
| ENTREGUE | ENTREGUE | APROVADO | Envio finalizado com sucesso |
| CANCELADO | CANCELADO | CANCELADO | Envio cancelado |

## Regras de Transição

1. **Criação do Pedido**:
   - Pedido: PENDENTE
   - Pagamento: PENDENTE
   - Envio: -

2. **Pagamento Aprovado**:
   - Pedido: PAGO
   - Pagamento: APROVADO
   - Envio: PENDENTE

3. **Início da Preparação**:
   - Pedido: EM_PREPARACAO
   - Pagamento: APROVADO
   - Envio: EM_PREPARACAO

4. **Início do Transporte**:
   - Pedido: EM_TRANSITO
   - Pagamento: APROVADO
   - Envio: EM_TRANSITO

5. **Entrega Finalizada**:
   - Pedido: ENTREGUE
   - Pagamento: APROVADO
   - Envio: ENTREGUE

6. **Cancelamento**:
   - Pedido: CANCELADO
   - Pagamento: CANCELADO
   - Envio: CANCELADO

## Validações de Consistência

1. **Pedido PENDENTE**:
   - Pagamento deve ser PENDENTE
   - Envio não deve existir

2. **Pedido PAGO**:
   - Pagamento deve ser APROVADO
   - Envio deve ser PENDENTE

3. **Pedido EM_PREPARACAO**:
   - Pagamento deve ser APROVADO
   - Envio deve ser EM_PREPARACAO

4. **Pedido EM_TRANSITO**:
   - Pagamento deve ser APROVADO
   - Envio deve ser EM_TRANSITO

5. **Pedido ENTREGUE**:
   - Pagamento deve ser APROVADO
   - Envio deve ser ENTREGUE

6. **Pedido CANCELADO**:
   - Pagamento deve ser CANCELADO
   - Envio deve ser CANCELADO (se existir) 
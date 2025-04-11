# Sistema de Microserviços de Pedidos

Este projeto consiste em um sistema distribuído de microserviços para gerenciamento de pedidos, pagamentos e envios, utilizando Spring Boot, PostgreSQL e Kafka.

## Arquitetura

O sistema é composto por três microserviços principais:

1. **Serviço de Pedidos** (`pedidos-service`)
2. **Serviço de Pagamento** (`pagamento-service`)
3. **Serviço de Envio** (`envio-service`)

A comunicação entre os serviços é feita através do Apache Kafka, utilizando o modo KRaft (sem Zookeeper).

## Tecnologias Utilizadas

- **Spring Boot 3.4.4**: Framework para desenvolvimento dos microserviços
- **Groovy**: Linguagem de programação para os serviços
- **PostgreSQL 16**: Banco de dados relacional
- **Apache Kafka 3.6.1**: Sistema de mensageria (modo KRaft)
- **Docker**: Containerização dos serviços
- **Docker Compose**: Orquestração dos containers

## Serviços

### 1. Serviço de Pedidos

**Porta**: 8080

Responsável por gerenciar os pedidos dos clientes, incluindo:
- Criação de pedidos
- Gerenciamento de itens do pedido
- Atualização de status
- Integração com serviços de pagamento e envio

**Endpoints**:
- `POST /api/pedidos` - Criar pedido
- `GET /api/pedidos/{id}` - Buscar pedido por ID
- `GET /api/pedidos` - Listar todos os pedidos
- `PUT /api/pedidos/{id}` - Atualizar pedido
- `DELETE /api/pedidos/{id}` - Deletar pedido
- `PUT /api/pedidos/{id}/status` - Atualizar status do pedido

### 2. Serviço de Pagamento

**Porta**: 8081

Responsável por processar pagamentos dos pedidos, incluindo:
- Processamento de pagamentos
- Validação de métodos de pagamento
- Atualização de status de pagamento
- Integração com serviços de pedidos

**Endpoints**:
- `POST /api/pagamentos` - Criar pagamento
- `GET /api/pagamentos/{id}` - Buscar pagamento por ID
- `GET /api/pagamentos/pedido/{pedidoId}` - Buscar pagamento por pedido
- `PUT /api/pagamentos/{id}/status` - Atualizar status do pagamento

### 3. Serviço de Envio

**Porta**: 8082

Responsável por gerenciar o envio dos pedidos, incluindo:
- Criação de envios
- Rastreamento de entregas
- Atualização de status de envio
- Integração com serviços de pedidos

**Endpoints**:
- `POST /api/envios` - Criar envio
- `GET /api/envios/{id}` - Buscar envio por ID
- `GET /api/envios/pedido/{pedidoId}` - Buscar envio por pedido
- `PUT /api/envios/{id}/status` - Atualizar status do envio
- `PUT /api/envios/{id}/rastreamento` - Atualizar código de rastreamento

## Fluxo de Dados

1. Cliente cria um pedido no `pedidos-service`
2. `pedidos-service` publica evento no Kafka
3. `pagamento-service` consome evento e processa pagamento
4. `pagamento-service` publica resultado no Kafka
5. `pedidos-service` atualiza status do pagamento
6. `envio-service` consome evento e inicia processo de envio
7. `envio-service` publica atualizações no Kafka
8. `pedidos-service` atualiza status do envio

## Configuração do Ambiente

### Pré-requisitos

- Docker
- Docker Compose
- Java 17

### Executando o Projeto

1. Clone o repositório
2. Execute o comando:
```bash
docker-compose up -d
```

Os serviços estarão disponíveis em:
- Pedidos: http://localhost:8080
- Pagamento: http://localhost:8081
- Envio: http://localhost:8082

### Parando os Serviços

```bash
docker-compose down
```

### Verificando Logs

```bash
docker-compose logs -f [nome-do-serviço]
```

## Estrutura do Projeto

```
.
├── pedidos-service/
│   ├── src/
│   │   ├── main/
│   │   │   ├── groovy/
│   │   │   │   └── com/microservices/pedidos/
│   │   │   └── resources/
│   │   └── test/
│   ├── build.gradle
│   └── Dockerfile
├── pagamento-service/
│   ├── src/
│   │   ├── main/
│   │   │   ├── groovy/
│   │   │   │   └── com/microservices/pagamento/
│   │   │   └── resources/
│   │   └── test/
│   ├── build.gradle
│   └── Dockerfile
├── envio-service/
│   ├── src/
│   │   ├── main/
│   │   │   ├── groovy/
│   │   │   │   └── com/microservices/envio/
│   │   │   └── resources/
│   │   └── test/
│   ├── build.gradle
│   └── Dockerfile
├── docker-compose.yml
└── README.md
```

## Status dos Pedidos

O sistema mantém diferentes status para cada aspecto do pedido:

### Status do Pedido
- `PENDENTE`: Pedido criado
- `PAGO`: Pagamento confirmado
- `EM_PROCESSAMENTO`: Em preparação para envio
- `ENVIADO`: Pedido enviado
- `ENTREGUE`: Pedido entregue
- `CANCELADO`: Pedido cancelado

### Status do Pagamento
- `PENDENTE`: Aguardando pagamento
- `APROVADO`: Pagamento aprovado
- `REJEITADO`: Pagamento rejeitado
- `CANCELADO`: Pagamento cancelado

### Status do Envio
- `PENDENTE`: Aguardando envio
- `EM_TRANSITO`: Em trânsito
- `ENTREGUE`: Entregue
- `CANCELADO`: Envio cancelado # microservices-shop

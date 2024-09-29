# Projeto de Comunicação Indireta com RabbitMQ - Fila de Mensagens e Pub/Sub

Este projeto implementa duas abordagens de comunicação indireta utilizando RabbitMQ:
1. **Fila de Mensagens (FIFO)** para processar pedidos de sorvete.
2. **Pub/Sub** para anunciar novos sabores de sorvete.

## Tecnologias Utilizadas
- **Java** (JDK 17)
- **RabbitMQ**
- **Maven**
- **SLF4J** (para logging)

---

## 1. Fila de Mensagens (FIFO) - Pedidos de Sorvete

### Descrição:
Nesta abordagem, os pedidos de sorvete são colocados em uma fila e processados de forma sequencial, na ordem em que chegaram (FIFO - First In, First Out). Um único consumidor processa cada mensagem.

### Como Funciona:
- O **PedidoProdutor** envia pedidos de sorvete para a fila chamada `"fila_pedidos_sorvete"`.
- O **AtendenteConsumidor** consome os pedidos um por um, na ordem em que foram colocados na fila.
- A fila garante que cada pedido será processado apenas por um consumidor e na ordem correta.

### Exemplo de Pedidos:
- Sorvete de Chocolate com Calda Quente
- Sorvete de Morango com Granulado
- Sorvete de Baunilha com Cobertura de Caramelo
- Sorvete de Pistache com Castanhas

### Características Principais:
- **Ordem Garantida (FIFO)**: Os pedidos são processados na ordem em que chegam à fila.
- **Um Consumidor por Mensagem**: Cada mensagem é entregue e processada por apenas um consumidor.

---

## 2. Pub/Sub - Anúncio de Novos Sabores

### Descrição:
Na abordagem **Pub/Sub** (Publicação/Assinatura), o produtor publica um anúncio de novos sabores de sorvete, e todos os consumidores inscritos recebem a mesma mensagem ao mesmo tempo.

### Como Funciona:
- O **AnuncioProdutor** publica anúncios de novos sabores de sorvete na **exchange** chamada `"anuncio_sabores"`.
- Todos os consumidores inscritos nessa exchange recebem as mensagens simultaneamente, independentemente de quantos consumidores estejam ouvindo.
- O consumidor é responsável por escutar a exchange e processar os anúncios recebidos.

### Exemplo de Sabores Anunciados:
- Pistache
- Doce de Leite
- Nutella
- Menta com Chocolate
- Frutas Vermelhas

### Características Principais:
- **Entrega Simultânea**: Todos os consumidores recebem a mesma mensagem ao mesmo tempo.
- **Múltiplos Consumidores**: Vários consumidores podem processar a mesma mensagem simultaneamente, pois ela é entregue a todos os inscritos na exchange.

---

## Diferença Principal entre as Abordagens

### Fila de Mensagens (FIFO):
- **Entrega única por mensagem**: A mensagem é entregue para **apenas um consumidor**.
- **Ordem Garantida**: As mensagens são processadas na ordem de chegada, de maneira sequencial.
- **Cenário Ideal**: Quando cada mensagem (pedido) precisa ser processada individualmente e em ordem.

### Pub/Sub:
- **Entrega para múltiplos consumidores**: A mensagem é entregue a **todos os consumidores inscritos** ao mesmo tempo.
- **Cenário Ideal**: Quando a mesma mensagem precisa ser distribuída para vários consumidores ao mesmo tempo, como no caso de anúncios ou notificações.

---

## Como Executar

### Requisitos:
- Instale o RabbitMQ no seu sistema.
- Certifique-se de que o RabbitMQ está rodando e que a interface de gerenciamento está habilitada (normalmente acessível via [http://localhost:15672](http://localhost:15672)).

### Passos para Execução:

#### 1. Fila de Mensagens (FIFO):
1. Execute o consumidor:
   - Classe: `AtendenteConsumidor`
   - Ele ficará aguardando pedidos de sorvete na fila.
2. Execute o produtor:
   - Classe: `PedidoProdutor`
   - Ele enviará pedidos de sorvete para a fila, que serão processados na ordem em que chegarem.

#### 2. Pub/Sub:
1. Execute o consumidor:
   - Classe: `ClienteConsumidor`
   - Ele ficará aguardando anúncios de novos sabores na exchange.
2. Execute o produtor:
   - Classe: `AnuncioProdutor`
   - Ele publicará anúncios de novos sabores, que serão recebidos simultaneamente por todos os consumidores inscritos.

---


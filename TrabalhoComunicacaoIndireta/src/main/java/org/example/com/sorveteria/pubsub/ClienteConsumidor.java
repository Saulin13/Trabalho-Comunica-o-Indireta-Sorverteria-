package com.sorveteria.pubsub;

import com.rabbitmq.client.*;

public class ClienteConsumidor {

    private static final String EXCHANGE_NAME = "anuncio_sabores";
    private static final String QUEUE_NAME = "fila_anuncio_sabores"; // Nome fixo para a fila

    public static void main(String[] argv) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        // Declare the exchange
        channel.exchangeDeclare(EXCHANGE_NAME, "fanout");

        // Declare a durable queue with a fixed name
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);

        // Bind the queue to the exchange
        channel.queueBind(QUEUE_NAME, EXCHANGE_NAME, "");

        System.out.println("Aguardando novos sabores...");

        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            String mensagem = new String(delivery.getBody(), "UTF-8");
            System.out.println("Novo anúncio recebido: " + mensagem);
        };

        // Start consuming messages
        channel.basicConsume(QUEUE_NAME, true, deliverCallback, consumerTag -> { });
    }
}

package com.sorveteria.fila;

import com.rabbitmq.client.*;

public class AtendenteConsumidor {

    private final static String QUEUE_NAME = "fila_pedidos_sorvete";

    public static void main(String[] argv) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        System.out.println("Aguardando pedidos...");

        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            String pedido = new String(delivery.getBody(), "UTF-8");
            System.out.println("Preparando sorvete de: " + pedido);
        };
        channel.basicConsume(QUEUE_NAME, true, deliverCallback, consumerTag -> { });
    }
}

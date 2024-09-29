package com.sorveteria.fila;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class PedidoProdutor {

    private final static String QUEUE_NAME = "fila_pedidos_sorvete";

    public static void main(String[] argv) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel()) {
            channel.queueDeclare(QUEUE_NAME, false, false, false, null);

            // List of new orders
            String[] pedidos = {
                    "Sorvete de Chocolate com Calda Quente",
                    "Sorvete de Morango com Granulado",
                    "Sorvete de Baunilha com Cobertura de Caramelo",
                    "Sorvete de Limão com Raspas de Limão Siciliano",
                    "Sorvete de Pistache com Castanhas",
                    "Sorvete de Nutella com Morangos",
                    "Sundae de Frutas Vermelhas",
                    "Sorvete de Cookies and Cream com Biscoito Triturado",
                    "Sorvete de Maracujá com Calda de Chocolate",
                    "Sorvete de Menta com Pedacinhos de Chocolate"
            };

            // Loop to send each order to the queue
            for (String pedido : pedidos) {
                channel.basicPublish("", QUEUE_NAME, null, pedido.getBytes("UTF-8"));
                System.out.println("Pedido enviado: " + pedido);
            }
        }
    }
}

package com.sorveteria.pubsub;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class AnuncioProdutor {

    private static final String EXCHANGE_NAME = "anuncio_sabores";

    public static void main(String[] argv) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel()) {
            // Declare the exchange
            channel.exchangeDeclare(EXCHANGE_NAME, "fanout");

            // List of new flavors to announce
            String[] novosSabores = {
                    "Pistache",
                    "Doce de Leite",
                    "Nutella",
                    "Menta com Chocolate",
                    "Frutas Vermelhas",
                    "Cookies and Cream",
                    "Maracujá com Chocolate",
                    "Coco com Doce de Leite",
                    "Torta de Limão",
                    "Café com Chocolate"
            };

            // Loop to send each flavor as a separate announcement
            for (String sabor : novosSabores) {
                String mensagem = "Novo sabor de " + sabor + " disponível!";
                channel.basicPublish(EXCHANGE_NAME, "", null, mensagem.getBytes("UTF-8"));
                System.out.println("Anúncio enviado: " + mensagem);
            }
        }
    }
}

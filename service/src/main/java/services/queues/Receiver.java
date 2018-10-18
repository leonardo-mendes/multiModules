package services.queues;

import com.rabbitmq.client.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

@Service
public class Receiver implements Runnable {

    @Autowired
    ConnectionFactory connectionFactory;

    @Value("${queue.order.name}")
    private String queue;

    @Override
    public void run() {
        try {
            Channel channel = null;
            Connection connection = connectionFactory.newConnection();
            channel = connection.createChannel();
            channel.queueDeclare(queue, false, false, false, null);
            System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

            Consumer consumer = new DefaultConsumer(channel) {
                @Override
                public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body)
                        throws IOException {
                    String message = new String(body, "UTF-8");
                    System.out.println(" [x] Received '" + message + "'");
                }
            };
            channel.basicConsume(queue, true, consumer);

        } catch (IOException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
    }
}

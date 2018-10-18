package services.queues;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

@Service
public class Sender {

    @Autowired
    ConnectionFactory connectionFactory;

    @Value("${queue.order.name}")
    private String queue;

    public void send(String message) throws IOException, TimeoutException {
        Connection connection = connectionFactory.newConnection();
        Channel channel = connection.createChannel();

        channel.queueDeclare(queue, false, false, false, null);
        String sendMessage = message;
        channel.basicPublish("", queue, null, message.getBytes("UTF-8"));
        System.out.println(" [x] Sent a message: '" + message + "'");

        channel.close();
        connection.close();
    }
}

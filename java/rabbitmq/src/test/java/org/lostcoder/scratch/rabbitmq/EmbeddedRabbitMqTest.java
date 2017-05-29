package org.lostcoder.scratch.rabbitmq;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import io.arivera.oss.embedded.rabbitmq.EmbeddedRabbitMq;
import io.arivera.oss.embedded.rabbitmq.EmbeddedRabbitMqConfig;
import io.arivera.oss.embedded.rabbitmq.PredefinedVersion;
import io.arivera.oss.embedded.rabbitmq.Version;
import io.arivera.oss.embedded.rabbitmq.util.ArchiveType;
import io.arivera.oss.embedded.rabbitmq.util.OperatingSystem;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Created by Sean on 2017/05/03.
 */
public class EmbeddedRabbitMqTest {

    private EmbeddedRabbitMq rabbitMq;

    @Before
    public void startRabbitMq() {
        EmbeddedRabbitMqConfig config = new EmbeddedRabbitMqConfig.Builder().version(PredefinedVersion.LATEST).build();
        rabbitMq = new EmbeddedRabbitMq(config);
        rabbitMq.start();
    }

    @After
    public void stopRabbitMq() {
        rabbitMq.stop();
    }

    @Test
    public void connectToRabbitMq() throws Exception {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("localhost");
        connectionFactory.setVirtualHost("/");
        connectionFactory.setUsername("guest");
        connectionFactory.setPassword("guest");

        Connection connection = connectionFactory.newConnection();
        assertThat(connection.isOpen(), equalTo(true));
        Channel channel = connection.createChannel();
        assertThat(channel.isOpen(), equalTo(true));
        connection.getServerProperties().forEach((s, o) -> {
            System.out.println(s + " => " + o);
        });
        channel.close();
        connection.close();
    }
}

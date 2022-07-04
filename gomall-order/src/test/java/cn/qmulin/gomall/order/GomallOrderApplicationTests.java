package cn.qmulin.gomall.order;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Slf4j
class GomallOrderApplicationTests {
    @Autowired
    private AmqpAdmin amqpAdmin;
    @Autowired
    private RabbitTemplate rabbitTemplate;

    /**
     * 1.使用AmqAdmin进行创建Exchange\Queue\Binding
     */
    @Test
    void creatExchange() {
        DirectExchange exchange = new DirectExchange("hello-java-exchange", true, false);
        amqpAdmin.declareExchange(exchange);
        log.info("exchange {} 创建成功", exchange.getName());
    }

    @Test
    public void creatQueue() {
        Queue queue = new Queue("hello-java-queue", true, false, false);
        amqpAdmin.declareQueue(queue);
        log.info("queue {} 创建成功", queue.getName());
    }
    @Test
    public void creatBinding() {
        Binding binding = new Binding("hello-java-queue", Binding.DestinationType.QUEUE, "hello-java-exchange", "hello.java", null);
        amqpAdmin.declareBinding(binding);
        log.info("binding创建成功");
    }


    @Test
    void sendMessage(){
        Message message = new Message(new byte[]{1, 3}, new MessageProperties());
        rabbitTemplate.send("hello-java-exchange","hello.java",message);
        rabbitTemplate.convertAndSend("hello-java-exchange","hello.java","sfsaga");
    }
}

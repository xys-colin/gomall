package cn.qmulin.gomall.ware.listener;

import cn.qmulin.common.to.mq.OrderTo;
import cn.qmulin.common.to.mq.StockLockedTo;
import cn.qmulin.gomall.ware.service.WareSkuService;
import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * @description:
 * @author: xys
 * @date: 2022/7/4 19:28
 */
@Service
@RabbitListener(queues = "stock.release.stock.queue")
public class StockReleaseListener {
    @Autowired
    WareSkuService wareSkuService;

    @RabbitHandler
    public void handleStockLockedRelease(StockLockedTo to, Channel channel, Message message) throws IOException {
        try {
            wareSkuService.unlockStock(to);
            channel.basicAck(message.getMessageProperties().getDeliveryTag(),false);
        }catch (Exception e){
            channel.basicReject(message.getMessageProperties().getDeliveryTag(),true);
        }
    }
    @RabbitHandler
    public void handleOrderCloseRelease(OrderTo orderTo, Message message, Channel channel) throws IOException {
        try {
            wareSkuService.unlockStock(orderTo);
            channel.basicAck(message.getMessageProperties().getDeliveryTag(),false);
        }catch (Exception exception){
            channel.basicReject(message.getMessageProperties().getDeliveryTag(),true);
        }

    }
}

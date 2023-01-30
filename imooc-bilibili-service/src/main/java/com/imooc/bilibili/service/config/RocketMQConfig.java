package com.imooc.bilibili.service.config;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.imooc.bilibili.domain.UserFollowing;
import com.imooc.bilibili.domain.UserMoment;
import com.imooc.bilibili.domain.constant.UserMQConstant;
import com.imooc.bilibili.service.UserFollowingService;
import com.imooc.bilibili.service.websocket.WebSocketService;
import io.netty.util.internal.StringUtil;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.common.message.MessageExt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Configuration
public class RocketMQConfig {

    @Value("${rocketmq.name.server.address}")
    private String nameServerAddr;

    @Resource
    private RedisTemplate<String, String> redisTemplate;

    @Autowired
    private UserFollowingService userFollowingService;

    //和用户动态相关的生产者
    @Bean("momentsProducer")
    public DefaultMQProducer momentsProducer() throws MQClientException {
        //实例化消息生产者producer
        DefaultMQProducer producer = new DefaultMQProducer(UserMQConstant.GROUP_MOMENTS);
        //设置NameServer的地址
        producer.setNamesrvAddr(nameServerAddr);
        //启动Producer实例
        producer.start();
        return producer;
    }

    //和用户动态相关的消费者
    @Bean("momentsConsumer")
    public DefaultMQPushConsumer momentsConsumer() throws MQClientException {
        //实例化消费者
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer(UserMQConstant.GROUP_MOMENTS);
        //设置NameServer的地址
        consumer.setNamesrvAddr(nameServerAddr);
        //订阅一个或多个topic，并设置tag来过滤需要消费的消息
        consumer.subscribe(UserMQConstant.TOPIC_MOMENTS, "*");
        //注册回调实现类来处理从broker拉取回来的消息
        consumer.registerMessageListener(new MessageListenerConcurrently() {
            @Override
            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, //对传出的消息的扩充
                                                            ConsumeConcurrentlyContext consumeConcurrentlyContext) {
                MessageExt msg = msgs.get(0);
                if(msg == null){
                    return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
                }
                //取出msg中关于实体类的参数
                String bodyStr = new String(msg.getBody());
                //将参数转换为实体类
                UserMoment userMoment = JSONObject.toJavaObject(JSON.parseObject(bodyStr), UserMoment.class);
                Long userId = userMoment.getUserId();
                List<UserFollowing> fanList = userFollowingService.getUserFans(userId);
                for (UserFollowing fan : fanList) {
                    String key = "subscribed-" + fan.getUserId();
                    String subscribedListStr = redisTemplate.opsForValue().get(key);
                    List<UserMoment> subscribedList;
                    if(StringUtil.isNullOrEmpty(subscribedListStr)){
                        subscribedList = new ArrayList<>();
                    } else {
                        subscribedList = JSONArray.parseArray(subscribedListStr, UserMoment.class);
                    }
                    subscribedList.add(userMoment);
                    redisTemplate.opsForValue().set(key, JSONObject.toJSONString(subscribedList));
                }
                //标记该消息已经被成功消费
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            }
        });
        //启动消费者实例
        consumer.start();
        return consumer;
    }

    //和弹幕相关的生产者
    @Bean("bulletChatsProducer")
    public DefaultMQProducer bulletChatsProducer() throws MQClientException {
        //实例化消息生产者producer
        DefaultMQProducer producer = new DefaultMQProducer(UserMQConstant.GROUP_BULLETS);
        //设置NameServer的地址
        producer.setNamesrvAddr(nameServerAddr);
        //启动Producer实例
        producer.start();
        return producer;
    }

    //和弹幕相关的消费者
    @Bean("bulletChatsConsumer")
    public DefaultMQPushConsumer bulletChatsConsumer() throws MQClientException {
        //实例化消费者
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer(UserMQConstant.GROUP_BULLETS);
        //设置NameServer的地址
        consumer.setNamesrvAddr(nameServerAddr);
        //订阅一个或多个topic，并设置tag来过滤需要消费的消息
        consumer.subscribe(UserMQConstant.TOPIC_BULLETS, "*");
        //注册回调实现类来处理从broker拉取回来的消息
        consumer.registerMessageListener(new MessageListenerConcurrently() {
            @Override
            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, //对传出的消息的扩充
                                                            ConsumeConcurrentlyContext consumeConcurrentlyContext) {
                MessageExt msg = msgs.get(0);
                if(msg == null){
                    return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
                }
                //取出msg中关于实体类的参数
                byte[] msgByte = msg.getBody();
                String bodyStr = new String(msgByte);
                JSONObject jsonObject = JSONObject.parseObject(bodyStr);
                String sessionId = jsonObject.getString("sessionId");
                String message = jsonObject.getString("message");
                WebSocketService webSocketService = WebSocketService.WEBSOCKET_MAP.get(sessionId);
                if(webSocketService.getSession().isOpen()){
                    try {
                        webSocketService.sendMessage(message);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                //标记该消息已经被成功消费
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            }
        });
        //启动消费者实例
        consumer.start();
        return consumer;
    }

}

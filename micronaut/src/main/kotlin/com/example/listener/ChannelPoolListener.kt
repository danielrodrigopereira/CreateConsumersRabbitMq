package com.example.listener

import com.rabbitmq.client.BuiltinExchangeType
import com.rabbitmq.client.Channel
import com.rabbitmq.client.Consumer
import com.rabbitmq.client.DefaultConsumer
import io.micronaut.rabbitmq.connect.ChannelInitializer
import java.io.IOException
import javax.inject.Named
import javax.inject.Singleton

@Singleton
@Named("product-cluster")
class ChannelPoolListener : ChannelInitializer() {

    @Throws(IOException::class)
    @Named("product-cluster")
    override fun initialize(channel: Channel) {
        channel.queueDeclare("Product", true, false, false, null)
        channel.basicQos(100, true)
        channel.basicQos(100, false)
//        channel.basicConsume("Product", false, DefaultConsumer(channel));
//        channel.basicConsume("Product", false, DefaultConsumer(channel));
//        channel.basicConsume("Product", false, DefaultConsumer(channel));
//        channel.basicConsume("Product", false, DefaultConsumer(channel));
//        channel.basicConsume("Product", false, DefaultConsumer(channel));
//        channel.basicConsume("Product", false, DefaultConsumer(channel));
//        channel.basicConsume("Product", false, DefaultConsumer(channel));
//        channel.basicConsume("Product", false, DefaultConsumer(channel));
//        channel.basicConsume("Product", false, DefaultConsumer(channel));
        channel.exchangeDeclare("notificationExchange", BuiltinExchangeType.DIRECT, true)

        channel.queueBind("Product", "notificationExchange", "ProductKey")
    }

}
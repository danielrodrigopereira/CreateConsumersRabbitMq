package com.example.demoSpring.listener

import org.springframework.amqp.core.Binding
import org.springframework.amqp.core.BindingBuilder
import org.springframework.amqp.core.DirectExchange
import org.springframework.amqp.core.Queue
import org.springframework.amqp.rabbit.annotation.EnableRabbit
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory
import org.springframework.amqp.rabbit.connection.ConnectionFactory
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@EnableRabbit
@Configuration
class RabbitConfig {
    var host: String = "localhost"
    var username: String = "guest"
    var password: String = "guest"
    var concurrentConsumers: Int = 50
    val exchangeName: String = "notificationExchange"
    val queue: String = "Product"
    val routingKey: String = "ProductKey"

    @Bean
    fun connectionFactory(): ConnectionFactory {
        val connectionFactory = CachingConnectionFactory(host)
        connectionFactory.username = username!!
        connectionFactory.setPassword(password!!)
        return connectionFactory
    }

    @Bean
    fun rabbitTemplate(): RabbitTemplate {
        val rabbitTemplate = RabbitTemplate(connectionFactory())
        rabbitTemplate.setExchange(exchangeName)
//        rabbitTemplate.messageConverter = Jackson2JsonMessageConverter()
        return rabbitTemplate
    }

    @Bean
    fun rabbitListenerContainerFactory(): SimpleRabbitListenerContainerFactory {
        //TODO: testar LocalizedQueueConnectionFactory para conectar ao cluster
        val factory = SimpleRabbitListenerContainerFactory()
        factory.setConnectionFactory(connectionFactory())
        factory.setMessageConverter(Jackson2JsonMessageConverter())
        factory.setConcurrentConsumers(concurrentConsumers)
        factory.setDefaultRequeueRejected(false)
        return factory
    }

    @Bean
    fun directExchange(): DirectExchange {
        return DirectExchange(exchangeName)
    }

    @Bean
    fun createQueue(): Queue {
        return Queue(queue)
    }

    @Bean
    fun binding(): Binding {
        return BindingBuilder.bind(createQueue())
                .to(directExchange())
                .with(routingKey)
    }

}
package com.example.listener

import io.micronaut.http.annotation.Consumes
import io.micronaut.rabbitmq.annotation.Queue
import io.micronaut.rabbitmq.annotation.RabbitListener
import io.micronaut.rabbitmq.annotation.RabbitProperty
import java.util.*


@RabbitListener
//@RabbitProperty(name = "name", value = "consumer", type = String::class)
class ProductListener {

    val messageLengths: MutableList<String> = Collections.synchronizedList(ArrayList())

//    @Queue(value = "Product", connection = "product-listener")
    @Queue("Product")
    fun receive(data: ByteArray) {
        val string = String(data)
        messageLengths.add(string)
        println("Kotlin received ${data.size} bytes from RabbitMQ: ${string}")
    }
}
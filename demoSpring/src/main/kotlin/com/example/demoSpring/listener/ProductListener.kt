package com.example.demoSpring.listener

import org.springframework.amqp.rabbit.annotation.RabbitListener
import org.springframework.stereotype.Component
import java.util.*


@Component
class ProductListener {

    val messageLengths: MutableList<String> = Collections.synchronizedList(ArrayList())

    @RabbitListener(queues = arrayOf("Product"))
    fun receive(data: ByteArray) {
        val string = String(data)
        messageLengths.add(string)
        println("Kotlin received ${data.size} bytes from RabbitMQ: ${string}")
    }
}
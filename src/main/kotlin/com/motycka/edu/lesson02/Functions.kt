package com.motycka.edu.lesson02

val coffeeOrders = mutableMapOf<Int, List<String>>()
var currentOrderId = -1

fun main() {
    // You can write code here to try the functions
    processOrder(listOf(ESPRESSO, CAPPUCCINO, CAPPUCCINO, AMERICANO), 20.0)
    processOrder(listOf(ESPRESSO, FLAT_WHITE, AMERICANO), 10.0)
    // processOrder(listOf(ESPRESSO, ESPRESSO, DOUBLE_ESPRESSO), 5.0) // will fail due to insufficient payment
}

/* Implement the functions below */


fun processOrder(items: List<String>, payment: Double): Double {
    val orderId = placerOrder(items) // call placerOrder(items)
    val totalToPay = payOrder(orderId) // call payOrder(orderId)

    val change = payment - totalToPay // calculate change by subtracting totalToPay from payment

    if (change < 0) {
        println("Order $orderId failed: Insufficient payment.")
        return -1.0
    }

    completeOrder(orderId) // call completeOrder(orderId)

    println("Order $orderId completed. Change: $change")
    return change
}

// TODO Implement placerOrder(items: List<String>): Int
fun placerOrder(items: List<String>): Int {
    currentOrderId += 1
    coffeeOrders[currentOrderId] = items
    println("Placed order #$currentOrderId: $items")
    return currentOrderId
}
// TODO Implement payOrder(orderId: Int): Double
fun payOrder(orderId: Int): Double {
    val items = coffeeOrders[orderId]
        ?: throw IllegalStateException("Order ID $orderId not found.")

    val prices = items.map {
        when (it) {
            ESPRESSO -> ESPRESSO_PRICE
            DOUBLE_ESPRESSO -> DOUBLE_ESPRESSO_PRICE
            CAPPUCCINO -> CAPPUCCINO_PRICE
            LATTE -> LATTE_PRICE
            AMERICANO -> AMERICANO_PRICE
            FLAT_WHITE -> FLAT_WHITE_PRICE
            else -> 0.0
        }
    }

    return if (items.size >= 4) {
        println("You ordered 3 or more coffees, you get 1 for free!")
        prices.sum() - (prices.minOrNull() ?: 0.0)
    } else {
        prices.sum()
    }
}
// TODO Implement completeOrder(orderId: Int)
fun completeOrder(orderId: Int) {
    if (!coffeeOrders.containsKey(orderId)) {
        throw IllegalStateException("Order ID $orderId not found.")
    }

    println("Order $orderId is marked as complete.")
}
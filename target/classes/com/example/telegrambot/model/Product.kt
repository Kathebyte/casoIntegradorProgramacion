package com.example.telegrambot.model

data class Product(
        val code: Int,
        var name: String,
        var price: Double,
        var quantity: Int,
        val initialQuantity: Int
)

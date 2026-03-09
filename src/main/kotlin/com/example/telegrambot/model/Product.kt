package com.example.telegrambot.model

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id

@Entity
data class Product(
        @Id @GeneratedValue(strategy = GenerationType.IDENTITY) val code: Int? = null,
        var name: String = "",
        var price: Double = 0.0,
        var quantity: Int = 0,
        var initialQuantity: Int = 0
)

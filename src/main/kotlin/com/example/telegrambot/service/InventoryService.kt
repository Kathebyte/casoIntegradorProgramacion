package com.example.telegrambot.service

import com.example.telegrambot.model.Product
import org.springframework.stereotype.Service

@Service
class InventoryService {
    private val products =
            mutableListOf(
                    Product(1, "Peras", 4000.0, 65, 65),
                    Product(2, "Limones", 1500.0, 25, 25),
                    Product(3, "Moras", 2000.0, 30, 30),
                    Product(4, "Piñas", 3000.0, 15, 15),
                    Product(5, "Tomates", 1000.0, 30, 30),
                    Product(6, "Fresas", 3000.0, 12, 12),
                    Product(7, "Frunas", 300.0, 50, 50),
                    Product(8, "Galletas", 500.0, 400, 400),
                    Product(9, "Chocolates", 1200.0, 500, 500),
                    Product(10, "Arroz", 1200.0, 60, 60)
            )

    fun getAllProducts(): List<Product> = products

    fun calculateTotalInventoryValue(): Double {
        return products.sumOf { it.price * it.quantity }
    }

    fun getLowStockProducts(): List<Product> {
        return products.filter { it.quantity <= it.initialQuantity * 0.1 }
    }

    fun insertProduct(name: String, price: Double, quantity: Int) {
        val newCode = products.size + 1
        val newProduct = Product(newCode, name, price, quantity, quantity)
        products.add(newProduct)
    }
}

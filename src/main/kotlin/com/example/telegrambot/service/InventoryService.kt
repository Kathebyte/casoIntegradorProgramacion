package com.example.telegrambot.service

import com.example.telegrambot.model.Product
import com.example.telegrambot.repository.ProductRepository
import jakarta.annotation.PostConstruct
import org.springframework.stereotype.Service

@Service
class InventoryService(private val productRepository: ProductRepository) {

    @PostConstruct
    fun initData() {
        if (productRepository.count() == 0L) {
            val initialProducts =
                    listOf(
                            Product(
                                    name = "Peras",
                                    price = 4000.0,
                                    quantity = 65,
                                    initialQuantity = 65
                            ),
                            Product(
                                    name = "Limones",
                                    price = 1500.0,
                                    quantity = 25,
                                    initialQuantity = 25
                            ),
                            Product(
                                    name = "Moras",
                                    price = 2000.0,
                                    quantity = 30,
                                    initialQuantity = 30
                            ),
                            Product(
                                    name = "Piñas",
                                    price = 3000.0,
                                    quantity = 15,
                                    initialQuantity = 15
                            ),
                            Product(
                                    name = "Tomates",
                                    price = 1000.0,
                                    quantity = 30,
                                    initialQuantity = 30
                            ),
                            Product(
                                    name = "Fresas",
                                    price = 3000.0,
                                    quantity = 12,
                                    initialQuantity = 12
                            ),
                            Product(
                                    name = "Frunas",
                                    price = 300.0,
                                    quantity = 50,
                                    initialQuantity = 50
                            ),
                            Product(
                                    name = "Galletas",
                                    price = 500.0,
                                    quantity = 400,
                                    initialQuantity = 400
                            ),
                            Product(
                                    name = "Chocolates",
                                    price = 1200.0,
                                    quantity = 500,
                                    initialQuantity = 500
                            ),
                            Product(
                                    name = "Arroz",
                                    price = 1200.0,
                                    quantity = 60,
                                    initialQuantity = 60
                            )
                    )
            productRepository.saveAll(initialProducts)
        }
    }

    fun getAllProducts(): List<Product> = productRepository.findAll()

    fun calculateTotalInventoryValue(): Double {
        return productRepository.findAll().sumOf { it.price * it.quantity }
    }

    fun getLowStockProducts(): List<Product> {
        return productRepository.findAll().filter { it.quantity <= it.initialQuantity * 0.1 }
    }

    fun insertProduct(name: String, price: Double, quantity: Int) {
        val newProduct =
                Product(name = name, price = price, quantity = quantity, initialQuantity = quantity)
        productRepository.save(newProduct)
    }

    fun updateProduct(code: Int, newQuantity: Int, newPrice: Double): Boolean {
        val productOptional = productRepository.findById(code)
        if (productOptional.isPresent) {
            val product = productOptional.get()
            product.quantity = newQuantity
            product.initialQuantity = newQuantity // Se actualiza la base para el cálculo del 10%
            product.price = newPrice
            productRepository.save(product)
            return true
        }
        return false
    }

    fun deleteProduct(code: Int): Boolean {
        if (productRepository.existsById(code)) {
            productRepository.deleteById(code)
            return true
        }
        return false
    }

    fun updateStock(code: Int, soldQuantity: Int): Product? {
        val productOptional = productRepository.findById(code)
        if (productOptional.isPresent) {
            val product = productOptional.get()
            if (product.quantity >= soldQuantity) {
                product.quantity -= soldQuantity
                return productRepository.save(product)
            }
        }
        return null
    }
}

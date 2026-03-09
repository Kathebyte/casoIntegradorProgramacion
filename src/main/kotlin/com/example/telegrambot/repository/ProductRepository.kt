package com.example.telegrambot.repository

import com.example.telegrambot.model.Product
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository interface ProductRepository : JpaRepository<Product, Int>

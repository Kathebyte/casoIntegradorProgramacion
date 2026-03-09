package com.example.telegrambot

import com.example.telegrambot.service.InventoryService
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import org.telegram.telegrambots.client.okhttp.OkHttpTelegramClient
import org.telegram.telegrambots.longpolling.interfaces.LongPollingUpdateConsumer
import org.telegram.telegrambots.longpolling.starter.SpringLongPollingBot
import org.telegram.telegrambots.longpolling.util.LongPollingSingleThreadUpdateConsumer
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.objects.Update
import org.telegram.telegrambots.meta.generics.TelegramClient

@Component
class SimpleBot(
        @Value("\${telegram.bot.token}") private val botToken: String,
        private val inventoryService: InventoryService
) : SpringLongPollingBot, LongPollingSingleThreadUpdateConsumer {

    private val telegramClient: TelegramClient = OkHttpTelegramClient(botToken)

    override fun getBotToken(): String = botToken

    override fun getUpdatesConsumer(): LongPollingUpdateConsumer = this

    override fun consume(update: Update) {
        if (update.hasMessage() && update.message.hasText()) {
            val messageText = update.message.text
            val chatId = update.message.chatId.toString()

            val respuesta =
                    when {
                        messageText.startsWith("/inventario") -> formatInventory()
                        messageText.startsWith("/resumen") -> formatSummary()
                        messageText.startsWith("/insertar") -> handleInsert(messageText)
                        else ->
                                "¡Hola! Soy el asistente de la tienda de Doña Rosa. Usa /inventario para ver los productos o /resumen para el valor total."
                    }

            val response =
                    SendMessage.builder()
                            .chatId(chatId)
                            .text(respuesta)
                            .parseMode("Markdown")
                            .build()

            try {
                telegramClient.execute(response)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    private fun formatInventory(): String {
        val products = inventoryService.getAllProducts()
        val sb = StringBuilder("📦 *Inventario Actual:*\n\n")
        products.forEach { p ->
            val lowStockAlert =
                    if (p.quantity <= p.initialQuantity * 0.1) " ⚠️ (Bajo stock)" else ""
            sb.append("${p.code}. ${p.name} - $${p.price} | Cant: ${p.quantity}$lowStockAlert\n")
        }
        return sb.toString()
    }

    private fun formatSummary(): String {
        val totalValue = inventoryService.calculateTotalInventoryValue()
        val lowStock = inventoryService.getLowStockProducts()
        val lowStockNames =
                if (lowStock.isEmpty()) "¡Ninguno!" else lowStock.joinToString { it.name }

        return """
            📊 *Resumen de Tienda:*
            💰 Valor Total: $$totalValue
            ⚠️ Productos con bajo stock (10%): $lowStockNames
        """.trimIndent()
    }

    private fun handleInsert(text: String): String {
        // Formato esperado: /insertar NOMBRE PRECIO CANTIDAD
        // Ejemplo: /insertar Papas 2500 100
        val parts = text.split(" ")
        if (parts.size < 4) {
            return "❌ Formato incorrecto. Usa: `/insertar NOMBRE PRECIO CANTIDAD`\n\nEjemplo: `/insertar Papas 2500 100`"
        }

        return try {
            val name = parts[1]
            val price = parts[2].toDouble()
            val qty = parts[3].toInt()

            inventoryService.insertProduct(name, price, qty)
            "✅ ¡Listo! El producto *${name}* ha sido agregado con un código automático."
        } catch (e: Exception) {
            "❌ Error: Asegúrate de que el precio y cantidad sean números."
        }
    }
}

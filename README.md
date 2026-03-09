# 🍎 Bot de Inventario - Tienda Doña Rosa

Este es un asistente de Telegram desarrollado en **Kotlin** con **Spring Boot** para ayudar a Doña Rosa a gestionar su tienda de barrio de forma sencilla y persistente.

## 🚀 Funcionalidades Principales

- **📦 Gestión de Inventario**: Ver la lista completa de productos, precios y cantidades actuales.
- **💰 Resumen Financiero**: Cálculo automático del valor total de toda la mercancía en tienda.
- **💳 Registro de Ventas**: Descuenta el stock automáticamente al realizar una venta.
- **⚠️ Alertas Proactivas**: Notifica inmediatamente cuando un producto baja del 10% de su stock inicial.
- **💾 Persistencia Real**: Utiliza una base de datos **H2** para que los datos no se pierdan al reiniciar el bot.
- **🛠️ CRUD Completo**: Permite insertar, actualizar y eliminar productos mediante comandos simples.

## ⌨️ Comandos Disponibles

- `/inventario` - Ver todos los productos.
- `/resumen` - Ver valor total y productos con bajo stock.
- `/insertar NOMBRE PRECIO CANTIDAD` - Agregar un nuevo producto.
- `/actualizar CODIGO CANTIDAD PRECIO` - Modificar un producto existente.
- `/eliminar CODIGO` - Borrar un producto.
- `/venta CODIGO CANTIDAD` - Registrar una venta (activa alertas de stock).

## 🛡️ Seguridad de Datos
Los datos se almacenan localmente en una base de datos cifrada por el sistema de archivos del servidor, garantizando que solo el administrador del bot tiene acceso a la información comercial de Doña Rosa.

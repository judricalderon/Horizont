# 📈 Horizont – Backend de Cotizaciones en Tiempo Real con Finnhub

Este proyecto es un backend desarrollado en **Spring Boot** que permite consultar el **precio actual de acciones** en tiempo real utilizando la API pública de [Finnhub](https://finnhub.io).

## 🧠 Funcionalidades

- Consultar el precio actual (`currentPrice`) de una acción.
- Consultar múltiples acciones a la vez.
- Exponer un endpoint RESTful para integrarse fácilmente con un frontend (ej: React).
- Recibir respuestas estructuradas con nombres de campos legibles (`currentPrice`, `openPrice`, etc.).
- Preparado para escalar con otras APIs como Alpaca.

---

## 🏗️ Arquitectura

```text
[ Controller ]
     ↓
[ MarketService ] ← (interfaz: IMarketService)
     ↓
[ FinnhubClient ] ← (interfaz: IFinnhubClient)
     ↓
[ API Finnhub ]

// API base del backend Spring Boot
const BASE_URL = 'http://localhost:8080/api';

class TradingApi {
    // Obtener cotización de símbolo específico (Finnhub)
    async getSymbolInfo(symbol) {
        try {
          const response = await fetch(`http://localhost:8080/api/market/quotes?symbols=${symbol}`);
          const data = await response.json();
          const quote = data[0];
      
          return {
            symbol: quote.symbol,
            price: quote.quote?.c ?? 0,
            change: (quote.quote?.c ?? 0) - (quote.quote?.pc ?? 0),
            open: quote.quote?.o ?? 0,
            high: quote.quote?.h ?? 0,
            low: quote.quote?.l ?? 0,
            volume: 0
          };
        } catch (error) {
          console.error('Error fetching symbol info:', error);
          return {
            symbol,
            price: 0,
            change: 0,
            open: 0,
            high: 0,
            low: 0,
            volume: 0
          };
        }
      }

    // Obtener lista de cotizaciones (símbolos predefinidos)
    async getSymbols() {
        const predefinedSymbols = ['AAPL', 'GOOGL', 'MSFT', 'TSLA'];
        const results = await Promise.all(predefinedSymbols.map(s => this.getSymbolInfo(s)));
        return results;
    }

    // Simulación de orden (aún no implementado en el backend)
    async placeOrder(orderData) {
        // TODO: Implementar con backend real
        return {
            success: true,
            orderId: crypto.randomUUID()
        };
    }

    // Obtener posiciones del portafolio (simulado)
    async getPositions() {
        // TODO: reemplazar por datos reales del backend
        return [
            {
                symbol: 'AAPL',
                quantity: 10,
                averagePrice: 150,
                totalValue: 1570,
                pnl: 70
            },
            {
                symbol: 'GOOGL',
                quantity: 5,
                averagePrice: 2700,
                totalValue: 2750,
                pnl: 50
            }
        ];
    }

    // Obtener historial de órdenes (simulado)
    async getOrderHistory() {
        // TODO: reemplazar por datos reales del backend
        return [
            {
                date: new Date().toISOString(),
                symbol: 'AAPL',
                type: 'market',
                quantity: 10,
                price: 150,
                status: 'Ejecutada'
            }
        ];
    }
}

export const tradingApi = new TradingApi();

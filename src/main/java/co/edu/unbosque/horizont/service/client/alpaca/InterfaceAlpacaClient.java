package co.edu.unbosque.horizont.service.client.alpaca;

import co.edu.unbosque.horizont.dto.client.alpaca.AlpacaAccountDTO;
import co.edu.unbosque.horizont.dto.client.alpaca.AlpacaOrderResultDTO;
import co.edu.unbosque.horizont.entity.TipoOrden;
import org.springframework.http.ResponseEntity;
/**
 * Define el contrato para los servicios de integración con la API de Alpaca.
 *
 * <p>Esta interfaz permite desacoplar la lógica de negocio de la implementación concreta
 * de llamadas a la API externa. Facilita la inyección de dependencias, pruebas unitarias
 * y futuras extensiones o cambios en la lógica de comunicación.</p>
 */
public interface InterfaceAlpacaClient {
    /**
     * Crea una cuenta en Alpaca utilizando los datos proporcionados.
     *
     * @param cuenta objeto {@link AlpacaAccountDTO} con la información requerida por la API de Alpaca
     * @return {@link ResponseEntity} con la respuesta de Alpaca en formato JSON, o un mensaje de error
     */
    ResponseEntity<String> crearCuenta(AlpacaAccountDTO cuenta);

    double getLastPrice(String simbolo);

    AlpacaOrderResultDTO placeOrder(String symbol, int quantity, TipoOrden tipo);
}

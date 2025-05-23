package co.edu.unbosque.horizont.service.client.alpaca;

import co.edu.unbosque.horizont.dto.client.alpaca.AlpacaAccountDTO;

import co.edu.unbosque.horizont.dto.client.alpaca.AlpacaOrderResultDTO;
import co.edu.unbosque.horizont.entity.TipoOrden;
import co.edu.unbosque.horizont.service.internal.TradeServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;


import java.util.Map;

/**
 * Servicio que implementa la comunicación con la API externa de Alpaca para la creación de cuentas de usuario.
 * <p>
 * Utiliza {@link RestTemplate} para realizar solicitudes HTTP autenticadas hacia la API de Alpaca,
 * enviando la información del usuario encapsulada en un {@link AlpacaAccountDTO}.
 * </p>
 */

@Service
public class AlpacaClient implements InterfaceAlpacaClient {

    private static final Logger log = LoggerFactory.getLogger(TradeServiceImpl.class);
    /**
     * Clave pública de la API de Alpaca, inyectada desde el archivo de configuración.
     */
    @Value("${Alpaca_api_key}")
    private String apiKey;
    /**
     * Clave secreta de la API de Alpaca, inyectada desde el archivo de configuración.
     */
    @Value("${Alpaca_api_secret}")
    private String apiSecret;
    /**
     * Cliente HTTP para realizar las solicitudes REST.
     */
    private final RestTemplate restTemplate;
    /**
     * URL base para la creación de cuentas en el entorno sandbox de Alpaca.
     */
    private final String ALPACA_URL = "https://broker-api.sandbox.alpaca.markets/v1/accounts";
    /**
     * Constructor que inicializa el {@link RestTemplate} a partir de un {@link RestTemplateBuilder}.
     *
     * @param builder objeto utilizado para construir el {@code RestTemplate}
     */

    // Para trading de mercado (paper)
    @Value("${alpaca.paper.api_key}")
    private String paperApiKey;
    @Value("${alpaca.paper.api_secret}")
    private String paperApiSecret;
    @Value("${alpaca.paper.api_url}")
    private String paperUrl;

    public AlpacaClient(RestTemplateBuilder builder) {
        this.restTemplate = builder.build();
    }
    /**
     * Crea una cuenta de usuario en Alpaca enviando los datos provistos en un {@link AlpacaAccountDTO}.
     *
     * @param cuenta objeto con la información estructurada del usuario a registrar
     * @return {@link ResponseEntity} con el resultado de la operación, incluyendo el cuerpo de la respuesta
     *         o un mensaje de error en caso de fallo
     */
@Override
    public ResponseEntity<String> crearCuenta(AlpacaAccountDTO cuenta) {
        try {
            // Convertir a JSON para revisión

            ObjectMapper mapper = new ObjectMapper();
            String json = mapper.writeValueAsString(cuenta);
            System.out.println("JSON enviado a Alpaca:");
            System.out.println(json);

            // Configurar headers
            HttpHeaders headers = new HttpHeaders();
            headers.setBasicAuth(apiKey, apiSecret);
            headers.setContentType(MediaType.APPLICATION_JSON);

            // Armar petición
            HttpEntity<AlpacaAccountDTO> request = new HttpEntity<>(cuenta, headers);

            // Hacer POST
            ResponseEntity<String> response = restTemplate.postForEntity(ALPACA_URL, request, String.class);


            // 💡 NUEVO: imprimir respuesta de Alpaca

            System.out.println("Respuesta HTTP Alpaca: " + response.getStatusCode());
            System.out.println("Body de la respuesta Alpaca: " + response.getBody());

            return response;
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al serializar JSON de la cuenta Alpaca: " + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al crear cuenta en Alpaca: " + e.getMessage());
        }
    }
    public double getLastPrice(String simbolo) {
        // Implementación real usando la API de Alpaca
        // o devuelve un valor simulado por ahora
        return 123.45; // ejemplo temporal
    }

    @Override
    public AlpacaOrderResultDTO placeOrder(String symbol, int quantity, TipoOrden tipo) {
        // Construimos la URL para Paper-Trading
        String url = paperUrl + "/orders";

        HttpHeaders headers = new HttpHeaders();
        headers.add("APCA-API-KEY-ID",    paperApiKey);
        headers.add("APCA-API-SECRET-KEY", paperApiSecret);
        headers.setContentType(MediaType.APPLICATION_JSON);

        Map<String,Object> body = Map.of(
                "symbol",        symbol,
                "qty",           quantity,
                "side",          (tipo == TipoOrden.COMPRA ? "buy" : "sell"),
                "type",          "market",
                "time_in_force", "gtc"
        );

        HttpEntity<Map<String,Object>> req = new HttpEntity<>(body, headers);

        try {
            ResponseEntity<AlpacaOrderResultDTO> resp =
                    restTemplate.postForEntity(url, req, AlpacaOrderResultDTO.class);

            if (resp.getStatusCode().is2xxSuccessful() && resp.getBody() != null) {
                return resp.getBody();
            }
            throw new RuntimeException("Orden rechazada: " + resp.getStatusCode());

        } catch (HttpClientErrorException.Forbidden forb) {
            log.error("Alpaca Paper Trading 403 Forbidden: {}", forb.getResponseBodyAsString());
            throw forb;
        }

    }}
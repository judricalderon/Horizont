package co.edu.unbosque.horizont.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Manejador global de excepciones relacionadas con validaciones de argumentos.
 *
 * Esta clase captura excepciones del tipo {@link IllegalArgumentException} en cualquier controlador
 * y genera una respuesta estandarizada para el cliente.
 */
@ControllerAdvice
public class ValidationExceptionHandler {
    /**
     * Maneja excepciones de tipo {@link IllegalArgumentException}.
     *
     * Crea una respuesta de error con el mensaje de la excepción y el código HTTP 400 (Bad Request),
     * permitiendo enviar información clara al cliente sobre errores de validación o argumentos inválidos.
     *
     * @param ex la excepción capturada.
     * @return una respuesta HTTP con el objeto {@link BaseResponse} conteniendo el error.
     */


@ControllerAdvice
public class ValidationExceptionHandler {


    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<BaseResponse> handleIllegalArgument(IllegalArgumentException ex) {
        BaseResponse response = new BaseResponse(
                ex.getMessage(),
                400
        );
        return ResponseEntity.badRequest().body(response);
    }
}

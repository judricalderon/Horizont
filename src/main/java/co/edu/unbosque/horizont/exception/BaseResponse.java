package co.edu.unbosque.horizont.exception;

import org.modelmapper.spi.ErrorMessage;
/**
 * Clase base para estructurar las respuestas de la API.
 *
 * Proporciona un formato consistente para devolver mensajes, códigos de estado
 * y detalles opcionales de error hacia los clientes que consumen la API.
 */
public class BaseResponse {
    /** Mensaje descriptivo de la operación realizada o del error producido. */
    private String mensaje;

    /** Código de estado asociado a la respuesta, puede ser código HTTP o uno interno. */
    private int codigo;

    /** Información adicional de error en caso de que ocurra un problema. */
    private ErrorMessage errorMensaje;

    /**
     * Constructor completo que inicializa el mensaje, el código y el error.
     *
     * @param mensaje descripción del resultado de la operación.
     * @param codigo código de estado de la respuesta.
     * @param errorMensaje objeto que contiene información adicional del error.
     */
    public BaseResponse(String mensaje, int codigo, ErrorMessage errorMensaje) {
        this.mensaje = mensaje;
        this.codigo = codigo;
        this.errorMensaje = errorMensaje;
    }
    /**
     * Constructor que inicializa solo el mensaje y el código.
     *
     * @param mensaje descripción del resultado de la operación.
     * @param codigo código de estado de la respuesta.
     */
    public BaseResponse(String mensaje, int codigo) {
        this.mensaje = mensaje;
        this.codigo = codigo;
    }
    /**
     * Constructor vacío requerido para la deserialización.
     */
    public BaseResponse() {
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public ErrorMessage getErrorMensaje() {
        return errorMensaje;
    }

    public void setErrorMensaje(ErrorMessage errorMensaje) {
        this.errorMensaje = errorMensaje;
    }
}
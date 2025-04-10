package co.edu.unbosque.horizont.exception;

import org.modelmapper.spi.ErrorMessage;

public class BaseResponse {
    private String mensaje;
    private int codigo;
    private ErrorMessage errorMensaje;

    public BaseResponse(String mensaje, int codigo, ErrorMessage errorMensaje) {
        this.mensaje = mensaje;
        this.codigo = codigo;
        this.errorMensaje = errorMensaje;
    }

    public BaseResponse(String mensaje, int codigo) {
        this.mensaje = mensaje;
        this.codigo = codigo;
    }

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
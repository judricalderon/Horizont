package co.edu.unbosque.horizont.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

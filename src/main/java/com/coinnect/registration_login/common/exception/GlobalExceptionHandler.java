package com.coinnect.registration_login.common.exception;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleResourceNotFoundException(ResourceNotFoundException ex) {
        return buildResponse(HttpStatus.NOT_FOUND, List.of(ex.getMessage()));
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<Map<String, Object>> handleBadCredentialsException(BadCredentialsException ex) {
        return buildResponse(HttpStatus.UNAUTHORIZED, List.of("Credenciales inválidas. Intenta de nuevo."));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        List<String> errors = ex.getBindingResult().getFieldErrors().stream()
                .map(error -> String.format("%s: %s", error.getField(), error.getDefaultMessage()))
                .collect(Collectors.toList());
        return buildResponse(HttpStatus.BAD_REQUEST, errors);
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<Map<String, Object>> handleAuthenticationException(AuthenticationException ex) {
        return buildResponse(HttpStatus.UNAUTHORIZED, List.of("No has iniciado sesión correctamente. Por favor, inicia sesión."));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleGenericException(Exception ex) {
        return buildResponse(HttpStatus.INTERNAL_SERVER_ERROR, List.of("Ha ocurrido un error inesperado." + ex.getMessage()));
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, Object>> handleIllegalArgumentException(IllegalArgumentException ex) {
        return buildResponse(HttpStatus.BAD_REQUEST, List.of(ex.getMessage()));
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<Map<String, Object>> handleAccessDeniedException(AccessDeniedException ex) {
        return buildResponse(HttpStatus.FORBIDDEN, List.of("Acceso denegado."));
    }

    @ExceptionHandler(ResourceAlreadyExistsException.class)
    public ResponseEntity<Map<String, Object>> handleResourceAlreadyExistsException(ResourceAlreadyExistsException ex) {
        return buildResponse(HttpStatus.CONFLICT, List.of(ex.getMessage()));
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<Map<String, Object>> handleDataIntegrityViolationException(DataIntegrityViolationException ex) {
        return buildResponse(HttpStatus.CONFLICT, List.of("Conflicto de integridad de datos: " + ex.getMessage()));
    }

    private ResponseEntity<Map<String, Object>> buildResponse(HttpStatus status, List<String> errors) {
        String statusDescription = getStatusDescription(status);
        Map<String, Object> body = Map.of(
                "status", status.value(),
                "status_description", statusDescription,
                "errors", errors,
                "timestamp", Instant.now()
        );
        return ResponseEntity.status(status).body(body);
    }

    private String getStatusDescription(HttpStatus status) {
        switch (status) {
            case UNAUTHORIZED:
                return "No autorizado. El cliente no ha proporcionado credenciales válidas o no tiene permiso para acceder al recurso.";
            case FORBIDDEN:
                return "Prohibido. El cliente no tiene los permisos necesarios para acceder al recurso.";
            case NOT_FOUND:
                return "No encontrado. El recurso solicitado no se pudo encontrar.";
            case BAD_REQUEST:
                return "Solicitud incorrecta. La solicitud del cliente contiene errores o está mal formada.";
            case INTERNAL_SERVER_ERROR:
                return "Error interno del servidor. Ocurrió un problema en el servidor.";
            case CONFLICT:
                return "Conflicto. Hay un conflicto en la solicitud, como violación de reglas de integridad de datos.";
            default:
                return "Error desconocido.";
        }
    }
}

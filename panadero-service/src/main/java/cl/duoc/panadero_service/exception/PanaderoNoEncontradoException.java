package cl.duoc.panadero_service.exception;

public class PanaderoNoEncontradoException extends RuntimeException {
    public PanaderoNoEncontradoException(String mensaje){
        super(mensaje);
    }
}

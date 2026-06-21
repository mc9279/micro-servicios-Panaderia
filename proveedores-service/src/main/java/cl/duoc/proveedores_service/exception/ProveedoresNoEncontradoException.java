package cl.duoc.proveedores_service.exception;

public class ProveedoresNoEncontradoException extends RuntimeException{
    public ProveedoresNoEncontradoException(String mensaje){
        super(mensaje);
    }
}

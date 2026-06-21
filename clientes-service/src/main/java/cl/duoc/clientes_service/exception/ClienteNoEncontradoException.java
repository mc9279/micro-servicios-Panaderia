package cl.duoc.clientes_service.exception;

public class ClienteNoEncontradoException extends  RuntimeException{
    public ClienteNoEncontradoException(String mensaje){
        super(mensaje);
    }
}

package cl.duoc.inventario_service.exception;

public class InventarioNoEncontradoException extends  RuntimeException{
    public InventarioNoEncontradoException(String mensaje){
        super(mensaje);
    }
}

package org.example.excepcion;

public class TareaInexistenteException extends Exception{
    public TareaInexistenteException(String mensaje){
        super(mensaje);
    }
}

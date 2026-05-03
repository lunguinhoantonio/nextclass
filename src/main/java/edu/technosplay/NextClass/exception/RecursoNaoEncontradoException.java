package edu.technosplay.NextClass.exception;

public class RecursoNaoEncontradoException extends RuntimeException {
    public RecursoNaoEncontradoException(String message) {
        super(message);
    }
    public RecursoNaoEncontradoException(String recurso, Long id) {
        super(String.format("%s com id %d não encontrado", recurso, id));
    }
}

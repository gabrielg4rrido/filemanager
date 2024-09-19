package com.gg.filemanager.service.exception;

public class ObjectNotFoundException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public ObjectNotFoundException(Long id) {
        super("Objeto com ID " + id + " não encontrado.");
    }
}
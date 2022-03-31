package br.com.alura.alurabank.controller.exceptions;

public class ContaNaoEncontradaException extends IllegalArgumentException {

    public ContaNaoEncontradaException(String s) {
        super(s);
    }
}

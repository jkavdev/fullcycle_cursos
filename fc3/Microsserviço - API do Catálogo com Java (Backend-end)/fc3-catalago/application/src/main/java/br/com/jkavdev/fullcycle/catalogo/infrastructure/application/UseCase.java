package br.com.jkavdev.fullcycle.catalogo.infrastructure.application;

public abstract class UseCase<IN, OUT> {

    public abstract OUT execute(IN anIn);
}

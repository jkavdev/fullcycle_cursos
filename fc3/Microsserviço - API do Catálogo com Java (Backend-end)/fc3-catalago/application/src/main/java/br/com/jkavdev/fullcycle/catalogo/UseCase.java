package br.com.jkavdev.fullcycle.catalogo;

public abstract class UseCase<IN, OUT> {

    public abstract OUT execute(IN anIn);
}

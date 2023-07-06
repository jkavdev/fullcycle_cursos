package br.com.jkavdev.fullcycle.catalogo.infrastructure.application;

public abstract class UnityUseCase<IN> {

    public abstract void execute(IN anIn);
}

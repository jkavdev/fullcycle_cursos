package br.com.jkavdev.fullcycle.admin.catalogo.domain;

public abstract class AggregateRoot<ID extends Indetifier> extends Entity<ID> {

    protected AggregateRoot(final ID id) {
        super(id);
    }
}

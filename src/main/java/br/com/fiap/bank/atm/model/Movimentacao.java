package br.fiap.bank.atm.model;

import java.time.LocalDateTime;

// Representa cada registro do histórico da conta (extrato).
// Toda vez que acontece algo na conta, um objeto dessa classe é criado e salvo.
public class Movimentacao extends BaseEntity {

    private LocalDateTime dataHora;
    private TipoMovimentacao tipo;
    private Dinheiro valor;

    public Movimentacao(LocalDateTime dataHora, Dinheiro valor, TipoMovimentacao tipo) {
        super();
        this.dataHora = dataHora;
        this.valor = valor;
        this.tipo = tipo;
    }

    public LocalDateTime getDataHora() {
        return dataHora;
    }

    public Dinheiro getValor() {
        return valor;
    }

    public TipoMovimentacao getTipo() {
        return tipo;
    }

    // A igualdade é pelo id herdado de BaseEntity,
    // porque cada movimentação é única mesmo que tenha o mesmo valor e tipo.
    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }
}

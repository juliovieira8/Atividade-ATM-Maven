package br.fiap.bank.atm.model;

import java.math.BigDecimal;

// Conta Corrente: cobra uma taxa de R$25 a cada saque realizado.
// Herda de Conta e só precisa implementar a regra de taxa que é diferente da Poupança.
public class ContaCorrente extends Conta {

    // Defini como constante para não ter número mágico solto no código.
    private static final Double TAXA_MANUTENCAO = 25.00;

    public ContaCorrente(Cliente cliente, ContaAcesso contaAcesso, Dinheiro saldo) {
        // Passa a taxa para a classe pai, assim ela fica registrada no atributo taxa da Conta.
        super(cliente, contaAcesso, saldo, TAXA_MANUTENCAO);
    }

    // Esse método é chamado automaticamente após cada saque (a lógica está em Conta.realizarSaque).
    // Só desconta a taxa se o saldo for suficiente para cobri-la.
    @Override
    protected void aplicarRegraDeTaxa() {
        Dinheiro taxaSaque = new Dinheiro(BigDecimal.valueOf(TAXA_MANUTENCAO));
        if (this.saldo.maiorOuIgualQue(taxaSaque)) {
            this.saldo = this.saldo.subtrair(taxaSaque);
            registrarMovimentacao(taxaSaque, TipoMovimentacao.TAXA);
        }
    }

    // Esse método é separado do aplicarRegraDeTaxa para não cobrar a taxa duas vezes.
    // O aplicarRegraDeTaxa é chamado no saque, esse aqui é chamado manualmente todo mês.
    public void aplicarTaxaMensal() {
        Dinheiro taxaMensal = new Dinheiro(BigDecimal.valueOf(TAXA_MANUTENCAO));
        if (this.saldo.maiorOuIgualQue(taxaMensal)) {
            this.saldo = this.saldo.subtrair(taxaMensal);
            registrarMovimentacao(taxaMensal, TipoMovimentacao.TAXA);
        }
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }
}

package br.fiap.bank.atm.model;

// Enum que categoriza cada movimentação do extrato.
// TAXA é usado pela ContaCorrente (desconto por saque) e
// RENDIMENTO é usado pela ContaPoupanca (juros mensais).
public enum TipoMovimentacao {
    DEPOSITO, SAQUE, TAXA, RENDIMENTO
}

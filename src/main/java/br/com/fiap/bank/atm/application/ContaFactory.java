package br.fiap.bank.atm.application;

import br.fiap.bank.atm.model.Cliente;
import br.fiap.bank.atm.model.Conta;
import br.fiap.bank.atm.model.ContaAcesso;
import br.fiap.bank.atm.model.ContaCorrente;
import br.fiap.bank.atm.model.ContaPoupanca;
import br.fiap.bank.atm.model.Dinheiro;

// Factory responsável por criar os diferentes tipos de conta.
// Usei o padrão Factory para que o resto do sistema não precise saber qual classe concreta
// instanciar — só pede "quero uma conta corrente" e a factory resolve.
public class ContaFactory {

    // Singleton: só existe uma instância da factory no sistema inteiro.
    // O construtor privado impede que alguém crie outra instância por fora.
    private static ContaFactory instance;

    private ContaFactory() {}

    public static ContaFactory getInstance() {
        if (instance == null) {
            instance = new ContaFactory();
        }
        return instance;
    }

    // Retorna Conta (tipo pai) em vez de ContaCorrente para que quem chama
    // não precise depender do tipo específico — só usa a interface de Conta.
    public Conta criarContaCorrente(Cliente cliente, ContaAcesso contaAcesso, Dinheiro saldo) {
        return new ContaCorrente(cliente, contaAcesso, saldo);
    }

    public Conta criarContaPoupanca(Cliente cliente, ContaAcesso contaAcesso, Dinheiro saldo) {
        return new ContaPoupanca(cliente, contaAcesso, saldo);
    }
}

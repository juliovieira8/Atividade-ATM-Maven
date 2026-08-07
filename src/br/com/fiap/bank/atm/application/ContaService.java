package br.fiap.bank.atm.application;

import br.fiap.bank.atm.model.Conta;
import br.fiap.bank.atm.model.Dinheiro;
import br.fiap.bank.atm.model.Movimentacao;

import java.util.List;

// Camada de serviço que fica entre o terminal e o modelo.
// O terminal não chama a Conta diretamente — passa pelo service.
// Isso evita que a tela saiba demais sobre como a conta funciona por dentro.
public class ContaService {

    private Conta conta;

    public ContaService(Conta conta) {
        this.conta = conta;
    }

    public void realizarDeposito(Dinheiro valor) {
        conta.realizarDeposito(valor);
    }

    public void realizarSaque(Dinheiro valor) {
        conta.realizarSaque(valor);
    }

    public Dinheiro obterSaldo() {
        return conta.getSaldo();
    }

    public List<Movimentacao> obterMovimentacoes() {
        return conta.getMovimentacoes();
    }

    // Busco só o primeiro nome para cumprimentar o usuário de forma mais amigável no terminal.
    public String obterNomeCliente() {
        return conta.getCliente().obterPrimeiroNome();
    }
}

package br.fiap.bank.atm.application;

import br.fiap.bank.atm.model.Conta;

// Serviço responsável apenas pela autenticação.
// Separei de ContaService para deixar claro que autenticação e operações financeiras
// são responsabilidades diferentes — cada classe faz uma coisa só.
public class AutorizacaoService {

    private Conta conta;

    public AutorizacaoService(Conta conta) {
        this.conta = conta;
    }

    // Delega a validação para ContaAcesso, que é quem conhece a senha e controla as tentativas.
    public Boolean autorizar(String senha) {
        return conta.getContaAcesso().validarSenha(senha);
    }

    // O terminal usa esse método para saber se deve exibir mensagem de bloqueio.
    public Boolean isContaBloqueada() {
        return conta.getContaAcesso().isBloqueado();
    }
}

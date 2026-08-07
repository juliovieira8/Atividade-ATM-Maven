package br.fiap.bank.atm;

import java.util.Scanner;

import br.fiap.bank.atm.application.AutorizacaoService;
import br.fiap.bank.atm.application.ContaService;
import br.fiap.bank.atm.model.Conta;
import br.fiap.bank.atm.presentation.CadastrarContaAcessoController;
import br.fiap.bank.atm.presentation.TerminalBancarioController;

public class Main {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        CadastrarContaAcessoController cadastrarContaAcessoController = new CadastrarContaAcessoController();
        cadastrarContaAcessoController.iniciar();

        Conta conta = cadastrarContaAcessoController.getConta();

        ContaService contaService = new ContaService(conta);
        AutorizacaoService autorizacaoService = new AutorizacaoService(conta);

        TerminalBancarioController terminal = new TerminalBancarioController(contaService, autorizacaoService);
        terminal.iniciar();

        scanner.close();
    }
}

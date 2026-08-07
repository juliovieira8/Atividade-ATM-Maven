package br.fiap.bank.atm.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

// Classe abstrata que serve de base para ContaCorrente e ContaPoupanca.
// Coloquei aqui tudo que é comum entre os dois tipos de conta para não repetir código.
// Só o comportamento de taxa é diferente — cada subclasse implementa do seu jeito.
public abstract class Conta extends BaseEntity {

    // Protected para que as subclasses consigam acessar diretamente,
    // por exemplo ContaPoupanca precisa mexer no saldo para aplicar o rendimento.
    protected Cliente cliente;
    protected Dinheiro saldo;
    protected Double taxa;
    protected StatusConta status;
    protected LocalDate dataAbertura;
    protected ContaAcesso contaAcesso;
    protected List<Movimentacao> movimentacoes;

    public Conta(Cliente cliente, ContaAcesso contaAcesso, Dinheiro saldo, Double taxa) {
        super();
        // Validações logo no construtor para garantir que nenhum objeto inválido seja criado.
        // Se não fizer isso aqui, o NullPointerException aparece em outro lugar sem contexto.
        if (cliente == null) {
            throw new IllegalArgumentException("Cliente não pode ser nulo.");
        }
        if (contaAcesso == null) {
            throw new IllegalArgumentException("ContaAcesso não pode ser nulo.");
        }
        if (saldo == null) {
            throw new IllegalArgumentException("Saldo não pode ser nulo.");
        }
        this.cliente = cliente;
        this.contaAcesso = contaAcesso;
        this.saldo = saldo;
        this.taxa = taxa;
        this.status = StatusConta.ATIVA;
        this.dataAbertura = LocalDate.now();
        this.movimentacoes = new ArrayList<>();
    }

    // Método público chamado de fora. Ele verifica o status e depois delega
    // para os métodos privados sacar() e aplicarRegraDeTaxa().
    public void realizarSaque(Dinheiro valor) {
        if (this.status != StatusConta.ATIVA) {
            throw new IllegalStateException("Operação não permitida. A conta está " + this.status + ".");
        }
        sacar(valor);
        // Chama o método abstrato — cada subclasse decide o que acontece aqui.
        aplicarRegraDeTaxa();
    }

    public void realizarDeposito(Dinheiro valor) {
        if (this.status != StatusConta.ATIVA) {
            throw new IllegalStateException("Operação não permitida. A conta está " + this.status + ".");
        }
        depositar(valor);
    }

    public void bloquear() {
        // Não faz sentido bloquear uma conta que já foi encerrada.
        if (this.status == StatusConta.ENCERRADA) {
            throw new IllegalStateException("Não é possível bloquear uma conta encerrada.");
        }
        this.status = StatusConta.BLOQUEADA;
    }

    public void encerrar() {
        this.status = StatusConta.ENCERRADA;
    }

    // Privado porque ninguém de fora deve chamar diretamente — tem que passar pelo realizarDeposito.
    private void depositar(Dinheiro valor) {
        if (valor == null || valor.menorOuIgualQue(new Dinheiro("0"))) {
            throw new IllegalArgumentException("Valor de depósito deve ser maior que zero.");
        }
        this.saldo = this.saldo.adicionar(valor);
        registrarMovimentacao(valor, TipoMovimentacao.DEPOSITO);
    }

    // Privado pelo mesmo motivo do depositar.
    private void sacar(Dinheiro valor) {
        if (valor == null || valor.menorOuIgualQue(new Dinheiro("0"))) {
            throw new IllegalArgumentException("Valor de saque deve ser maior que zero.");
        }
        if (valor.maiorQue(this.saldo)) {
            throw new IllegalArgumentException("Saldo insuficiente para realizar o saque.");
        }
        this.saldo = this.saldo.subtrair(valor);
        registrarMovimentacao(valor, TipoMovimentacao.SAQUE);
    }

    // Método abstrato — força ContaCorrente e ContaPoupanca a implementarem
    // cada uma do seu jeito. Isso é polimorfismo na prática.
    protected abstract void aplicarRegraDeTaxa();

    // Protected para que as subclasses também possam registrar movimentações,
    // como ContaPoupanca que registra o rendimento mensal.
    protected void registrarMovimentacao(Dinheiro valor, TipoMovimentacao tipo) {
        movimentacoes.add(new Movimentacao(LocalDateTime.now(), valor, tipo));
    }

    public Dinheiro getSaldo() {
        return saldo;
    }

    public Double getTaxa() {
        return taxa;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public LocalDate getDataAbertura() {
        return dataAbertura;
    }

    public StatusConta getStatus() {
        return status;
    }

    public ContaAcesso getContaAcesso() {
        return contaAcesso;
    }

    // Retorna uma versão somente leitura da lista para que ninguém consiga
    // adicionar ou remover movimentações por fora da classe.
    public List<Movimentacao> getMovimentacoes() {
        return Collections.unmodifiableList(movimentacoes);
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }
}

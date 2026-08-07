package br.fiap.bank.atm.model;

import java.math.BigDecimal;
import java.math.RoundingMode;

// Criei essa classe para representar valores monetários de forma segura.
// Não usei double porque double tem erro de precisão (ex: 0.1 + 0.2 = 0.30000000000000004),
// o que seria um problema grave num sistema bancário. BigDecimal resolve isso.
public class Dinheiro {

    private BigDecimal valor;

    public Dinheiro(BigDecimal valor) {
        // setScale(2) garante que o valor sempre tenha duas casas decimais.
        // HALF_UP é o arredondamento padrão (ex: 2.225 vira 2.23).
        this.valor = valor.setScale(2, RoundingMode.HALF_UP);
    }

    // Construtor extra que aceita String para facilitar na hora de criar valores no código,
    // como new Dinheiro("1000.00"), sem precisar criar um BigDecimal na mão.
    public Dinheiro(String valor) {
        this(new BigDecimal(valor));
    }

    // Os métodos abaixo retornam um objeto Dinheiro novo em vez de alterar o atual.
    // Aprendi que isso se chama imutabilidade — evita bugs porque o valor original nunca muda.
    public Dinheiro adicionar(Dinheiro outro) {
        return new Dinheiro(this.valor.add(outro.valor));
    }

    public Dinheiro subtrair(Dinheiro outro) {
        return new Dinheiro(this.valor.subtract(outro.valor));
    }

    // Uso compareTo em vez de > ou < porque BigDecimal não suporta esses operadores diretamente.
    // compareTo retorna -1, 0 ou 1, então comparo com > 0, == 0 ou < 0.
    public Boolean maiorQue(Dinheiro outro) {
        return this.valor.compareTo(outro.valor) > 0;
    }

    public Boolean maiorOuIgualQue(Dinheiro outro) {
        return this.valor.compareTo(outro.valor) >= 0;
    }

    public Boolean menorOuIgualQue(Dinheiro outro) {
        return this.valor.compareTo(outro.valor) <= 0;
    }

    public Boolean menorQue(Dinheiro outro) {
        return this.valor.compareTo(outro.valor) < 0;
    }

    public BigDecimal getValor() {
        return valor;
    }

    // Uso compareTo no equals também porque BigDecimal considera 2.20 e 2.2 diferentes,
    // mas pra nós são o mesmo valor monetário.
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Dinheiro dinheiro = (Dinheiro) obj;
        return this.valor.compareTo(dinheiro.valor) == 0;
    }

    @Override
    public int hashCode() {
        return this.valor.hashCode();
    }

    // Formata o valor como moeda brasileira na hora de exibir no terminal.
    @Override
    public String toString() {
        return String.format("R$ %.2f", valor);
    }
}

package br.fiap.bank.atm.model;

// Representa o cliente do banco. Estende BaseEntity para já ter id e data de criação.
public class Cliente extends BaseEntity {

    private String nomeCompleto;

    public Cliente(String nomeCompleto) {
        super();
        // Coloquei essa validação porque não faz sentido criar um cliente sem nome.
        // O trim() remove espaços em branco, então um nome só com espaços também é inválido.
        if (nomeCompleto == null || nomeCompleto.trim().isEmpty()) {
            throw new IllegalArgumentException("Nome completo é obrigatório.");
        }
        this.nomeCompleto = nomeCompleto;
    }

    // Usei split(" ") para pegar só o primeiro nome ao invés do nome completo,
    // fica mais amigável na hora de cumprimentar o usuário no terminal.
    public String obterPrimeiroNome() {
        return nomeCompleto.split(" ")[0];
    }

    public String getNomeCompleto() {
        return nomeCompleto;
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }
}

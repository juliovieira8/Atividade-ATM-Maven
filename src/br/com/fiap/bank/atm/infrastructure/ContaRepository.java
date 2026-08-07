package br.fiap.bank.atm.infrastructure;

import br.fiap.bank.atm.model.Conta;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

// Repository é responsável por guardar e buscar as contas.
// Por enquanto os dados ficam em memória (HashMap), mas a ideia é que
// em um sistema real isso se conectaria com um banco de dados.
public class ContaRepository {

    // Usei HashMap com UUID como chave para busca eficiente por id.
    private Map<UUID, Conta> contas;

    public ContaRepository() {
        this.contas = new HashMap<>();
    }

    // Se já existir uma conta com o mesmo id, sobrescreve — funciona como update também.
    public void salvar(Conta conta) {
        contas.put(conta.getId(), conta);
    }

    public Conta buscarPorId(UUID id) {
        return contas.get(id);
    }

    // Útil para checar se uma conta existe antes de tentar operar nela.
    public Boolean existe(UUID id) {
        return contas.containsKey(id);
    }
}

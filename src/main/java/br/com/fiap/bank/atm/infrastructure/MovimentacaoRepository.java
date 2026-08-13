package br.com.fiap.bank.atm.infrastructure;

import br.com.fiap.bank.atm.model.Movimentacao;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public class MovimentacaoRepository {

    // Conjunto seguro do FIAP Bank que impede duplicatas baseado na BaseEntity
    private Set<Movimentacao> movimentacoes = new HashSet<>();

    public void adicionar(Movimentacao entidade) {
        movimentacoes.add(entidade);
    }

    // O jeito antigo (Java 7-): Risco altíssimo de NullPointerException
    public Movimentacao buscarPorId(UUID id) {
        for (Movimentacao m : movimentacoes) {
            if (m.getId().equals(id)) {
                return m;
            }
        }
        return null; // A bomba relógio!
    }

    public void remover(UUID id) {
        Movimentacao m = buscarPorId(id);
        if (m != null) {
            movimentacoes.remove(m);
        }
    }

    public List<Movimentacao> buscarTodas() {
        // Convertendo o Set de volta para List para compatibilidade de visualização
        return new ArrayList<>(movimentacoes);
    }
}
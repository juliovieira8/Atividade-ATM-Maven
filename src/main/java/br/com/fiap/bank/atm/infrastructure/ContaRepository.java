package br.com.fiap.bank.atm.infrastructure;

import br.com.fiap.bank.atm.domain.Conta;
import java.util.HashMap;
import java.util.Map;

public class ContaRepository {

    // K = String (Ex: "0001-12345"), V = Conta
    private Map<String, Conta> cacheContas = new HashMap<>();

    // Helper para padronizar a chave composta
    private String gerarChave(String agencia, String numero) {
        return agencia + "-" + numero;
    }

    public void adicionar(Conta entidade) {
        String chave = gerarChave(entidade.getAgencia(), entidade.getNumero());
        cacheContas.put(chave, entidade);
    }

    // Busca O(1) de altíssima performance (mas ainda retornando null se não achar)
    public Conta validarContaNoAtm(String agencia, String numero) {
        return cacheContas.get(gerarChave(agencia, numero));
    }
}
package br.com.fiap.bank.atm.domain.repository;

import br.com.fiap.bank.atm.domain.BaseEntity;
import java.util.List;
import java.util.UUID;

// Interface genérica que serve para Movimentacao, Conta, Cliente...
public interface ATMRepository<T extends BaseEntity> {
    void adicionar(T entidade);
    T buscarPorId(UUID id);
    void remover(UUID id);
    List<T> buscarTodas();
}
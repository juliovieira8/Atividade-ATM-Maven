package br.fiap.bank.atm.model;

import java.time.LocalDate;
import java.util.UUID;

// Classe base que todas as entidades do sistema herdam.
// Coloquei aqui o id e a data de criação porque todo objeto do banco
// precisa dessas informações, então faz sentido centralizar em vez de repetir em cada classe.
public abstract class BaseEntity {

    private UUID id;
    private LocalDate dataCriacao;

    public BaseEntity() {
        // UUID gera um identificador único automático, assim não preciso
        // me preocupar em controlar IDs manualmente.
        this.id = UUID.randomUUID();
        this.dataCriacao = LocalDate.now();
    }

    public UUID getId() {
        return id;
    }

    public LocalDate getDataCriacao() {
        return dataCriacao;
    }

    // Sobrescrevi o equals para comparar pelo id, porque dois objetos
    // com o mesmo id representam a mesma entidade no sistema.
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        BaseEntity that = (BaseEntity) obj;
        return this.id.equals(that.id);
    }

    // Sempre que sobrescreve o equals, precisa sobrescrever o hashCode também.
    // Aprendi isso estudando na internet — se não fizer isso, a classe quebra dentro de HashMap e HashSet.
    @Override
    public int hashCode() {
        return this.id.hashCode();
    }
}

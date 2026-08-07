package br.fiap.bank.atm.model;

// Enum com os possíveis estados de uma conta.
// Usei enum em vez de String para evitar erros de digitação e deixar mais seguro.
// Assim o compilador avisa se eu tentar usar um status que não existe.
public enum StatusConta {
    ATIVA, BLOQUEADA, ENCERRADA
}

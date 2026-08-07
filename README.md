# FIAP Bank ATM — Checkpoint 2

> Disciplina: Domain Driven Design - Java | Professor: Eduardo dos Santos Ramos | Turma: 2ESPG

---

## O que é esse projeto?

Esse projeto é o Checkpoint 2 da disciplina de DDD com Java. A proposta era pegar o sistema de Caixa Eletrônico (ATM) que fizemos no CP1 e refatorar do zero usando Orientação a Objetos e os princípios de Domain-Driven Design.

O sistema roda no console e simula um terminal bancário com autenticação por senha, operações de saque/depósito e histórico de movimentações.

---

## Funcionalidades implementadas

- Login com senha (bloqueia a conta após 3 tentativas erradas)
- Consulta de saldo
- Depósito
- Saque (com taxa de R$ 25,00 para Conta Corrente)
- Histórico de movimentações com data, hora, tipo e valor
- Dois tipos de conta: **Corrente** e **Poupança**

---

## Arquitetura do projeto

Segui a separação em 4 camadas dentro do pacote `br.com.fiap.bank.atm`, conforme pedido no enunciado:

```
presentation  →  application  →  model
                                    ↑
                           infrastructure
```

### Camada `model` (Domínio)

Aqui ficam todas as regras de negócio. Não usei nenhum `Scanner` ou `System.out` aqui, só a lógica pura.

| Classe | Tipo | O que faz |
|---|---|---|
| `BaseEntity` | Abstract | Gera UUID e data de criação para as entidades |
| `Cliente` | Entity | Representa o correntista |
| `ContaAcesso` | Value Object | Controla a senha, tentativas e bloqueio |
| `Dinheiro` | Value Object | Encapsula o BigDecimal com as operações monetárias |
| `Movimentacao` | Entity | Registra uma transação (data, tipo, valor) |
| `Conta` | Abstract | Base das contas, define o Template Method do saque |
| `ContaCorrente` | Entity | Cobra R$ 25,00 de taxa por saque |
| `ContaPoupanca` | Entity | Sem taxa, tem rendimento mensal de 1,1% |
| `StatusConta` | Enum | ATIVA, BLOQUEADA, ENCERRADA |
| `TipoMovimentacao` | Enum | DEPOSITO, SAQUE, TAXA, RENDIMENTO |

### Camada `application` (Serviços)

Faz a ponte entre a tela e o domínio. Sem lógica de negócio e sem console aqui.

| Classe | Padrão | O que faz |
|---|---|---|
| `ContaFactory` | Singleton + Factory | Cria contas (Corrente ou Poupança) |
| `ContaService` | Service | Expõe depósito, saque, saldo e histórico |
| `AutorizacaoService` | Service | Valida a senha delegando pro `ContaAcesso` |

### Camada `presentation` (Interface)

Só a interação com o usuário, sem nenhuma regra de negócio.

| Classe | O que faz |
|---|---|
| `TerminalBancarioController` | Menu, leitura de entradas, formatação das telas |

### Camada `infrastructure` (Repositório)

Simula um banco de dados em memória.

| Classe | O que faz |
|---|---|
| `ContaRepository` | Armazena contas num HashMap |

---

## Estrutura de pastas

```
cp2java/
└── src/
    └── br/
        └── fiap/
            └── bank/
                └── atm/
                    ├── Main.java
                    ├── model/
                    │   ├── BaseEntity.java
                    │   ├── Cliente.java
                    │   ├── ContaAcesso.java
                    │   ├── Conta.java
                    │   ├── ContaCorrente.java
                    │   ├── ContaPoupanca.java
                    │   ├── Dinheiro.java
                    │   ├── Movimentacao.java
                    │   ├── StatusConta.java
                    │   └── TipoMovimentacao.java
                    ├── application/
                    │   ├── ContaFactory.java
                    │   ├── ContaService.java
                    │   └── AutorizacaoService.java
                    ├── presentation/
                    │   └── TerminalBancarioController.java
                    └── infrastructure/
                        └── ContaRepository.java
```

---

## Padrões de projeto usados

### Template Method — Saque

Usei esse padrão na classe abstrata `Conta` pra definir o fluxo do saque:
1. Valida o saldo
2. Debita o valor (registra `SAQUE`)
3. Aplica a regra de taxa — esse passo é abstrato, cada subclasse implementa do seu jeito

```java
// Conta.java
public void realizarSaque(Dinheiro valor) {
    sacar(valor);           // passo fixo
    aplicarRegraDeTaxa();   // passo variável (polimorfismo)
}

protected abstract void aplicarRegraDeTaxa();
```

- `ContaCorrente` → desconta R$ 25,00 e registra `TAXA`
- `ContaPoupanca` → não faz nada (sem taxa)

### Singleton — ContaFactory

Garante que só exista uma instância da fábrica:

```java
public static ContaFactory getInstance() {
    if (instance == null) {
        instance = new ContaFactory();
    }
    return instance;
}
```

### Factory — ContaFactory

Centraliza a criação de contas. Em vez de instanciar `ContaCorrente` ou `ContaPoupanca` direto, uso a factory:

```java
Conta conta = ContaFactory.getInstance().criarContaCorrente(cliente, contaAcesso, saldo);
```

---

## Regras de negócio

| Regra | Detalhe |
|---|---|
| Depósito | Valor tem que ser maior que zero |
| Saque | Valor maior que zero e não pode ultrapassar o saldo |
| Taxa (Conta Corrente) | R$ 25,00 descontados a cada saque |
| Bloqueio | 3 senhas erradas consecutivas bloqueiam a conta |
| Histórico | Toda operação gera um registro com data/hora, tipo e valor |

---

## Como rodar

**Pré-requisito:** Java 11+

**Compilar:**
```bash
mkdir -p out
javac -d out -sourcepath src $(find src -name "*.java")
```

**Executar:**
```bash
java -cp out br.com.fiap.bank.atm.Main
```

**Dados de acesso (simulados no código):**

| Campo | Valor |
|---|---|
| Cliente | João da Silva |
| Senha | `1234` |
| Tipo de Conta | Corrente |
| Saldo Inicial | R$ 1.000,00 |

---

## Exemplo de uso

```
============================================
      FIAP BANK - TERMINAL ATM (BETA)
============================================

Digite sua senha: 1234
Acesso autorizado! Bem-vindo, João!

============================================
              MENU PRINCIPAL
============================================
[1] Consultar Saldo
[2] Fazer Depósito
[3] Fazer Saque
[4] Histórico de Movimentações
[5] Sair
============================================
Escolha uma opção: 3

--- Fazer Saque ---
Informe o valor do saque: R$ 100
Saque realizado com sucesso!
Novo saldo: R$ 875,00

(saldo = 1000 - 100 de saque - 25 de taxa = 875)

============================================
Escolha uma opção: 4

--- Histórico de Movimentações ---
Data/Hora              | Tipo         | Valor
-------------------------------------------------------
11/05/2026 14:32:01    | SAQUE        | R$ 100,00
11/05/2026 14:32:01    | TAXA         | R$ 25,00
```

---

## Tecnologias

- Java puro (sem frameworks)
- `BigDecimal` pra precisão com valores monetários
- `LocalDateTime` pra data/hora das movimentações
- `UUID` como identificador único das entidades
- `ArrayList` e `HashMap` pra coleções em memória

---

*Checkpoint 2 — Domain Driven Design com Java — FIAP 2026*

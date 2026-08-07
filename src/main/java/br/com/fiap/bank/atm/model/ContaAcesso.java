package br.fiap.bank.atm.model;

// Essa classe cuida da parte de segurança da conta: senha e bloqueio.
// Separei do modelo de Conta para não misturar regra financeira com autenticação.
public class ContaAcesso {

    // Deixei como constante para ficar fácil de mudar no futuro se precisar.
    public static final Integer MAXIMO_TENTATIVAS = 3;

    private String senha;
    private Integer tentativas;
    private Boolean bloqueado;

    public ContaAcesso(String senha) {
        this.senha = senha;
        this.tentativas = 0;
        this.bloqueado = Boolean.FALSE;
    }

    // Esse método valida a senha e já controla as tentativas automaticamente.
    // Se errar 3 vezes, bloqueia — parecido com o que acontece no banco real.
    public Boolean validarSenha(String senhaInformada) {
        // Se já está bloqueado, nem deixa tentar de novo.
        if (bloqueado) {
            return Boolean.FALSE;
        }
        if (this.senha.equals(senhaInformada)) {
            // Acertou a senha, reseta o contador de tentativas.
            resetarTentativas();
            return Boolean.TRUE;
        }
        tentativas++;
        if (tentativas >= MAXIMO_TENTATIVAS) {
            bloqueado = Boolean.TRUE;
        }
        return Boolean.FALSE;
    }

    public Boolean isBloqueado() {
        return bloqueado;
    }

    // Esse método pode ser usado por um administrador para desbloquear a conta.
    public void resetarTentativas() {
        this.tentativas = 0;
        this.bloqueado = Boolean.FALSE;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        ContaAcesso that = (ContaAcesso) obj;
        return this.senha.equals(that.senha);
    }
}

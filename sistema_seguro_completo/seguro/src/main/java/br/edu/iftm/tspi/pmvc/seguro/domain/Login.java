package br.edu.iftm.tspi.pmvc.seguro.domain;

public class Login {

    private String usuario;
    private String senha;

    public Login() {
    }

    public Login(String usuario) {
        this.usuario = usuario;
    }

    public Login(String usuario, String senha) {
        this.usuario = usuario;
        this.senha = senha;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Login other = (Login) obj;
        return usuario != null && usuario.equals(other.usuario);
    }

    @Override
    public int hashCode() {
        return usuario != null ? usuario.hashCode() : 0;
    }
}

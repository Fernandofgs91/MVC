package br.edu.iftm.tspi.pmvc.seguro.domain;

public class Ocorrencia {

    private String codigo;
    private String local;
    private String descricao;
    private String data;
    private String renavam;

    // Default constructor
    public Ocorrencia() {
    }

    // Constructor with parameters
    public Ocorrencia(String codigo, String local, String descricao, String data, String renavam) {
        this.codigo = codigo;
        this.local = local;
        this.descricao = descricao;
        this.data = data;
        this.renavam = renavam;
    }

    // Getters and setters
    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getLocal() {
        return local;
    }

    public void setLocal(String local) {
        this.local = local;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getRenavam() {
        return renavam;
    }

    public void setRenavam(String renavam) {
        this.renavam = renavam;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Ocorrencia other = (Ocorrencia) obj;
        return codigo != null && codigo.equals(other.codigo);
    }

    @Override
    public int hashCode() {
        return codigo != null ? codigo.hashCode() : 0;
    }
}

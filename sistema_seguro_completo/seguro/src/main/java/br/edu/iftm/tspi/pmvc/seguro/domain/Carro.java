package br.edu.iftm.tspi.pmvc.seguro.domain;

public class Carro {

    private String renavam;
    private String placa;
    private String modelo;
    private String fabricante;
    private String cpf;

    public Carro() {
    }

    public Carro(String renavam, String placa, String modelo, String fabricante, String cpf) {
        this.renavam = renavam;
        this.placa = placa;
        this.modelo = modelo;
        this.fabricante = fabricante;
        this.cpf = cpf;
    }

    public String getRenavam() {
        return renavam;
    }

    public void setRenavam(String renavam) {
        this.renavam = renavam;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getFabricante() {
        return fabricante;
    }

    public void setFabricante(String fabricante) {
        this.fabricante = fabricante;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Carro other = (Carro) obj;
        return renavam != null && renavam.equals(other.renavam);
    }

    @Override
    public int hashCode() {
        return renavam != null ? renavam.hashCode() : 0;
    }
}

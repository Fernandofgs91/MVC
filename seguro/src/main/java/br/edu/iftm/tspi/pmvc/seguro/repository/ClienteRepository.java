package br.edu.iftm.tspi.pmvc.seguro.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import br.edu.iftm.tspi.pmvc.seguro.domain.Cliente;

@Repository
public class ClienteRepository {

    private final List<Cliente> clientes;

    public ClienteRepository() {
        this.clientes = new ArrayList<>();
        this.clientes.add(new Cliente("12345678901", "Carlos Silva", "MG1234567", "31987654321"));
        this.clientes.add(new Cliente("98765432100", "Ana Oliveira", "SP7654321", "11987654321"));
    }

    public List<Cliente> listar() {
        return new ArrayList<>(clientes);
    }

    public List<Cliente> buscaPorNome(String nome) {
        List<Cliente> busca = new ArrayList<>();
        for (Cliente cliente : clientes) {
            if (cliente.getNome().toLowerCase().contains(nome.toLowerCase())) {
                busca.add(cliente);
            }
        }
        return busca;
    }

    public Cliente buscaPorCpf(String cpf) {
        return clientes.stream()
                .filter(cliente -> cliente.getCpf().equals(cpf))
                .findFirst()
                .orElse(null);
    }

    public void novo(Cliente cliente) {
        clientes.add(cliente);
    }

    public boolean delete(String cpf) {
        return clientes.removeIf(cliente -> cliente.getCpf().equals(cpf));
    }

    public boolean update(Cliente cliente) {
        int index = clientes.indexOf(cliente);
        if (index != -1) {
            clientes.set(index, cliente);
            return true;
        }
        return false;
    }
}

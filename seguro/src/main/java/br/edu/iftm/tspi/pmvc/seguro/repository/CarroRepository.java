package br.edu.iftm.tspi.pmvc.seguro.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;

import br.edu.iftm.tspi.pmvc.seguro.domain.Carro;

@Repository
public class CarroRepository {

    private final List<Carro> carros;

    public CarroRepository() {
        this.carros = new ArrayList<>();
        this.carros.add(new Carro("12345678901234", "ABC1234", "Civic", "Honda", "12345678901"));
        this.carros.add(new Carro("98765432109876", "XYZ5678", "Corolla", "Toyota", "98765432100"));
        
    }

    public List<Carro> listar() {
        return new ArrayList<>(carros);
    }

    public List<Carro> buscaPorModelo(String modelo) {
        return carros.stream()
                .filter(carro -> carro.getModelo().toLowerCase().contains(modelo.toLowerCase()))
                .collect(Collectors.toList());
    }

    public List<Carro> buscaPorCpf(String cpf) {
        return carros.stream()
                .filter(carro -> carro.getCpf().equals(cpf))
                .collect(Collectors.toList());
    }

    public Carro buscaPorRenavam(String renavam) {
        return carros.stream()
                .filter(carro -> carro.getRenavam().equals(renavam))
                .findFirst()
                .orElse(null);
    }

    public void novo(Carro carro) {
        carros.add(carro);
    }

    public boolean delete(String renavam) {
        return carros.removeIf(carro -> carro.getRenavam().equals(renavam));
    }

    public boolean update(Carro carro) {
        int index = carros.indexOf(carro);
        if (index != -1) {
            carros.set(index, carro);
            return true;
        }
        return false;
    }
}

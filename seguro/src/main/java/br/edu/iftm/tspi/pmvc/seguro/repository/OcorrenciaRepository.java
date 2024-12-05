package br.edu.iftm.tspi.pmvc.seguro.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import br.edu.iftm.tspi.pmvc.seguro.domain.Ocorrencia;

@Repository
public class OcorrenciaRepository {

    private final List<Ocorrencia> ocorrencias;

    public OcorrenciaRepository() {
        this.ocorrencias = new ArrayList<>();
        // Add some dummy data
        this.ocorrencias.add(new Ocorrencia("001", "Rua A", "Acidente de trânsito", "2024-11-01", "1234567890"));
        this.ocorrencias.add(new Ocorrencia("002", "Rua B", "Roubo", "2024-11-02", "9876543210"));
    }

    public List<Ocorrencia> listar() {
        return new ArrayList<>(ocorrencias);
    }

    public List<Ocorrencia> buscaPorLocal(String local) {
        List<Ocorrencia> busca = new ArrayList<>();
        for (Ocorrencia ocorrencia : ocorrencias) {
            if (ocorrencia.getLocal().toLowerCase().contains(local.toLowerCase())) {
                busca.add(ocorrencia);
            }
        }
        return busca;
    }

    public Ocorrencia buscaPorCodigo(String codigo) {
        return ocorrencias.stream()
                .filter(ocorrencia -> ocorrencia.getCodigo().equals(codigo))
                .findFirst()
                .orElse(null);
    }

    public void novo(Ocorrencia ocorrencia) {
        ocorrencias.add(ocorrencia);
    }

    public boolean delete(String codigo) {
        return ocorrencias.removeIf(ocorrencia -> ocorrencia.getCodigo().equals(codigo));
    }

    public boolean update(Ocorrencia ocorrencia) {
        int index = ocorrencias.indexOf(ocorrencia);
        if (index != -1) {
            ocorrencias.set(index, ocorrencia);
            return true;
        }
        return false;
    }
}

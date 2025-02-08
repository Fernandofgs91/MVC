package br.edu.iftm.tspi.pmvc.seguro.repository;

import java.util.List;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import br.edu.iftm.tspi.pmvc.seguro.domain.Ocorrencia;

@Repository
public class OcorrenciaRepository {

    private final JdbcTemplate conexao;

    public OcorrenciaRepository(JdbcTemplate conexao) {
        this.conexao = conexao;
    }

    public List<Ocorrencia> listar() {
        String sql = """
                      SELECT codigo, local, descricao, data, renavam
                      FROM ocorrencia;
                      """;
        return conexao.query(sql, new BeanPropertyRowMapper<>(Ocorrencia.class));
    }

    public List<Ocorrencia> buscaPorLocal(String local) {
        String sql = """
                      SELECT codigo, local, descricao, data, renavam
                      FROM ocorrencia
                      WHERE LOWER(local) LIKE ?;
                      """;
        return conexao.query(sql, new BeanPropertyRowMapper<>(Ocorrencia.class), "%" + local.toLowerCase() + "%");
    }

    public Ocorrencia buscaPorCodigo(String codigo) {
        String sql = """
                      SELECT codigo, local, descricao, data, renavam
                      FROM ocorrencia
                      WHERE codigo = ?;
                      """;
        try {
            return conexao.queryForObject(sql, new BeanPropertyRowMapper<>(Ocorrencia.class), codigo);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    public void novo(Ocorrencia ocorrencia) {
        String sql = """
                      INSERT INTO ocorrencia (local, descricao, data, renavam)
                      VALUES (?, ?, ?, ?);
                      """;
        conexao.update(sql, ocorrencia.getLocal(), ocorrencia.getDescricao(), ocorrencia.getData(), ocorrencia.getRenavam());
    }

    public boolean atualizar(Ocorrencia ocorrencia) {
        // Verifica se o 'renavam' não é nulo antes de realizar a atualização
        if (ocorrencia.getRenavam() == null) {
            throw new IllegalArgumentException("O campo 'renavam' não pode ser nulo.");
        }
    
        String sql = """
                      UPDATE ocorrencia
                      SET local = ?,
                          descricao = ?,
                          data = ?,
                          renavam = ?
                      WHERE codigo = ?;
                      """;
        return conexao.update(sql, ocorrencia.getLocal(), ocorrencia.getDescricao(), ocorrencia.getData(), ocorrencia.getRenavam(), ocorrencia.getCodigo()) > 0;
    }

    public void excluir(String codigo) {
        String sql = "DELETE FROM ocorrencia WHERE codigo = ?;";
        conexao.update(sql, codigo);
    }
}

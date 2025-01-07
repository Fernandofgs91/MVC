package br.edu.iftm.tspi.pmvc.seguro.repository;

import java.util.List;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import br.edu.iftm.tspi.pmvc.seguro.domain.Carro;

@Repository
public class CarroRepository {

    private final JdbcTemplate conexao;

    public CarroRepository(JdbcTemplate conexao) {
        this.conexao = conexao;
    }

    /**
     * Lista todos os carros no banco de dados.
     *
     * @return Lista de carros.
     */
    public List<Carro> listar() {
        String sql = """
                      SELECT renavam,
                             placa,
                             modelo,
                             fabricante,
                             cpf
                      FROM carro;
                      """;
        return conexao.query(sql, new BeanPropertyRowMapper<>(Carro.class));
    }

    /**
     * Busca carros pelo modelo (case-insensitive).
     *
     * @param modelo Modelo ou parte do modelo do carro.
     * @return Lista de carros encontrados.
     */
    public List<Carro> buscarPorModelo(String modelo) {
        String sql = """
                      SELECT renavam,
                             placa,
                             modelo,
                             fabricante,
                             cpf
                      FROM carro
                      WHERE LOWER(modelo) LIKE ?;
                      """;
        return conexao.query(sql, new BeanPropertyRowMapper<>(Carro.class), "%" + modelo.toLowerCase() + "%");
    }

    /**
     * Busca carros associados a um CPF.
     *
     * @param cpf CPF do proprietário.
     * @return Lista de carros encontrados.
     */
    public List<Carro> buscarPorCpf(String cpf) {
        String sql = """
                      SELECT renavam,
                             placa,
                             modelo,
                             fabricante,
                             cpf
                      FROM carro
                      WHERE cpf = ?;
                      """;
        return conexao.query(sql, new BeanPropertyRowMapper<>(Carro.class), cpf);
    }

    /**
     * Busca um carro pelo RENAVAM.
     *
     * @param renavam RENAVAM do carro.
     * @return Objeto Carro encontrado ou null se não existir.
     */
    public Carro buscarPorRenavam(String renavam) {
        String sql = """
                      SELECT renavam,
                             placa,
                             modelo,
                             fabricante,
                             cpf
                      FROM carro
                      WHERE renavam = ?;
                      """;
        try {
            return conexao.queryForObject(sql, new BeanPropertyRowMapper<>(Carro.class), renavam);
        } catch (EmptyResultDataAccessException e) {
            return null; // Retorna null caso nenhum carro seja encontrado
        }
    }

    /**
     * Insere um novo carro no banco de dados.
     *
     * @param carro Objeto carro contendo os dados a serem salvos.
     */
    public void salvar(Carro carro) {
        String sql = """
                      INSERT INTO carro
                          (renavam, placa, modelo, fabricante, cpf)
                      VALUES (?, ?, ?, ?, ?);
                      """;
        conexao.update(sql, carro.getRenavam(), carro.getPlaca(), carro.getModelo(), carro.getFabricante(), carro.getCpf());
    }

    /**
     * Atualiza os dados de um carro existente.
     *
     * @param carro Objeto carro contendo os dados atualizados.
     */
    public void atualizar(Carro carro) {
        String sql = """
                      UPDATE carro
                      SET placa = ?,
                          modelo = ?,
                          fabricante = ?,
                          cpf = ?
                      WHERE renavam = ?;
                      """;
        conexao.update(sql, carro.getPlaca(), carro.getModelo(), carro.getFabricante(), carro.getCpf(), carro.getRenavam());
    }

    /**
     * Exclui um carro do banco de dados pelo RENAVAM.
     *
     * @param renavam RENAVAM do carro a ser excluído.
     */
    public void excluir(String renavam) {
        String sql = "DELETE FROM carro WHERE renavam = ?";
        conexao.update(sql, renavam);
    }
}

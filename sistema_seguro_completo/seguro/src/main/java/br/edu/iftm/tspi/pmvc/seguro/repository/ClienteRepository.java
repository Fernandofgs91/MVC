package br.edu.iftm.tspi.pmvc.seguro.repository;

import java.util.List;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import br.edu.iftm.tspi.pmvc.seguro.domain.Cliente;

@Repository
public class ClienteRepository {

    private final JdbcTemplate conexao;

    public ClienteRepository(JdbcTemplate conexao) {
        this.conexao = conexao;
    }

    public List<Cliente> listar() {
        String sql = """
                      SELECT cpf, nome, rg, telefone FROM cliente;
                      """;
        return conexao.query(sql, new BeanPropertyRowMapper<>(Cliente.class));
    }

    public List<Cliente> buscarPorNome(String nome) {
        if (nome == null || nome.trim().isEmpty()) {
            throw new IllegalArgumentException("O nome não pode ser nulo ou vazio.");
        }

        String sql = """
                      SELECT cpf, nome, rg, telefone FROM cliente
                      WHERE LOWER(nome) LIKE ?;
                      """;
        return conexao.query(sql, new BeanPropertyRowMapper<>(Cliente.class), "%" + nome.toLowerCase() + "%");
    }

    public void salvar(Cliente cliente) {
        if (cliente == null) {
            throw new IllegalArgumentException("O cliente não pode ser nulo.");
        }

        String sql = """
                      INSERT INTO cliente (cpf, nome, rg, telefone) VALUES (?, ?, ?, ?);
                      """;
        conexao.update(sql, cliente.getCpf(), cliente.getNome(), cliente.getRg(), cliente.getTelefone());
    }

    public void atualizar(Cliente cliente) {
        if (cliente == null || cliente.getCpf() == null) {
            throw new IllegalArgumentException("O cliente ou o CPF não pode ser nulo.");
        }

        if (cliente.getNome() == null || cliente.getNome().trim().isEmpty() ||
            cliente.getRg() == null || cliente.getRg().trim().isEmpty() ||
            cliente.getTelefone() == null || cliente.getTelefone().trim().isEmpty()) {
            throw new IllegalArgumentException("Nome, RG e telefone não podem ser nulos ou vazios.");
        }

        String sql = """
                      UPDATE cliente SET nome = ?, rg = ?, telefone = ? WHERE cpf = ?;
                      """;

        int rowsAffected = conexao.update(sql, cliente.getNome(), cliente.getRg(), cliente.getTelefone(), cliente.getCpf());

        if (rowsAffected == 0) {
            throw new EmptyResultDataAccessException("Nenhum cliente encontrado com o CPF fornecido.", 1);
        }
    }

    public void excluir(String cpf) {
        if (cpf == null || cpf.trim().isEmpty()) {
            throw new IllegalArgumentException("O CPF não pode ser nulo ou vazio.");
        }

        String sql = "DELETE FROM cliente WHERE cpf = ?";
        conexao.update(sql, cpf);
    }

    public Cliente buscarPorCpf(String cpf) {
        if (cpf == null || cpf.trim().isEmpty()) {
            throw new IllegalArgumentException("O CPF não pode ser nulo ou vazio.");
        }

        String sql = """
                      SELECT cpf, nome, rg, telefone FROM cliente WHERE cpf = ?;
                      """;
        try {
            return conexao.queryForObject(sql, new BeanPropertyRowMapper<>(Cliente.class), cpf);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    public boolean verificarDuplicidade(Cliente cliente) {
        if (cliente == null) {
            throw new IllegalArgumentException("O cliente não pode ser nulo.");
        }

        String sql = """
                      SELECT COUNT(*) FROM cliente WHERE cpf = ? OR rg = ? OR telefone = ?;
                      """;
        Integer count = conexao.queryForObject(sql, Integer.class, cliente.getCpf(), cliente.getRg(), cliente.getTelefone());
        return count != null && count > 0;
    }

    public void atualizarPorCriterio(Cliente cliente, String criterio, String valorCriterio) {
        if (cliente == null || criterio == null || valorCriterio == null || valorCriterio.trim().isEmpty()) {
            throw new IllegalArgumentException("O cliente, o critério ou o valor do critério não pode ser nulo ou vazio.");
        }

        if (!List.of("cpf", "nome", "rg", "telefone").contains(criterio.toLowerCase())) {
            throw new IllegalArgumentException("Critério inválido. Use 'cpf', 'nome', 'rg' ou 'telefone'.");
        }

        String sql = """
                      UPDATE cliente SET nome = ?, rg = ?, telefone = ? WHERE """ + criterio + " = ?";

        conexao.update(sql, cliente.getNome(), cliente.getRg(), cliente.getTelefone(), valorCriterio);
    }
}

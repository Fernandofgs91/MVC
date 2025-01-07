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

    /**
     * Lista todos os clientes no banco de dados.
     *
     * @return Lista de clientes.
     */
    public List<Cliente> listar() {
        String sql = """
                      SELECT cpf,
                             nome,
                             rg,
                             telefone
                      FROM cliente;
                      """;
        return conexao.query(sql, new BeanPropertyRowMapper<>(Cliente.class));
    }

    /**
     * Busca clientes pelo nome (case-insensitive).
     *
     * @param nome Nome ou parte do nome do cliente.
     * @return Lista de clientes encontrados.
     */
    public List<Cliente> buscarPorNome(String nome) {
        if (nome == null || nome.trim().isEmpty()) {
            throw new IllegalArgumentException("O nome não pode ser nulo ou vazio.");
        }

        String sql = """
                      SELECT cpf,
                             nome,
                             rg,
                             telefone
                      FROM cliente
                      WHERE LOWER(nome) LIKE ?;
                      """;
        return conexao.query(sql, new BeanPropertyRowMapper<>(Cliente.class), "%" + nome.toLowerCase() + "%");
    }

    /**
     * Salva um novo cliente no banco de dados.
     *
     * @param cliente Objeto cliente contendo os dados a serem salvos.
     */
    public void salvar(Cliente cliente) {
        if (cliente == null) {
            throw new IllegalArgumentException("O cliente não pode ser nulo.");
        }

        String sql = """
                      INSERT INTO cliente
                          (cpf, nome, rg, telefone)
                      VALUES (?, ?, ?, ?);
                      """;
        conexao.update(sql, cliente.getCpf(), cliente.getNome(), cliente.getRg(), cliente.getTelefone());
    }

    /**
     * Atualiza os dados de um cliente existente.
     *
     * @param cliente Objeto cliente contendo os dados atualizados.
     */
    public void atualizar(Cliente cliente) {
        if (cliente == null || cliente.getCpf() == null) {
            throw new IllegalArgumentException("O cliente ou o CPF não pode ser nulo.");
        }

        String sql = """
                      UPDATE cliente
                      SET nome = ?,
                          rg = ?,
                          telefone = ?
                      WHERE cpf = ?;
                      """;
        conexao.update(sql, cliente.getNome(), cliente.getRg(), cliente.getTelefone(), cliente.getCpf());
    }

    /**
     * Exclui um cliente do banco de dados pelo CPF.
     *
     * @param cpf CPF do cliente a ser excluído.
     */
    public void excluir(String cpf) {
        if (cpf == null || cpf.trim().isEmpty()) {
            throw new IllegalArgumentException("O CPF não pode ser nulo ou vazio.");
        }

        String sql = "DELETE FROM cliente WHERE cpf = ?";
        conexao.update(sql, cpf);
    }

    /**
     * Busca um cliente pelo CPF.
     *
     * @param cpf CPF do cliente a ser buscado.
     * @return Objeto cliente encontrado ou null se não existir.
     */
    public Cliente buscarPorCpf(String cpf) {
        if (cpf == null || cpf.trim().isEmpty()) {
            throw new IllegalArgumentException("O CPF não pode ser nulo ou vazio.");
        }

        String sql = """
                      SELECT cpf,
                             nome,
                             rg,
                             telefone
                      FROM cliente
                      WHERE cpf = ?;
                      """;
        try {
            return conexao.queryForObject(sql, new BeanPropertyRowMapper<>(Cliente.class), cpf);
        } catch (EmptyResultDataAccessException e) {
            // Retorna null se nenhum cliente for encontrado
            return null;
        }
    }
}

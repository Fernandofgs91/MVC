package br.edu.iftm.tspi.pmvc.seguro.repository;

import java.util.Optional;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import br.edu.iftm.tspi.pmvc.seguro.domain.Login;

@Repository
public class LoginRepository {

    private final JdbcTemplate db;

    public LoginRepository(JdbcTemplate db) {
        this.db = db;
    }

    public Optional<Login> findByUsuario(String usuario) {
        try {
            String sql = "SELECT * FROM login WHERE usuario = ?";
            return Optional.ofNullable(db.queryForObject(
                sql,
                new BeanPropertyRowMapper<>(Login.class),
                usuario
            ));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    public boolean usuarioExiste(String usuario) {
        String sql = "SELECT COUNT(*) FROM login WHERE usuario = ?";
        Integer count = db.queryForObject(sql, Integer.class, usuario);
        return count != null && count > 0;
    }

    public void save(Login login) {
        String sql = "INSERT INTO login(usuario, senha) VALUES (?, ?)";
        db.update(sql, login.getUsuario(), login.getSenha());
    }
}

package br.edu.iftm.tspi.pmvc.seguro.repository;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import br.edu.iftm.tspi.pmvc.seguro.domain.Login;

@Repository
public class LoginRepository {

    public JdbcTemplate db;

    public LoginRepository(JdbcTemplate db) {
        this.db = db;
    }

    public Login validarLogin(Login loginDigitado) {
        String sql = "select usuario,senha from login where usuario = ?";
        return db.queryForObject(
                            sql,
                            new BeanPropertyRowMapper<>(Login.class),
                            loginDigitado.getUsuario());
    }

    public boolean usuarioExiste(String usuario) {
        String sql = "select count(*) from login where usuario = ?";
        int count = db.queryForObject(sql, Integer.class, usuario);
        return count > 0;
    }

    public void salvar(Login login) {
        String sql = "insert into login(usuario,senha) values(?,?)";
        db.update(sql, login.getUsuario(), login.getSenha());
    }
}
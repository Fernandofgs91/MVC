package br.edu.iftm.tspi.pmvc.seguro.service;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import br.edu.iftm.tspi.pmvc.seguro.domain.Login;
import br.edu.iftm.tspi.pmvc.seguro.repository.LoginRepository;

@Service
public class LoginService {

    private final LoginRepository repository;
    private final BCryptPasswordEncoder encoder;

    public LoginService(LoginRepository repository) {
        this.repository = repository;
        this.encoder = new BCryptPasswordEncoder();
    }

    public boolean verificaLogin(Login loginDigitado) {
        if (loginDigitado == null || 
            loginDigitado.getUsuario() == null || loginDigitado.getUsuario().trim().isEmpty() || 
            loginDigitado.getSenha() == null || loginDigitado.getSenha().trim().isEmpty()) {
            throw new IllegalArgumentException("Usuário e senha não podem ser nulos ou vazios.");
        }

        try {
            Login loginBanco = repository.validarLogin(loginDigitado);
            return encoder.matches(loginDigitado.getSenha(), loginBanco.getSenha());
        } catch (EmptyResultDataAccessException e) {
            return false;
        }
    }

    public void salvar(Login login) {
        if (login == null || 
            login.getUsuario() == null || login.getUsuario().trim().isEmpty() ||
            login.getSenha() == null || login.getSenha().trim().isEmpty()) {
            throw new IllegalArgumentException("Usuário e senha são obrigatórios para cadastro.");
        }

        if (repository.usuarioExiste(login.getUsuario())) {
            throw new IllegalArgumentException("Usuário já cadastrado.");
        }

        String senhaCriptografada = encoder.encode(login.getSenha());
        login.setSenha(senhaCriptografada);
        repository.salvar(login);
    }
}
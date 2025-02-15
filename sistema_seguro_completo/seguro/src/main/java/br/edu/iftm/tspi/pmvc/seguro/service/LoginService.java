package br.edu.iftm.tspi.pmvc.seguro.service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import br.edu.iftm.tspi.pmvc.seguro.domain.Login;
import br.edu.iftm.tspi.pmvc.seguro.repository.LoginRepository;

import java.util.Optional;

@Service
public class LoginService {

    private final LoginRepository repository;
    private final BCryptPasswordEncoder encoder;

    public LoginService(LoginRepository repository) {
        this.repository = repository;
        this.encoder = new BCryptPasswordEncoder();
    }

    public boolean verificarLogin(Login loginDigitado) {
        if (loginDigitado == null || loginDigitado.getUsuario().isBlank() || loginDigitado.getSenha().isBlank()) {
            throw new IllegalArgumentException(" ⚠️ Usuário e senha não podem ser vazios.");
        }

        Optional<Login> loginBancoOpt = repository.findByUsuario(loginDigitado.getUsuario());

        return loginBancoOpt.isPresent() &&
               encoder.matches(loginDigitado.getSenha(), loginBancoOpt.get().getSenha());
    }

    public Optional<Login> buscarUsuario(String usuario) {
        return repository.findByUsuario(usuario);
    }
    

    public void salvar(Login login) {
        if (login == null || login.getUsuario().isBlank() || login.getSenha().isBlank()) {
            throw new IllegalArgumentException(" ⚠️ Usuário e senha são obrigatórios para cadastro.");
        }

        if (repository.usuarioExiste(login.getUsuario())) {
            throw new IllegalArgumentException(" ⚠️ Este Usuário já foi cadastrado.");
        }

        if (login.getSenha().length() < 5) {
            throw new IllegalArgumentException(" ❌ A senha deve conter pelo menos 5 caracteres.");
        }

        login.setSenha(encoder.encode(login.getSenha()));
        repository.save(login);
    }
}

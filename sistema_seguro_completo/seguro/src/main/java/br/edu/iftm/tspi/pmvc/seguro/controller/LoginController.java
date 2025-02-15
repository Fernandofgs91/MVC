package br.edu.iftm.tspi.pmvc.seguro.controller;

import java.util.Optional;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import br.edu.iftm.tspi.pmvc.seguro.domain.Login;
import br.edu.iftm.tspi.pmvc.seguro.service.LoginService;

@Controller
public class LoginController {

    private final LoginService service;

    public LoginController(LoginService service) {
        this.service = service;
    }

    @PostMapping("/login/entrar")
    public String validarLogin(Login login, Model model) {
        if (login.getUsuario().isBlank() || login.getSenha().isBlank()) {
            model.addAttribute("mensagem", " ⚠️ Usuário e senha são obrigatórios!");
            return "login/login";
        }

        Optional<Login> loginBancoOpt = service.buscarUsuario(login.getUsuario());

        if (loginBancoOpt.isEmpty()) {
            model.addAttribute("mensagem", " ❌ Usuário não cadastrado.");
            return "login/login";
        }

        if (service.verificarLogin(login)) {
            model.addAttribute("mensagem", " ✅ Login realizado com sucesso! Bem-vindo, " + login.getUsuario() + "!");
            return "redirect:/home";
        } else {
            model.addAttribute("mensagem", " 🔒 Senha incorreta! Tente novamente.");
            return "login/login";
        }
    }

    @GetMapping("/")
    public String telaInicial() {
        return "login/login";
    }

    @GetMapping("/login/telaNovoUsuario")
    public String novo() {
        return "login/cadastro";
    }

    @PostMapping("/login/novoUsuario")
    public String novoUsuario(Login login, @RequestParam("confirmarSenha") String confirmarSenha, Model model) {
        if (login.getUsuario().isBlank() || login.getSenha().isBlank() || confirmarSenha.isBlank()) {
            model.addAttribute("mensagem", " ⚠️ Usuário e senha são obrigatórios para cadastro!");
            return "login/cadastro";
        }

        if (!login.getSenha().equals(confirmarSenha)) {
            model.addAttribute("mensagem", "❌ A confirmação de senha não confere.");
            return "login/cadastro";
        }

        try {
            service.salvar(login);
            model.addAttribute("mensagem", " ✅ Usuário cadastrado com sucesso!");
            return "login/login";
        } catch (IllegalArgumentException e) {
            model.addAttribute("mensagem", e.getMessage());
            return "login/cadastro";
        }
    }
}

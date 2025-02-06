package br.edu.iftm.tspi.pmvc.seguro.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import br.edu.iftm.tspi.pmvc.seguro.domain.Login;
import br.edu.iftm.tspi.pmvc.seguro.service.LoginService;

@Controller
public class LoginController {

    private final LoginService service;

    // Injeção de dependência via construtor
    public LoginController(LoginService service) {
        this.service = service;
    }

    @PostMapping("/login/entrar")
    public String validarLogin(Login login, Model model) {
        if (service.verificaLogin(login)) {
            model.addAttribute("mensagem", "Usuário logado com sucesso");
            return "redirect:/home"; // Redireciona para a home
        } else {
            model.addAttribute("mensagem", "Usuário ou senha inválidos");
            return "login/login";
        }
    }

    @GetMapping("/")
    public String telaInicial(Model model) {
        return "login/login";
    }

    @GetMapping("/login/telaNovoUsuario")
    public String novo(Model model) {
        return "login/cadastro";
    }

    @PostMapping("/login/novoUsuario")
    public String novoUsuario(Login loginDigitado, Model model) {
        service.salvar(loginDigitado);
        model.addAttribute("mensagem", "Usuário " + loginDigitado.getUsuario() + " cadastrado com sucesso");
        return "login/login";
    }
}

package br.edu.iftm.tspi.pmvc.sistema_seguro.controller;

import br.edu.iftm.tspi.pmvc.domain.Cliente;
import br.edu.iftm.tspi.pmvc.sistema_seguro.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ClienteController {

    private final ClienteRepository clienteRepository;

    @Autowired
    public ClienteController(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    // Exibe o formulário de cadastro de cliente
    @GetMapping("/cadastroCliente")
    public String exibirFormulario(Model model) {
        model.addAttribute("cliente", new Cliente());  // Cria um novo objeto Cliente
        return "formCliente";  // A página formCliente.html
    }

    // Processa o envio do formulário para salvar o cliente
    @PostMapping("/salvarCliente")
    public String salvarCliente(Cliente cliente, Model model) {
        // Salva o cliente no repositório
        clienteRepository.adicionarCliente(cliente);

        // Adiciona o cliente ao modelo para exibir uma mensagem de sucesso
        model.addAttribute("mensagem", "Cliente cadastrado com sucesso!");
        return "clienteCadastrado";  // Redireciona para a página de confirmação
    }
}

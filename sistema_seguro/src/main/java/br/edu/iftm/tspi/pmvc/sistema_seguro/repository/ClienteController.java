package br.edu.iftm.tspi.pmvc.sistema_seguro.controller;

import br.edu.iftm.tspi.pmvc.domain.Cliente;
import br.edu.iftm.tspi.pmvc.sistema_seguro.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ClienteController {

    private final ClienteRepository clienteRepository;

    @Autowired
    public ClienteController(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    @GetMapping("/inserirClientes")
    public String inserirClientes() {
        // Inserir novos clientes
        Cliente cliente1 = new Cliente("123.765.789-54", "Fernando", "998436674");
        Cliente cliente2 = new Cliente("987.654.321-00", "Maria", "987654321");
        Cliente cliente3 = new Cliente("111.222.333-44", "João", "999888777");

        // Adiciona clientes ao repositório
        clienteRepository.adicionarCliente(cliente1);
        clienteRepository.adicionarCliente(cliente2);
        clienteRepository.adicionarCliente(cliente3);

        return "clientesInseridos";  // A view que você vai retornar para confirmar a inserção
    }

    @GetMapping("/listarClientes")
    public String listarClientes(Model model) {
        model.addAttribute("clientes", clienteRepository.listarClientes());
        return "listaClientes";  // A view que vai mostrar a lista de clientes
    }
}

package br.edu.iftm.tspi.pmvc.seguro.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.edu.iftm.tspi.pmvc.seguro.domain.Cliente;
import br.edu.iftm.tspi.pmvc.seguro.repository.ClienteRepository;

@Controller
@RequestMapping("/cliente")
public class ClienteController {

    private final ClienteRepository repository;

    private static final String URL_LISTA = "cliente/lista";
    private static final String URL_FORM = "cliente/form";
    private static final String URL_REDIRECT_LISTA = "redirect:/cliente";

    private static final String ATRIBUTO_MENSAGEM = "mensagem";
    private static final String ATRIBUTO_OBJETO = "cliente";
    private static final String ATRIBUTO_LISTA = "clientes";

    public ClienteController(ClienteRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public String listar(Model model) {
        model.addAttribute(ATRIBUTO_LISTA, repository.listar());
        return URL_LISTA;
    }

    @GetMapping("/buscar")
    public String buscarPorNome(@RequestParam("nome") String nome, Model model) {
        List<Cliente> clientes = repository.buscaPorNome(nome);
        model.addAttribute(ATRIBUTO_LISTA, clientes);
        model.addAttribute(ATRIBUTO_MENSAGEM, clientes.isEmpty() ? nome + " não encontrado." : null);
        return URL_LISTA;
    }

    @GetMapping({"/novo", "/editar/{cpf}"})
    public String abrirForm(@PathVariable(required = false) String cpf, Model model, RedirectAttributes redirectAttributes) {
        Cliente cliente = (cpf == null) ? new Cliente() : repository.buscaPorCpf(cpf);
        if (cpf != null && cliente == null) {
            redirectAttributes.addFlashAttribute(ATRIBUTO_MENSAGEM, cpf + " não encontrado.");
            return URL_REDIRECT_LISTA;
        }
        model.addAttribute(ATRIBUTO_OBJETO, cliente);
        return URL_FORM;
    }

    @PostMapping({"/novo", "/editar/{cpf}"})
    public String salvarOuAtualizar(@PathVariable(required = false) String cpf, @ModelAttribute Cliente cliente, RedirectAttributes redirectAttributes) {
        if (cpf == null) {
            repository.novo(cliente);
            redirectAttributes.addFlashAttribute(ATRIBUTO_MENSAGEM, cliente.getNome() + " salvo com sucesso.");
        } else if (repository.update(cliente)) {
            redirectAttributes.addFlashAttribute(ATRIBUTO_MENSAGEM, cliente.getNome() + " atualizado com sucesso.");
        } else {
            redirectAttributes.addFlashAttribute(ATRIBUTO_MENSAGEM, "Não foi possível atualizar " + cliente.getNome());
        }
        return URL_REDIRECT_LISTA;
    }

    @PostMapping("/excluir/{cpf}")
    public String excluir(@PathVariable String cpf, RedirectAttributes redirectAttributes) {
        repository.delete(cpf);
        redirectAttributes.addFlashAttribute(ATRIBUTO_MENSAGEM, "Cliente excluído com sucesso.");
        return URL_REDIRECT_LISTA;
    }
}
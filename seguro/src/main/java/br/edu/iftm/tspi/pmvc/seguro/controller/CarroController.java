package br.edu.iftm.tspi.pmvc.seguro.controller;

import java.util.ArrayList;
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

import br.edu.iftm.tspi.pmvc.seguro.domain.Carro;
import br.edu.iftm.tspi.pmvc.seguro.domain.Cliente;
import br.edu.iftm.tspi.pmvc.seguro.repository.CarroRepository;
import br.edu.iftm.tspi.pmvc.seguro.repository.ClienteRepository;

@Controller
@RequestMapping("/carro")
public class CarroController {

    private final CarroRepository carroRepository;
    private final ClienteRepository clienteRepository;

    private static final String URL_LISTA = "carro/lista";
    private static final String URL_FORM = "carro/form";
    private static final String URL_REDIRECT_LISTA = "redirect:/carro";

    private static final String ATRIBUTO_MENSAGEM = "mensagem";
    private static final String ATRIBUTO_OBJETO = "carro";
    private static final String ATRIBUTO_LISTA = "carros";

    public CarroController(CarroRepository carroRepository, ClienteRepository clienteRepository) {
        this.carroRepository = carroRepository;
        this.clienteRepository = clienteRepository;
    }

    @GetMapping
    public String listar(Model model) {
        model.addAttribute(ATRIBUTO_LISTA, carroRepository.listar());
        return URL_LISTA;
    }

    @GetMapping("/buscar")
    public String buscarPorModelo(@RequestParam("modelo") String modelo, Model model) {
        List<Carro> carros = carroRepository.buscaPorModelo(modelo);
        model.addAttribute(ATRIBUTO_LISTA, carros);
        model.addAttribute(ATRIBUTO_MENSAGEM, carros.isEmpty() ? modelo + " não encontrado." : null);
        return URL_LISTA;
    }

    @GetMapping({"/novo", "/editar/{renavam}"})
    public String abrirForm(@PathVariable(required = false) String renavam, Model model, RedirectAttributes redirectAttributes) {
        // Busca ou inicializa o objeto Carro
        Carro carro = (renavam == null) ? new Carro() : carroRepository.buscaPorRenavam(renavam);
        
        // Caso o renavam seja informado, mas o carro não seja encontrado
        if (renavam != null && carro == null) {
            redirectAttributes.addFlashAttribute(ATRIBUTO_MENSAGEM, renavam + " não encontrado.");
            return URL_REDIRECT_LISTA;
        }

        // Lista os clientes e seus CPFs
        List<Cliente> clientes = clienteRepository.listar();
        List<String> cpfs = new ArrayList<>();
        for (Cliente cliente : clientes) {
            cpfs.add(cliente.getCpf()); // Acessa o método correto
        }

        // Adiciona os atributos ao modelo
        model.addAttribute("cpfs", cpfs);
        model.addAttribute(ATRIBUTO_OBJETO, carro);
        model.addAttribute("clientes", clientes); // Passa a lista de clientes para o formulário

        return URL_FORM;
    }

    @PostMapping({"/novo", "/editar/{renavam}"})
    public String salvarOuAtualizar(@PathVariable(required = false) String renavam, @ModelAttribute Carro carro, RedirectAttributes redirectAttributes) {
        if (renavam == null) {
            carroRepository.novo(carro);
            redirectAttributes.addFlashAttribute(ATRIBUTO_MENSAGEM, carro.getModelo() + " salvo com sucesso.");
        } else if (carroRepository.update(carro)) {
            redirectAttributes.addFlashAttribute(ATRIBUTO_MENSAGEM, carro.getModelo() + " atualizado com sucesso.");
        } else {
            redirectAttributes.addFlashAttribute(ATRIBUTO_MENSAGEM, "Não foi possível atualizar " + carro.getModelo());
        }
        return URL_REDIRECT_LISTA;
    }

    @PostMapping("/excluir/{renavam}")
    public String excluir(@PathVariable String renavam, RedirectAttributes redirectAttributes) {
        carroRepository.delete(renavam);
        redirectAttributes.addFlashAttribute(ATRIBUTO_MENSAGEM, "Carro excluído com sucesso.");
        return URL_REDIRECT_LISTA;
    }
}

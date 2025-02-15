package br.edu.iftm.tspi.pmvc.seguro.controller;

import java.util.List;
import java.util.stream.Collectors;

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
        List<Carro> carros = carroRepository.buscarPorModelo(modelo);
        model.addAttribute(ATRIBUTO_LISTA, carros);
        model.addAttribute(ATRIBUTO_MENSAGEM, carros.isEmpty() ? "Nenhum carro encontrado para o modelo: " + modelo : null);
        return URL_LISTA;
    }

    @GetMapping({"/novo", "/editar/{renavam}"})
    public String abrirForm(@PathVariable(required = false) String renavam, Model model, RedirectAttributes redirectAttributes) {
        Carro carro = getCarroByRenavam(renavam, redirectAttributes);
        if (carro == null) {
            return URL_REDIRECT_LISTA;
        }

        List<String> cpfs = clienteRepository.listar()
                                             .stream()
                                             .map(Cliente::getCpf)
                                             .collect(Collectors.toList());

        model.addAttribute("cpfs", cpfs);
        model.addAttribute(ATRIBUTO_OBJETO, carro);
        return URL_FORM;
    }

    @PostMapping({"/novo", "/editar/{renavam}"})
    public String salvarOuAtualizar(@PathVariable(required = false) String renavam,
                                     @ModelAttribute Carro carro,
                                     RedirectAttributes redirectAttributes) {
        try {
            validarCarro(carro);
            if (renavam == null) {
                verificarDuplicidade(carro.getRenavam());
                carroRepository.salvar(carro);
                redirectAttributes.addFlashAttribute(ATRIBUTO_MENSAGEM, "Carro " + carro.getModelo() + " salvo com sucesso!");
            } else {
                carroRepository.atualizar(carro);
                redirectAttributes.addFlashAttribute(ATRIBUTO_MENSAGEM, "Carro " + carro.getModelo() + " atualizado com sucesso!");
            }
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute(ATRIBUTO_MENSAGEM, e.getMessage());
            return renavam == null ? "redirect:/carro/novo" : "redirect:/carro/editar/" + renavam;
        }

        return URL_REDIRECT_LISTA;
    }

    @PostMapping("/excluir/{renavam}")
    public String excluir(@PathVariable String renavam, RedirectAttributes redirectAttributes) {
        carroRepository.excluir(renavam);
        redirectAttributes.addFlashAttribute(ATRIBUTO_MENSAGEM, "Carro excluído com sucesso.");
        return URL_REDIRECT_LISTA;
    }

    // Métodos Auxiliares
    private Carro getCarroByRenavam(String renavam, RedirectAttributes redirectAttributes) {
        if (renavam == null) return new Carro();

        Carro carro = carroRepository.buscarPorRenavam(renavam);
        if (carro == null) {
            redirectAttributes.addFlashAttribute(ATRIBUTO_MENSAGEM, "Carro com RENAVAM " + renavam + " não encontrado.");
        }
        return carro;
    }

    private void validarCarro(Carro carro) {
        if (carro.getCpf() == null || carro.getCpf().isBlank()) {
            throw new IllegalArgumentException("O campo CPF é obrigatório.");
        }
        if (carro.getRenavam() == null || carro.getRenavam().isBlank()) {
            throw new IllegalArgumentException("O campo RENAVAM é obrigatório.");
        }
    }

    private void verificarDuplicidade(String renavam) {
        if (carroRepository.buscarPorRenavam(renavam) != null) {
            throw new IllegalArgumentException("Já existe um carro cadastrado com o RENAVAM: " + renavam);
        }
    }
}

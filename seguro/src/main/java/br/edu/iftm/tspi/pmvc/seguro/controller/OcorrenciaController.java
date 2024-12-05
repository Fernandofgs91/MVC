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
import br.edu.iftm.tspi.pmvc.seguro.domain.Ocorrencia;
import br.edu.iftm.tspi.pmvc.seguro.repository.CarroRepository;
import br.edu.iftm.tspi.pmvc.seguro.repository.OcorrenciaRepository;

@Controller
@RequestMapping("/ocorrencia")
public class OcorrenciaController {

    private final OcorrenciaRepository ocorrenciaRepository;
    private final CarroRepository carroRepository;

    private static final String URL_LISTA = "ocorrencia/lista";
    private static final String URL_FORM = "ocorrencia/form";
    private static final String URL_REDIRECT_LISTA = "redirect:/ocorrencia";

    private static final String ATRIBUTO_MENSAGEM = "mensagem";
    private static final String ATRIBUTO_OBJETO = "ocorrencia";
    private static final String ATRIBUTO_LISTA = "ocorrencias";

    public OcorrenciaController(OcorrenciaRepository ocorrenciaRepository, CarroRepository carroRepository) {
        this.ocorrenciaRepository = ocorrenciaRepository;
        this.carroRepository = carroRepository;
    }

    @GetMapping
    public String listar(Model model) {
        model.addAttribute(ATRIBUTO_LISTA, ocorrenciaRepository.listar());
        return URL_LISTA;
    }

    @GetMapping("/buscarPorLocal")
    public String buscarPorLocal(@RequestParam("local") String local, Model model) {
        List<Ocorrencia> ocorrencias = ocorrenciaRepository.buscaPorLocal(local);
        model.addAttribute(ATRIBUTO_LISTA, ocorrencias);
        model.addAttribute(ATRIBUTO_MENSAGEM, ocorrencias.isEmpty() ? local + " não encontrado." : null);
        return URL_LISTA;
    }

    @GetMapping({ "/novo", "/editar/{codigo}" })
    public String abrirForm(@PathVariable(required = false) String codigo, Model model,
            RedirectAttributes redirectAttributes) {
        Ocorrencia ocorrencia = (codigo == null) ? new Ocorrencia() : ocorrenciaRepository.buscaPorCodigo(codigo);
        if (codigo != null && ocorrencia == null) {
            redirectAttributes.addFlashAttribute(ATRIBUTO_MENSAGEM, codigo + " não encontrado.");
            return URL_REDIRECT_LISTA;
        }
        List<Carro> carros = carroRepository.listar();
        List<String> renavans = new ArrayList<>();
        for (Carro carro : carros) {
            renavans.add(carro.getRenavam());
        }
        model.addAttribute("renavans", renavans);
        model.addAttribute(ATRIBUTO_OBJETO, ocorrencia);
        model.addAttribute("carros", carroRepository.listar()); // Passa a lista de carros para o formulário
        return URL_FORM;
    }

    @PostMapping({ "/novo", "/editar/{codigo}" })
    public String salvarOuAtualizar(@PathVariable(required = false) String codigo,
            @ModelAttribute Ocorrencia ocorrencia, RedirectAttributes redirectAttributes) {
        if (codigo == null) {
            ocorrenciaRepository.novo(ocorrencia);
            redirectAttributes.addFlashAttribute(ATRIBUTO_MENSAGEM, "Ocorrência salva com sucesso.");
        } else if (ocorrenciaRepository.update(ocorrencia)) {
            redirectAttributes.addFlashAttribute(ATRIBUTO_MENSAGEM, "Ocorrência atualizada com sucesso.");
        } else {
            redirectAttributes.addFlashAttribute(ATRIBUTO_MENSAGEM, "Não foi possível atualizar a ocorrência.");
        }
        return URL_REDIRECT_LISTA;
    }

    @PostMapping("/excluir/{codigo}")
    public String excluir(@PathVariable String codigo, RedirectAttributes redirectAttributes) {
        ocorrenciaRepository.delete(codigo);
        redirectAttributes.addFlashAttribute(ATRIBUTO_MENSAGEM, "Ocorrência excluída com sucesso.");
        return URL_REDIRECT_LISTA;
    }
}

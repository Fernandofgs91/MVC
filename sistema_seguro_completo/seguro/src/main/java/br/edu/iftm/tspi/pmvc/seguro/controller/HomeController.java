package br.edu.iftm.tspi.pmvc.seguro.controller;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/home")
    public String index(Model model) {
        model.addAttribute("title", "Bem-vindo ao Sistema!");
        return "fragments/layout"; 
    }
    
}
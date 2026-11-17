package com.ong.adocao;

import jakarta.validation.Valid;
import java.util.List;
import java.util.Locale;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;

@org.springframework.stereotype.Controller
public class Controller {

    private final AdocaoService adocaoService;

    public Controller(AdocaoService adocaoService) {
        this.adocaoService = adocaoService;
    }

    @GetMapping("/")
    public String inicio() {
        return "redirect:/vitrine";
    }

    @GetMapping("/vitrine")
    public String vitrine(@RequestParam(required = false) String especie, Model model) {
        String filtro = normalizarEspecie(especie);
        model.addAttribute("animais", adocaoService.listarAnimais(filtro));
        model.addAttribute("especieSelecionada", filtro);
        return "vitrine";
    }

    @GetMapping("/adotar/{animalId}")
    public String formulario(@PathVariable Long animalId, Model model) {
        model.addAttribute("animal", buscarAnimal(animalId));
        model.addAttribute("candidatura", new CandidaturaForm());
        return "formulario";
    }

    @PostMapping("/adotar/{animalId}/candidaturas")
    public String criarCandidatura(
            @PathVariable Long animalId,
            @Valid @ModelAttribute("candidatura") CandidaturaForm candidatura,
            BindingResult bindingResult,
            Model model) {
        Animal animal = buscarAnimal(animalId);
        if (bindingResult.hasErrors()) {
            model.addAttribute("animal", animal);
            return "formulario";
        }

        Candidatura criada = adocaoService.criarCandidatura(animal, candidatura);
        return "redirect:/candidaturas/" + criada.protocolo() + "/confirmacao";
    }

    @GetMapping("/candidaturas/{protocolo}/confirmacao")
    public String confirmacao(@PathVariable Long protocolo, Model model) {
        Candidatura candidatura = adocaoService.buscarCandidatura(protocolo)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        model.addAttribute("candidatura", candidatura);
        return "confirmacao";
    }

    private Animal buscarAnimal(Long animalId) {
        return adocaoService.buscarAnimal(animalId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    private String normalizarEspecie(String especie) {
        if (especie == null || especie.isBlank()) {
            return null;
        }

        String filtro = especie.toLowerCase(Locale.ROOT);
        if (!List.of("cao", "gato").contains(filtro)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Espécie inválida.");
        }
        return filtro;
    }
}

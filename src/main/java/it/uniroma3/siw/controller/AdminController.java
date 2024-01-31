package it.uniroma3.siw.controller;

import it.uniroma3.siw.model.*;
import it.uniroma3.siw.service.*;
import it.uniroma3.siw.validator.CredenzialiValidator;
import it.uniroma3.siw.validator.PresidenteValidator;
import it.uniroma3.siw.validator.SquadraValidator;
import it.uniroma3.siw.validator.UtenteValidator;
import jakarta.persistence.EntityManager;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static it.uniroma3.siw.model.Credenziali.ADMIN_ROLE;
import static it.uniroma3.siw.model.Credenziali.PRESIDENTE_ROLE;

@Controller
public class AdminController {

    @Autowired PresidenteService presidenteService;
    @Autowired GiocatoreService giocatoreService;
    @Autowired SquadraService squadraService;
    @Autowired UtenteService utenteService;
    @Autowired CredenzialiService credenzialiService;
    @Autowired TesseramentoService tesseramentoService;
    @Autowired EntityManager entityManager;
    @Autowired UtenteValidator utenteValidator;
    @Autowired CredenzialiValidator credenzialiValidator;
    @Autowired PresidenteValidator presidenteValidator;
    @Autowired SquadraValidator squadraValidator;

    @GetMapping("/admin/formNewPresidente")
    public String formNewPresidente(Model model) {
        model.addAttribute("presidente", new Presidente());
        model.addAttribute("utente", new Utente());
        model.addAttribute("credenziali", new Credenziali());
        return "admin/formNewPresidente.html";
    }

    @PostMapping("/admin/presidente")
    public String newPresidente(@Valid @ModelAttribute("utente") Utente utente, @Valid @ModelAttribute("credenziali") Credenziali credenziali, @Valid @ModelAttribute("presidente")Presidente presidente, BindingResult utenteBindingResult, BindingResult credenzialiBindingResult, BindingResult presidenteBindingResult, Model model) {
        String username = credenziali.getUsername();
        utente.setEmail(username);
        this.utenteValidator.validate(utente, utenteBindingResult);
        this.credenzialiValidator.validate(credenziali, credenzialiBindingResult);
        this.presidenteValidator.validate(presidente, presidenteBindingResult);
        if(!utenteBindingResult.hasErrors() || !credenzialiBindingResult.hasErrors() || !presidenteBindingResult.hasErrors()) {
            credenziali.setRuolo(PRESIDENTE_ROLE);
            credenziali.setUtente(utente);
            presidente.setUtente(utente);
            this.utenteService.save(utente);
            this.presidenteService.save(presidente);
            this.credenzialiService.save(credenziali);
           model.addAttribute("utente", utente);
           model.addAttribute("credenziali", credenziali);
           model.addAttribute("presidente", presidente);
            return "admin/presidenteAggiunto.html";
        }
        model.addAttribute("utente", utente);
        model.addAttribute("credenziali", credenziali);
        model.addAttribute("presidente", presidente);
        return "/admin/formNewPresidente.html";
    }

    @GetMapping("/admin/formNewSquadra")
    public String formNewSquadra(Model model) {
        model.addAttribute("squadra", new Squadra());
        return "admin/formNewSquadra.html";
    }

    @GetMapping("/admin/selezionaSquadra")
    public String selezionaSquadra(Model model){
        model.addAttribute("squadre", this.squadraService.findAll());
        return "admin/selezionaSquadra.html";
    }

    @GetMapping("/admin/formModificaSquadra/{id}")
    public String formModificaSquadra(@PathVariable("id")Long id, Model model){
        model.addAttribute("squadra", this.squadraService.findById(id));
        return "admin/formModificaSquadra.html";
    }

    @PostMapping("/admin/modificaSquadra/{id}")
    public String modificaSquadra(@PathVariable("id") Long id, @RequestParam("nome") String nome, @RequestParam("annoFondazione") String anno, @RequestParam("sede") String sede, Model model) {
        Squadra squadra = this.squadraService.findById(id);
        List<String> modifiche = new ArrayList<>();
        if (!nome.isEmpty()) {
            squadra.setNome(nome);
            modifiche.add("nome");
        }
        if (!anno.isEmpty()) {
            squadra.setAnnoFondazione(anno);
            modifiche.add("anno di fondazione");
        }
        if (!sede.isEmpty()) {
            squadra.setSede(sede);
            modifiche.add("sede");
        }
        this.squadraService.save(squadra);
        model.addAttribute("squadra", squadra);
        model.addAttribute("modifiche", modifiche);
        return "admin/squadraModificata.html";
    }

    @PostMapping("/admin/squadra")
    public String newSquadra(@Valid @ModelAttribute("squadra") Squadra squadra, BindingResult bindingResult, Model model) {
        this.squadraValidator.validate(squadra, bindingResult);
        if(bindingResult.hasErrors()) {
            return "admin/formNewSquadra.html";
        }
        this.squadraService.save(squadra);
        model.addAttribute("squadra", squadra);
        return "admin/squadraAggiunta.html";
    }

    @GetMapping("/admin/formAssegnaPresidente")
    public String formAssegnaPresidente(Model model) {
        model.addAttribute("squadre", this.squadraService.findSenzaPresidente());
        model.addAttribute("presidenti", this.presidenteService.findLiberi());
        model.addAttribute("errore", null);
        return "admin/formAssegnaPresidente.html";
    }

    @PostMapping("/admin/assegnaPresidente")
    public String assegnaPresidente(@RequestParam("id") String id, @RequestParam("codiceFiscale") String codiceFiscale, Model model) {
        if (codiceFiscale.trim().isEmpty() || id.trim().isEmpty()) {
            model.addAttribute("errore", "I due campi sono obbligatori!");
            return "admin/formAssegnaPresidente.html";
        }
        Squadra squadra = this.squadraService.findById(Long.valueOf(id));
        Presidente presidente = this.presidenteService.findByCodiceFiscale(codiceFiscale);
        squadra.setPresidente(presidente);
        this.squadraService.save(squadra);
        model.addAttribute("squadra", squadra);
        return "admin/presidenteAssegnato.html";
    }

    @GetMapping("/admin/formCambiaPresidente")
    public String formCambiaPresidente(Model model) {
        model.addAttribute("squadre", this.squadraService.findSenzaPresidente());
        model.addAttribute("presidenti", this.presidenteService.findOccupati());
        model.addAttribute("errore", null);
        return "admin/formCambiaPresidente.html";
    }

    @PostMapping("/admin/cambiaPresidente")
    public String cambiaPresidente(@RequestParam("id") String id, @RequestParam("codiceFiscale") String codiceFiscale, Model model) {
        if (codiceFiscale.trim().isEmpty()) {
            model.addAttribute("errore", "Presidente è un campo obbligatorio!");
            return "admin/formCambiaPresidente.html";
        }
        Presidente presidente = this.presidenteService.findByCodiceFiscale(codiceFiscale);
        Squadra vecchia = this.squadraService.findByPresidente(presidente);
       if (!id.trim().isEmpty()) {
           Squadra nuova = this.squadraService.findById(Long.valueOf(id));
           nuova.setPresidente(presidente);
           this.squadraService.save(nuova);
       }
        vecchia.setPresidente(null);
        this.squadraService.save(vecchia);
       return "admin/presidenteAssegnato.html";
    }
}

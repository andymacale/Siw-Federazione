package it.uniroma3.siw.service;

import it.uniroma3.siw.model.Presidente;
import it.uniroma3.siw.model.Utente;
import it.uniroma3.siw.repository.PresidenteRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PresidenteService {

    @Autowired PresidenteRepository presidenteRepository;
    @Autowired SquadraService squadraService;

    @Transactional
    public Presidente save(Presidente presidente) {
        return this.presidenteRepository.save(presidente);
    }

    public boolean existsByCodiceFiscale(String codiceFiscale) {
        return this.presidenteRepository.existsByCodiceFiscale(codiceFiscale);
    }

    public List<Presidente> findAll() {
        return this.presidenteRepository.findAll();
    }

    public List<Presidente> findLiberi() {
        List<Presidente> tutti = this.findAll();
        List<Presidente> out = new ArrayList<>();

        for(Presidente presidente : tutti) {
            if (!this.squadraService.existsByPresidente(presidente)) {
                out.add(presidente);
            }
        }
        return out;
    }

    public List<Presidente> findOccupati() {
        List<Presidente> tutti = this.findAll();
        List<Presidente> out = new ArrayList<>();

        for(Presidente presidente : tutti) {
            if (this.squadraService.existsByPresidente(presidente)) {
                out.add(presidente);
            }
        }
        return out;
    }

    public Presidente findByCodiceFiscale(String codiceFiscale) {
        return this.presidenteRepository.findByCodiceFiscale(codiceFiscale);
    }

    public Presidente findByUtente(Utente utente) {
        return this.presidenteRepository.findByUtente(utente);
    }

    public Presidente findById(Long id){
        Optional<Presidente> out = this.presidenteRepository.findById(id);
        return out.orElse(null);
    }
}

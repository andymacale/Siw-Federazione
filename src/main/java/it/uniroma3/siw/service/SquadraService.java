package it.uniroma3.siw.service;

import it.uniroma3.siw.model.Giocatore;
import it.uniroma3.siw.model.Presidente;
import it.uniroma3.siw.model.Squadra;
import it.uniroma3.siw.repository.SquadraRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class SquadraService {

    @Autowired SquadraRepository squadraRepository;
    //@Autowired PresidenteService presidenteService;
    @Autowired EntityManager entityManager;

    @Transactional
    public Squadra save(Squadra squadra) {
       return this.squadraRepository.save(squadra);
    }

    public List<Squadra> findAll() {
        return this.squadraRepository.findAll();
    }

    public boolean existsByPresidente(Presidente presidente) {
        return this.squadraRepository.existsByPresidente(presidente);
    }

    public Squadra findByPresidente(Presidente presidente) {
        return this.squadraRepository.findByPresidente(presidente);
    }

    public Squadra findById(Long id) {
        return this.squadraRepository.findById(id).orElse(null);
    }

    public List<Squadra> findSenzaPresidente() {
        List<Squadra> tutte = this.findAll();
        List<Squadra> out = new ArrayList<>();

        for (Squadra squadra : tutte) {
            if (squadra.getPresidente() == null){
                out.add(squadra);
            }
        }
        return out;
    }
}
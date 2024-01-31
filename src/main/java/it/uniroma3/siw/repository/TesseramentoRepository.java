package it.uniroma3.siw.repository;

import it.uniroma3.siw.model.Giocatore;
import it.uniroma3.siw.model.Tesseramento;
import it.uniroma3.siw.model.Squadra;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface TesseramentoRepository extends CrudRepository<Tesseramento, Long> {

    public Tesseramento save(Tesseramento tesseramento);

    public List<Tesseramento> findByGiocatore(Giocatore giocatore);

    public List<Tesseramento> findBySquadra(Squadra squadra);
}

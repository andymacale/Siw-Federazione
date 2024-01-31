package it.uniroma3.siw.repository;

import it.uniroma3.siw.model.Presidente;
import it.uniroma3.siw.model.Utente;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface PresidenteRepository extends CrudRepository<Presidente, Long> {

    public Presidente save(Presidente presidente);

    public boolean existsByCodiceFiscale(String codiceFiscale);

    public Presidente findByCodiceFiscale(String codiceFiscale);

    public Presidente findByUtente(Utente utente);

    public List<Presidente> findAll();

    public Optional<Presidente> findById(Long id);

}

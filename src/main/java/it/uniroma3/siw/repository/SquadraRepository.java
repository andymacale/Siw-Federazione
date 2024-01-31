package it.uniroma3.siw.repository;

import it.uniroma3.siw.model.Presidente;
import it.uniroma3.siw.model.Squadra;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface SquadraRepository extends CrudRepository<Squadra, Long> {

    public Squadra save(Squadra squadra);

    public List<Squadra> findAll();

    public boolean existsByPresidente(Presidente presidente);

    public Squadra findByPresidente(Presidente presidente);

    public Optional<Squadra> findById(Long id);

}

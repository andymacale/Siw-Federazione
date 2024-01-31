package it.uniroma3.siw.service;

import it.uniroma3.siw.model.Giocatore;
import it.uniroma3.siw.model.Squadra;
import it.uniroma3.siw.model.Tesseramento;
import it.uniroma3.siw.repository.TesseramentoRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class TesseramentoService {

    @Autowired TesseramentoRepository tesseramentoRepository;
    @Autowired EntityManager entityManager;

    private List<LocalDate> generaDate(LocalDate inizio, LocalDate fine) {
        List<LocalDate> out = new ArrayList<>();
        LocalDate ind = inizio;
        while (!(ind.equals(fine))) {
            ind = ind.plusDays(1);
        }
        return out;
    }

    @Transactional
    public Tesseramento save(Tesseramento tesseramento) {
        return this.tesseramentoRepository.save(tesseramento);
    }

    public boolean checkTesseramenti(LocalDate inizio, LocalDate fine, Long id) {
        StringBuilder  select = new StringBuilder();
        select.append("select g.id from tesseramento t join giocatore g on g.id = t.giocatore_id where g.id = ");
        select.append(id);
        select.append(" and ('");
        select.append(inizio);
        select.append("' between data_inizio and data_fine or '");
        select.append(fine);
        select.append("' between data_inizio and data_fine)");
        Query query = this.entityManager.createNativeQuery(select.toString());
        List<Object> ris = (List<Object>) query.getResultList();
        return ris.isEmpty();
    }

    public List<Tesseramento> findByGiocatore(Giocatore giocatore) {
        return this.tesseramentoRepository.findByGiocatore(giocatore);
    }

    public List<Tesseramento> findBySquadra(Squadra squadra) {
        return this.tesseramentoRepository.findBySquadra(squadra);
    }

    public Tesseramento findTesseramentoGiusto(Squadra squadra, Giocatore giocatore) {
        StringBuilder select = new StringBuilder();

        select.append("select t.id, to_char(t.data_inizio, 'yyyy-mm-dd'), to_char(t.data_fine, 'yyyy-mm-dd') from tesseramento t ");
        select.append("join squadra s on s.id = t.squadra_id join giocatore g on g.id = t.giocatore_id ");
        select.append("where (g.id = ");
        select.append(giocatore.getId());
        select.append(" and s.id = ");
        select.append(squadra.getId());
        select.append(") and now() between data_inizio and data_fine order by t.data_inizio, t.data_fine limit 1");
        Query query = this.entityManager.createNativeQuery(select.toString());
        Object[] ris = (Object[]) query.getResultList().get(0);
        Tesseramento out = new Tesseramento();
        out.setId((Long) ris[0]);
        out.setDataInizio(LocalDate.parse((String) ris[1]));
        out.setDataFine(LocalDate.parse((String) ris[2]));
        out.setSquadra(squadra);
        out.setGiocatore(giocatore);
        return out;
    }

}

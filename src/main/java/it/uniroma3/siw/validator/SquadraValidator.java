package it.uniroma3.siw.validator;

import it.uniroma3.siw.model.Squadra;
import it.uniroma3.siw.service.SquadraService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class SquadraValidator implements Validator {

    @Autowired SquadraService squadraService;

    @Override
    public boolean supports(Class<?> clazz) {
        return Squadra.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Squadra squadra = (Squadra) target;

        String nome = squadra.getNome().trim();
        String anno = squadra.getAnnoFondazione().trim();
        String sede = squadra.getSede().trim();

        if (nome.isEmpty()) {
            errors.reject("NotBlank.squadra.nome");
        }

        if (anno.isEmpty()) {
            errors.reject("NotBlank.squadra.annoFondazione");
        }

        if (sede.isEmpty()) {
            errors.reject("NotBlank.squadra.sede");
        }
        
    }
}

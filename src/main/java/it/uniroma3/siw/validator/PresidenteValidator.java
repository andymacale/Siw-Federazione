package it.uniroma3.siw.validator;

import it.uniroma3.siw.model.Presidente;
import it.uniroma3.siw.service.PresidenteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class PresidenteValidator implements Validator {

    @Autowired PresidenteService presidenteService;

    @Override
    public boolean supports(Class<?> clazz) {
        return Presidente.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Presidente presidente = (Presidente) target;

        String codiceFiscale = presidente.getCodiceFiscale().trim();
        String luogoNascita = presidente.getLuogoNascita().trim();

        if (codiceFiscale.isEmpty()) {
            errors.reject("NotBlank.presidente.codiceFiscale");
        }

        if (luogoNascita.isEmpty()) {
            errors.reject("NotBlank.presidente.luogoNascita");
        }

        if (!codiceFiscale.isEmpty() && this.presidenteService.existsByCodiceFiscale(codiceFiscale)) {
            errors.reject("presidente.duplicate.codiceFiscale");
        }
    }
}

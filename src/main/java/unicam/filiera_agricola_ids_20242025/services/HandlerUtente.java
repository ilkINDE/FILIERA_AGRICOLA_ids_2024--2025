package unicam.filiera_agricola_ids_20242025.services;

import org.springframework.stereotype.Service;
import unicam.filiera_agricola_ids_20242025.models.Utenti.Utente;
import unicam.filiera_agricola_ids_20242025.repository.UtenteRepository;

@Service
public class HandlerUtente {

    private UtenteRepository utenteRepository;

    public HandlerUtente(UtenteRepository utenteRepository) {
        this.utenteRepository = utenteRepository;
    }

    public Utente registrazione(String email, String password) {
        Utente utente = new Utente(email, password);
        return  utenteRepository.save(utente);
    }
}

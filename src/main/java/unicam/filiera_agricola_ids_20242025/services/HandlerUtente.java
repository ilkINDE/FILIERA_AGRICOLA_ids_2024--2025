package unicam.filiera_agricola_ids_20242025.services;

import org.springframework.stereotype.Service;
import unicam.filiera_agricola_ids_20242025.models.Utenti.Acquirente;
import unicam.filiera_agricola_ids_20242025.models.Utenti.Utente;
import unicam.filiera_agricola_ids_20242025.repository.AcquirenteRepository;
import unicam.filiera_agricola_ids_20242025.repository.UtenteRepository;

@Service
public class HandlerUtente {

    private final UtenteRepository utenteRepository;
    private final AcquirenteRepository acquirenteRepository;

    public HandlerUtente(UtenteRepository utenteRepository, AcquirenteRepository acquirenteRepository) {
        this.utenteRepository = utenteRepository;
        this.acquirenteRepository = acquirenteRepository;
    }

    public Utente registrazione(String email, String password) {
        Utente utente = new Utente(email, password);
        utente = utenteRepository.save(utente);

        Acquirente acquirente = new Acquirente(utente);
        acquirenteRepository.save(acquirente);

        return utente;
    }

    //TODO verifica email duplicate per account
    //TODO mostra lista di ruoli
}

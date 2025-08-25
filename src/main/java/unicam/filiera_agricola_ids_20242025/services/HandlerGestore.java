package unicam.filiera_agricola_ids_20242025.services;

import org.springframework.stereotype.Service;
import unicam.filiera_agricola_ids_20242025.models.*;
import unicam.filiera_agricola_ids_20242025.models.Amministratori.Gestore;
import unicam.filiera_agricola_ids_20242025.models.Utenti.Utente;
import unicam.filiera_agricola_ids_20242025.repository.GestoreRepository;
import unicam.filiera_agricola_ids_20242025.repository.RichiestaRuoloRepository;
import unicam.filiera_agricola_ids_20242025.repository.UtenteRepository;

import java.util.List;

@Service
public class HandlerGestore {

    private final GestoreRepository gestoreRepository;
    private final RichiestaRuoloRepository richiestaRuoloRepository;
    private final UtenteRepository utenteRepository;

    public HandlerGestore(GestoreRepository gestoreRepository,
                          RichiestaRuoloRepository richiestaRuoloRepository,
                          UtenteRepository utenteRepository) {
        this.gestoreRepository = gestoreRepository;
        this.richiestaRuoloRepository = richiestaRuoloRepository;
        this.utenteRepository = utenteRepository;
    }


     // Restituisce l'unico Gestore (id = 1).
     // Se non esiste, lo crea.

    public Gestore getGestore() {
        return gestoreRepository.findById(1)
                .orElseGet(() -> {
                    Gestore nuovoGestore = new Gestore("Gestore Unico");
                    nuovoGestore.setIdGestore(1);
                    return gestoreRepository.save(nuovoGestore);
                });
    }


     // Restituisce tutte le richieste di ruolo in attesa.

    public List<RichiestaRuolo> getRichiesteInAttesa() {
        return richiestaRuoloRepository.findAll()
                .stream()
                .filter(r -> r.getStatoRichiesta() == StatoRichiesta.IN_ATTESA)
                .toList();
    }


     // Approva una richiesta: l'utente ottiene il ruolo richiesto.

    public void approvaRichiesta(int idRichiesta) {
        RichiestaRuolo richiesta = richiestaRuoloRepository.findById(idRichiesta)
                .orElseThrow(() -> new RuntimeException("Richiesta non trovata"));

        if (richiesta.getStatoRichiesta() != StatoRichiesta.IN_ATTESA) {
            throw new RuntimeException("Richiesta già processata");
        }

        // assegna il ruolo all'utente
        Utente utente = richiesta.getUtente();
        utente.getRuoli().add(richiesta.getRuoloRichiesto());
        utenteRepository.save(utente);

        // aggiorna stato richiesta
        richiesta.setStatoRichiesta(StatoRichiesta.APPROVATA);
        richiestaRuoloRepository.save(richiesta);
    }


     //Rifiuta una richiesta di ruolo.

    public void rifiutaRichiesta(int idRichiesta) {
        RichiestaRuolo richiesta = richiestaRuoloRepository.findById(idRichiesta)
                .orElseThrow(() -> new RuntimeException("Richiesta non trovata"));

        if (richiesta.getStatoRichiesta() != StatoRichiesta.IN_ATTESA) {
            throw new RuntimeException("Richiesta già processata");
        }

        richiesta.setStatoRichiesta(StatoRichiesta.RIFIUTATA);
        richiestaRuoloRepository.save(richiesta);
    }
}

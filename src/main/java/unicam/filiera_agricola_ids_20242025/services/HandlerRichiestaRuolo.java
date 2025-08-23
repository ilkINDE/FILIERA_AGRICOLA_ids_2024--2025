package unicam.filiera_agricola_ids_20242025.services;

import org.springframework.stereotype.Service;
import unicam.filiera_agricola_ids_20242025.models.*;
import unicam.filiera_agricola_ids_20242025.repository.GestoreRepository;
import unicam.filiera_agricola_ids_20242025.repository.RichiestaRuoloRepository;
import unicam.filiera_agricola_ids_20242025.repository.UtenteRepository;

@Service
public class HandlerRichiestaRuolo {

    private final UtenteRepository utenteRepository;
    private final RichiestaRuoloRepository richiestaRuoloRepository;
    private final GestoreRepository gestoreRepository;


    public HandlerRichiestaRuolo(RichiestaRuoloRepository richiestaRuoloRepository, UtenteRepository utenteRepository, GestoreRepository gestoreRepository) {
        this.richiestaRuoloRepository = richiestaRuoloRepository;
        this.utenteRepository = utenteRepository;
        this.gestoreRepository = gestoreRepository;

    }

    public RichiestaRuolo creaRichiesta(int idUtente, Ruolo ruoloRichiesto) {

        Utente utente = utenteRepository.findById(idUtente)
                .orElseThrow(() -> new RuntimeException("Utente non trovato"));

        if (utente.getRuoli().contains(ruoloRichiesto)) {
            throw new RuntimeException("l'Utente possiede già il ruolo richiesto");
        }

        boolean richiestaEsistente = richiestaRuoloRepository
                .findByUtenteAndRuoloRichiestoAndStatoRichiesta(utente, ruoloRichiesto, StatoRichiesta.IN_ATTESA)
                .isPresent();
        if (richiestaEsistente) {
            throw new RuntimeException("L'Utente ha già una richiesta in corso per questo ruolo");
        }

        Gestore gestore = gestoreRepository.findAll()
                .stream()
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Gestore non trovato"));

        RichiestaRuolo richiesta = new RichiestaRuolo(utente, ruoloRichiesto);

        // 🔹 associo il gestore
        richiesta.setGestore(gestore);

        gestore.getRichiesteRuolo().add(richiesta);
        return richiestaRuoloRepository.save(richiesta);

    }
}

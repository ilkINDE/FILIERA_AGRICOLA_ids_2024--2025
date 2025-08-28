package unicam.filiera_agricola_ids_20242025.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import unicam.filiera_agricola_ids_20242025.DTO.RichiestaDistributoreDTO;
import unicam.filiera_agricola_ids_20242025.DTO.RichiestaProduttoreDTO;
import unicam.filiera_agricola_ids_20242025.DTO.RichiestaTrasformatoreDTO;
import unicam.filiera_agricola_ids_20242025.models.*;
import unicam.filiera_agricola_ids_20242025.models.Amministratori.Gestore;
import unicam.filiera_agricola_ids_20242025.models.Utenti.Utente;
import unicam.filiera_agricola_ids_20242025.repository.GestoreRepository;
import unicam.filiera_agricola_ids_20242025.repository.RichiestaRuoloRepository;
import unicam.filiera_agricola_ids_20242025.repository.UtenteRepository;

@Service
public class HandlerRichiestaRuolo {

    private final UtenteRepository utenteRepository;
    private final RichiestaRuoloRepository richiestaRuoloRepository;
    private final GestoreRepository gestoreRepository;
    private final ObjectMapper objectMapper; // per serializzare i DTO in JSON


    public HandlerRichiestaRuolo(RichiestaRuoloRepository richiestaRuoloRepository, UtenteRepository utenteRepository, GestoreRepository gestoreRepository, ObjectMapper objectMapper) {
        this.richiestaRuoloRepository = richiestaRuoloRepository;
        this.utenteRepository = utenteRepository;
        this.gestoreRepository = gestoreRepository;
        this.objectMapper = objectMapper;
    }

    public RichiestaRuolo creaRichiestaProduttore(int idUtente, RichiestaProduttoreDTO dto) {

            Utente utente = utenteRepository.findById(idUtente)
                  .orElseThrow(() -> new RuntimeException("Utente non trovato"));

        if (utente.getRuoli().contains(Ruolo.PRODUTTORE)) {
              throw new RuntimeException("L'utente possiede già il ruolo richiesto");
          }

        boolean richiestaEsistente = richiestaRuoloRepository
                .findByUtenteAndRuoloRichiestoAndStatoRichiesta(utente, Ruolo.PRODUTTORE, StatoRichiesta.IN_ATTESA)
                .isPresent();
        if (richiestaEsistente) {
            throw new RuntimeException("L'utente ha già una richiesta in corso per questo ruolo");
        }

        // trasformo DTO in JSON
        String datiExtra;
        try {
            datiExtra = objectMapper.writeValueAsString(dto);
         }  catch (Exception exception) {
                throw new RuntimeException("Errore serializzazione dati extra", exception);
         }

        Gestore gestore = gestoreRepository.findAll()
                .stream()
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Gestore non trovato"));


        RichiestaRuolo richiesta = new RichiestaRuolo(utente, Ruolo.PRODUTTORE, datiExtra);
        richiesta.setGestore(gestore);

        gestore.getRichiesteRuolo().add(richiesta);
        return richiestaRuoloRepository.save(richiesta);
    }

    public RichiestaRuolo creaRichiestaTrasformatore(int idUtente, RichiestaTrasformatoreDTO dto) {

        Utente utente = utenteRepository.findById(idUtente)
                .orElseThrow(() -> new RuntimeException("Utente non trovato"));

        if (utente.getRuoli().contains(Ruolo.TRASFORMATORE)) {
            throw new RuntimeException("L'utente possiede già il ruolo richiesto");
        }

        boolean richiestaEsistente = richiestaRuoloRepository
                .findByUtenteAndRuoloRichiestoAndStatoRichiesta(utente, Ruolo.TRASFORMATORE, StatoRichiesta.IN_ATTESA)
                .isPresent();
        if (richiestaEsistente) {
            throw new RuntimeException("L'utente ha già una richiesta in corso per questo ruolo");
        }

        String datiExtra;
        try {
            datiExtra = objectMapper.writeValueAsString(dto);
        } catch (Exception e) {
            throw new RuntimeException("Errore serializzazione dati extra", e);
        }

        Gestore gestore = gestoreRepository.findAll().stream()
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Gestore non trovato"));

        RichiestaRuolo richiesta = new RichiestaRuolo(utente, Ruolo.TRASFORMATORE, datiExtra);
        richiesta.setGestore(gestore);

        gestore.getRichiesteRuolo().add(richiesta);

        return richiestaRuoloRepository.save(richiesta);
    }

    public RichiestaRuolo creaRichiestaDistributore(int idUtente, RichiestaDistributoreDTO dto) {

        Utente utente = utenteRepository.findById(idUtente)
                .orElseThrow(() -> new RuntimeException("Utente non trovato"));

        if (utente.getRuoli().contains(Ruolo.DISTRIBUTORE)) {
            throw new RuntimeException("L'utente possiede già il ruolo richiesto");
        }

        boolean richiestaEsistente = richiestaRuoloRepository
                .findByUtenteAndRuoloRichiestoAndStatoRichiesta(utente, Ruolo.DISTRIBUTORE, StatoRichiesta.IN_ATTESA)
                .isPresent();
        if (richiestaEsistente) {
            throw new RuntimeException("L'utente ha già una richiesta in corso per questo ruolo");
        }

        String datiExtra;
        try {
            datiExtra = objectMapper.writeValueAsString(dto);
        } catch (Exception e) {
            throw new RuntimeException("Errore serializzazione dati extra", e);
        }

        Gestore gestore = gestoreRepository.findAll().stream()
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Gestore non trovato"));

        RichiestaRuolo richiesta = new RichiestaRuolo(utente, Ruolo.DISTRIBUTORE, datiExtra);
        richiesta.setGestore(gestore);

        gestore.getRichiesteRuolo().add(richiesta);

        return richiestaRuoloRepository.save(richiesta);
    }


    public RichiestaRuolo creaRichiestaAnimatore(int idUtente) {

        Utente utente = utenteRepository.findById(idUtente)
                .orElseThrow(() -> new RuntimeException("Utente non trovato"));

        if (utente.getRuoli().contains(Ruolo.ANIMATORE)) {
            throw new RuntimeException("L'utente possiede già il ruolo richiesto");
        }

        boolean richiestaEsistente = richiestaRuoloRepository
                .findByUtenteAndRuoloRichiestoAndStatoRichiesta(utente, Ruolo.ANIMATORE, StatoRichiesta.IN_ATTESA)
                .isPresent();
        if (richiestaEsistente) {
            throw new RuntimeException("L'utente ha già una richiesta in corso per questo ruolo");
        }

        Gestore gestore = gestoreRepository.findAll().stream()
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Gestore non trovato"));

        // niente dati extra per animatore
        RichiestaRuolo richiesta = new RichiestaRuolo(utente, Ruolo.ANIMATORE, null);
        richiesta.setGestore(gestore);

        gestore.getRichiesteRuolo().add(richiesta);

        return richiestaRuoloRepository.save(richiesta);
    }

    public RichiestaRuolo creaRichiestaCuratore(int idUtente) {

        Utente utente = utenteRepository.findById(idUtente)
                .orElseThrow(() -> new RuntimeException("Utente non trovato"));

        if (utente.getRuoli().contains(Ruolo.CURATORE)) {
            throw new RuntimeException("L'utente possiede già il ruolo richiesto");
        }

        boolean richiestaEsistente = richiestaRuoloRepository
                .findByUtenteAndRuoloRichiestoAndStatoRichiesta(utente, Ruolo.CURATORE, StatoRichiesta.IN_ATTESA)
                .isPresent();
        if (richiestaEsistente) {
            throw new RuntimeException("L'utente ha già una richiesta in corso per questo ruolo");
        }

        Gestore gestore = gestoreRepository.findAll().stream()
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Gestore non trovato"));

        // niente dati extra per curatore
        RichiestaRuolo richiesta = new RichiestaRuolo(utente, Ruolo.CURATORE, null);
        richiesta.setGestore(gestore);

        gestore.getRichiesteRuolo().add(richiesta);

        return richiestaRuoloRepository.save(richiesta);
    }
}

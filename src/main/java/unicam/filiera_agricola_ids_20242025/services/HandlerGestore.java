package unicam.filiera_agricola_ids_20242025.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;
import unicam.filiera_agricola_ids_20242025.DTO.RichiestaDistributoreDTO;
import unicam.filiera_agricola_ids_20242025.DTO.RichiestaProduttoreDTO;
import unicam.filiera_agricola_ids_20242025.DTO.RichiestaTrasformatoreDTO;
import unicam.filiera_agricola_ids_20242025.models.*;
import unicam.filiera_agricola_ids_20242025.models.Amministratori.Curatore;
import unicam.filiera_agricola_ids_20242025.models.Amministratori.Gestore;
import unicam.filiera_agricola_ids_20242025.models.State.StateRichiesta.StatoRichiesta;
import unicam.filiera_agricola_ids_20242025.models.Utenti.Animatore;
import unicam.filiera_agricola_ids_20242025.models.Utenti.Utente;
import unicam.filiera_agricola_ids_20242025.models.Utenti.Venditori.Distributore;
import unicam.filiera_agricola_ids_20242025.models.Utenti.Venditori.Produttore;
import unicam.filiera_agricola_ids_20242025.models.Utenti.Venditori.Trasformatore;
import unicam.filiera_agricola_ids_20242025.repository.*;

import java.util.List;

@Service
public class HandlerGestore {

    private final GestoreRepository gestoreRepository;
    private final RichiestaRuoloRepository richiestaRuoloRepository;
    private final UtenteRepository utenteRepository;
    private final VenditoreRepository venditoreRepository;
    private final AnimatoreRepository animatoreRepository;
    private final CuratoreRepository curatoreRepository;
    private final ObjectMapper objectMapper;

    public HandlerGestore(GestoreRepository gestoreRepository,
                          RichiestaRuoloRepository richiestaRuoloRepository,
                          UtenteRepository utenteRepository, VenditoreRepository venditoreRepository, AnimatoreRepository animatoreRepository, CuratoreRepository curatoreRepository, ObjectMapper objectMapper) {
        this.gestoreRepository = gestoreRepository;
        this.richiestaRuoloRepository = richiestaRuoloRepository;
        this.utenteRepository = utenteRepository;
        this.venditoreRepository = venditoreRepository;
        this.animatoreRepository = animatoreRepository;
        this.curatoreRepository = curatoreRepository;
        this.objectMapper = objectMapper;
    }
    //Restituisce il gestore
    @PostConstruct
    public void initGestore() {
        if (gestoreRepository.findById(1).isEmpty()) {
            Gestore g = new Gestore("Gestore Unico");
            g.setIdGestore(1);
            gestoreRepository.save(g);
        }
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

         Utente utente = richiesta.getUtente();

         Ruolo ruolo = richiesta.getRuoloRichiesto();
         utente.getRuoli().add(ruolo);
         utenteRepository.save(utente);

         switch (ruolo) {
             case PRODUTTORE -> {
                 try {
                     RichiestaProduttoreDTO dto = objectMapper.readValue(richiesta.getDatiExtra(), RichiestaProduttoreDTO.class);
                     Produttore produttore =
                             new Produttore(utente, dto.nome(), dto.piva(), dto.metodoColtivazione());
                     venditoreRepository.save(produttore);
                 } catch (Exception e) {
                     throw new RuntimeException("Errore deserializzazione dati extra", e);
                 }
             }
             case TRASFORMATORE -> {
                 try {
                     RichiestaTrasformatoreDTO dto =
                             objectMapper.readValue(richiesta.getDatiExtra(), RichiestaTrasformatoreDTO.class);
                     Trasformatore trasformatore =
                             new Trasformatore(utente, dto.nome(), dto.piva(), dto.processoDiTrasformazione());
                     venditoreRepository.save(trasformatore);
                 } catch (Exception e) {
                     throw new RuntimeException("Errore deserializzazione dati extra per Trasformatore", e);
                 }
             }
             case DISTRIBUTORE -> {
                 try {
                     RichiestaDistributoreDTO dto =
                             objectMapper.readValue(richiesta.getDatiExtra(), RichiestaDistributoreDTO.class);
                     Distributore distributore =
                             new Distributore(utente, dto.nome(), dto.piva());
                     venditoreRepository.save(distributore);
                 } catch (Exception e) {
                     throw new RuntimeException("Errore deserializzazione dati extra per Distributore", e);
                 }
             }
             case ANIMATORE -> {
                 Animatore animatore = new Animatore(utente);
                 animatoreRepository.save(animatore);
             }
             case CURATORE -> {
                 Curatore curatore = new Curatore(utente);
                 curatoreRepository.save(curatore);
             }
         }

         richiesta.approva();
         richiestaRuoloRepository.save(richiesta);
     }

    //Rifiuta una richiesta di ruolo.
    public void rifiutaRichiesta(int idRichiesta) {
        RichiestaRuolo richiesta = richiestaRuoloRepository.findById(idRichiesta)
                .orElseThrow(() -> new RuntimeException("Richiesta non trovata"));

        if (richiesta.getStatoRichiesta() != StatoRichiesta.IN_ATTESA) {
            throw new RuntimeException("Richiesta già processata");
        }

        richiesta.rifiuta();
        richiestaRuoloRepository.save(richiesta);
    }
}

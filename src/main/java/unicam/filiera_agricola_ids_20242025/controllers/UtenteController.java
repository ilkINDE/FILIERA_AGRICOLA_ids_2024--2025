package unicam.filiera_agricola_ids_20242025.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import unicam.filiera_agricola_ids_20242025.models.ContenutiSocial.ContenutoSocial;
import unicam.filiera_agricola_ids_20242025.models.Utenti.AnimatoreDellaFiliera.Evento;
import unicam.filiera_agricola_ids_20242025.models.Prodotti.Pacchetto;
import unicam.filiera_agricola_ids_20242025.models.Prodotti.Prodotto;
import unicam.filiera_agricola_ids_20242025.services.HandlerUtente;
import unicam.filiera_agricola_ids_20242025.models.Utenti.Utente.Utente;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/utenti")
public class UtenteController {

    private final HandlerUtente handlerUtente;

    @Autowired
    public UtenteController(HandlerUtente handlerUtente) {
        this.handlerUtente = handlerUtente;
    }

    // registrazione
    @PostMapping("/registrazione")
    public ResponseEntity<Utente> registra(@RequestParam String nome,
                                           @RequestParam String cognome,
                                           @RequestParam LocalDate dataNascita,
                                           @RequestParam String comuneDiProvenienza,
                                           @RequestParam String email,
                                           @RequestParam String password) {
        Utente nuovo = handlerUtente.registrazione(nome, cognome, dataNascita, comuneDiProvenienza, email, password);
        return ResponseEntity.ok(nuovo);
    }

    // ruoli disponibili
    @GetMapping("{idUtente}/ruoli")
    public ResponseEntity<?> listaRuoli(
            @PathVariable int idUtente
    ) {
        return ResponseEntity.ok(handlerUtente.getRuoliDisponibili(idUtente));
    }

    // prodotti caricati nella piattaforma
    @GetMapping("/prodottiDisponibili")
    public ResponseEntity<List<Prodotto>> getProdottiDisponibili() {
        return ResponseEntity.ok(handlerUtente.getProdottiDisponibili());
    }

    // info su prodotto singolo
    @GetMapping("/esploraProdotto/{idProdotto}")
    public ResponseEntity<Prodotto> esploraProdotto(@PathVariable int idProdotto) {
        return ResponseEntity.ok(handlerUtente.esploraProdotto(idProdotto));
    }

    // pacchetti disponbili nella piattaforma
    @GetMapping("/pacchettiDisponibili")
    public ResponseEntity<List<Pacchetto>> getPacchettiDisponibili() {
        return ResponseEntity.ok(handlerUtente.getPacchettiDisponibili());
    }

    // info su pacchetto singolo
    @GetMapping("/esploraPacchetto/{idPacchetto}")
    public ResponseEntity<Pacchetto> esploraPacchetto(@PathVariable int idPacchetto) {
        return ResponseEntity.ok(handlerUtente.esploraPacchetto(idPacchetto));
    }

    // eventi caricati sulla piattaforma
    @GetMapping("/eventiDisponibili")
    public ResponseEntity<List<Evento>> getEventiDisponibili() {
        return ResponseEntity.ok(handlerUtente.getEventiDisponibili());
    }

    // info su evento singolo
    @GetMapping("/esploraEvento/{idEvento}")
    public ResponseEntity<Evento> esploraEvento(@PathVariable int idEvento) {
        return ResponseEntity.ok(handlerUtente.esploraEvento(idEvento));
    }

    // mostra tutti contenuti pubblicati su prodotti caricati
    @GetMapping("/ContenutiPubblicati")
    public ResponseEntity<List<ContenutoSocial>> contenutiPubblicati() {
        return ResponseEntity.ok(handlerUtente.getContenutiPubblicati());
    }

    // accede alla mappa
    @GetMapping("/mappaOSM")
    public ResponseEntity<List<String>> esploraMappaOSM() {
        return ResponseEntity.ok(handlerUtente.esploraMappaOSM());
    }

}

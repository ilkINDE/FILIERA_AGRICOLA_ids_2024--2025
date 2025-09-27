package unicam.filiera_agricola_ids_20242025.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import unicam.filiera_agricola_ids_20242025.models.Evento;
import unicam.filiera_agricola_ids_20242025.models.Prodotti.Pacchetto;
import unicam.filiera_agricola_ids_20242025.models.Prodotti.Prodotto;
import unicam.filiera_agricola_ids_20242025.services.HandlerUtente;
import unicam.filiera_agricola_ids_20242025.models.Utenti.Utente;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/utenti")
public class UtenteController {

    private final HandlerUtente handlerUtente;

    @Autowired
    public UtenteController(HandlerUtente handlerUtente) {
        this.handlerUtente = handlerUtente;
    }

    // REGISTRAZIONE
    @PostMapping("/registrazione")
    public ResponseEntity<Utente> registra(@RequestParam String nome,
                                           @RequestParam String cognome,
                                           @RequestParam Date dataNascita,
                                           @RequestParam String comuneDiProvenienza,
                                           @RequestParam String email,
                                           @RequestParam String password) {
        Utente nuovo = handlerUtente.registrazione(nome, cognome, dataNascita, comuneDiProvenienza, email, password);
        return ResponseEntity.ok(nuovo);
    }

    // LISTA RUOLI DISPONIBILI
    @GetMapping("/ruoli")
    public ResponseEntity<?> listaRuoli() {
        return ResponseEntity.ok(handlerUtente.getRuoliDisponibili());
    }

    // --- PRODOTTI ---
    @GetMapping("/prodottiDisponibili")
    public ResponseEntity<List<Prodotto>> getProdottiDisponibili() {
        return ResponseEntity.ok(handlerUtente.getProdottiDisponibili());
    }

    @GetMapping("/esploraProdotto/{idProdotto}")
    public ResponseEntity<Prodotto> esploraProdotto(@PathVariable int idProdotto) {
        return ResponseEntity.ok(handlerUtente.esploraProdotto(idProdotto));
    }

    // --- PACCHETTI ---
    @GetMapping("/pacchettiDisponibili")
    public ResponseEntity<List<Pacchetto>> getPacchettiDisponibili() {
        return ResponseEntity.ok(handlerUtente.getPacchettiDisponibili());
    }

    @GetMapping("/esploraPacchetto/{idPacchetto}")
    public ResponseEntity<Pacchetto> esploraPacchetto(@PathVariable int idPacchetto) {
        return ResponseEntity.ok(handlerUtente.esploraPacchetto(idPacchetto));
    }

    // --- EVENTI ---
    @GetMapping("/eventiDisponibili")
    public ResponseEntity<List<Evento>> getEventiDisponibili() {
        return ResponseEntity.ok(handlerUtente.getEventiDisponibili());
    }

    @GetMapping("/esploraEvento/{idEvento}")
    public ResponseEntity<Evento> esploraEvento(@PathVariable int idEvento) {
        return ResponseEntity.ok(handlerUtente.esploraEvento(idEvento));
    }


    @GetMapping("/mappa")
    public ResponseEntity<List<String>> esploraMappa() {
        return ResponseEntity.ok(handlerUtente.esploraMappa());
    }

}

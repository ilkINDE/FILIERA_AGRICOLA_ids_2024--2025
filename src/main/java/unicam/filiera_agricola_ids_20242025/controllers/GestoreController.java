package unicam.filiera_agricola_ids_20242025.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import unicam.filiera_agricola_ids_20242025.models.Amministratori.Gestore;
import unicam.filiera_agricola_ids_20242025.models.RichiestaRuolo;
import unicam.filiera_agricola_ids_20242025.services.HandlerGestore;

import java.util.List;

@RestController
@RequestMapping("/gestore")
public class GestoreController {

    private final HandlerGestore handlerGestore;

    @Autowired
    public GestoreController(HandlerGestore handlerGestore) {
        this.handlerGestore = handlerGestore;
    }

    //  Recupera il gestore unico
    @GetMapping
    public ResponseEntity<Gestore> getGestore() {
        return ResponseEntity.ok(handlerGestore.getGestore());
    }

    //  Lista richieste in attesa
    @GetMapping("/richieste/inattesa")
    public ResponseEntity<List<RichiestaRuolo>> getRichiesteInAttesa() {
        return ResponseEntity.ok(handlerGestore.getRichiesteInAttesa());
    }

    //  Approva richiesta
    @PostMapping("/richieste/{idRichiesta}/approva")
    public ResponseEntity<String> approvaRichiesta(@PathVariable int idRichiesta) {
        handlerGestore.approvaRichiesta(idRichiesta);
        return ResponseEntity.ok("Richiesta approvata con successo");
    }

    //  Rifiuta richiesta
    @PostMapping("/richieste/{idRichiesta}/rifiuta")
    public ResponseEntity<String> rifiutaRichiesta(@PathVariable int idRichiesta) {
        handlerGestore.rifiutaRichiesta(idRichiesta);
        return ResponseEntity.ok("Richiesta rifiutata con successo");
    }
}

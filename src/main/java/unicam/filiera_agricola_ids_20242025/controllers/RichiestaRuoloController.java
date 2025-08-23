package unicam.filiera_agricola_ids_20242025.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import unicam.filiera_agricola_ids_20242025.services.HandlerRichiestaRuolo;
import unicam.filiera_agricola_ids_20242025.models.RichiestaRuolo;
import unicam.filiera_agricola_ids_20242025.models.Ruolo;

@RestController
@RequestMapping("/richieste-ruolo")
public class RichiestaRuoloController {

    private final HandlerRichiestaRuolo handlerRichiestaRuolo;



    @Autowired
    public RichiestaRuoloController(HandlerRichiestaRuolo handlerRichiestaRuolo) {
        this.handlerRichiestaRuolo = handlerRichiestaRuolo;

    }

    // CREAZIONE RICHIESTA DI NUOVO RUOLO
    @PostMapping("/{idUtente}/creaRichiesta")
    public ResponseEntity<RichiestaRuolo> creaRichiesta(@PathVariable int idUtente,
                                                        @RequestParam Ruolo ruolo) {
        RichiestaRuolo richiesta = handlerRichiestaRuolo.creaRichiesta(idUtente, ruolo);
        return ResponseEntity.ok(richiesta);
    }
}

package unicam.filiera_agricola_ids_20242025.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import unicam.filiera_agricola_ids_20242025.services.HandlerUtente;
import unicam.filiera_agricola_ids_20242025.models.Utente;

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
    public ResponseEntity<Utente> registra(@RequestParam String email,
                                           @RequestParam String password) {
        Utente nuovo = handlerUtente.registrazione(email, password);
        return ResponseEntity.ok(nuovo);
    }
}

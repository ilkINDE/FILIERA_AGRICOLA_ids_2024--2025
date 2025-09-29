package unicam.filiera_agricola_ids_20242025.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import unicam.filiera_agricola_ids_20242025.DTO.*;
import unicam.filiera_agricola_ids_20242025.services.HandlerRichiestaRuolo;

@RestController
@RequestMapping("/richieste-ruolo")
public class RichiestaRuoloController {

    private final HandlerRichiestaRuolo handlerRichiestaRuolo;


    @Autowired
    public RichiestaRuoloController(HandlerRichiestaRuolo handlerRichiestaRuolo) {
        this.handlerRichiestaRuolo = handlerRichiestaRuolo;

    }

    @PostMapping("/{idUtente}/richiediRuolo/Venditore/Produttore")
    public ResponseEntity<Void> creaRichiestaProduttore(
            @PathVariable int idUtente,
            @RequestBody @Valid RichiestaProduttoreDTO dto) {

        handlerRichiestaRuolo.creaRichiestaProduttore(idUtente, dto);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{idUtente}/richiediRuolo/Venditore/Trasformatore")
    public ResponseEntity<Void> creaRichiestaTrasformatore(
            @PathVariable int idUtente,
            @RequestBody @Valid RichiestaTrasformatoreDTO dto) {

        handlerRichiestaRuolo.creaRichiestaTrasformatore(idUtente, dto);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{idUtente}/richiediRuolo/Venditore/DistributoreDiTipicità")
    public ResponseEntity<Void> creaRichiestaDistributore(
            @PathVariable int idUtente,
            @RequestBody @Valid RichiestaDistributoreDTO dto) {

        handlerRichiestaRuolo.creaRichiestaDistributore(idUtente, dto);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{idUtente}/richiediRuolo/animatoreDellaFiliera")
    public ResponseEntity<Void> creaRichiestaAnimatore(
            @PathVariable int idUtente ) {

        handlerRichiestaRuolo.creaRichiestaAnimatore(idUtente);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{idUtente}/richiediRuolo/curatore")
    public ResponseEntity<Void> creaRichiestaCuratore(
            @PathVariable int idUtente) {

        handlerRichiestaRuolo.creaRichiestaCuratore(idUtente);
        return ResponseEntity.ok().build();
    }
}
package unicam.filiera_agricola_ids_20242025.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import unicam.filiera_agricola_ids_20242025.models.Prodotti.Pacchetto;
import unicam.filiera_agricola_ids_20242025.models.Prodotti.Prodotto;
import unicam.filiera_agricola_ids_20242025.services.HandlerCuratore;

import java.util.List;

@RestController
@RequestMapping("/curatori")
public class CuratoreController {

    private final HandlerCuratore handlerCuratore;

    @Autowired
    public CuratoreController(HandlerCuratore handlerCuratore) {
        this.handlerCuratore = handlerCuratore;
    }

    // Ottiene tutti i prodotti in attesa
    @GetMapping("/{idCuratore}/prodotti/inAttesa")
    public ResponseEntity<List<Prodotto>> getProdottiInAttesa(@PathVariable int idCuratore) {
        List<Prodotto> prodotti = handlerCuratore.getProdottiInAttesa(idCuratore);
        return ResponseEntity.ok(prodotti);
    }

    // Ottiene tutti i pacchetti in attesa
    @GetMapping("/{idCuratore}/pacchetti/inAttesa")
    public ResponseEntity<List<Pacchetto>> getPacchettiInAttesa(@PathVariable int idCuratore) {
        List<Pacchetto> pacchetti = handlerCuratore.getPacchettiInAttesa(idCuratore);
        return ResponseEntity.ok(pacchetti);
    }

    // Approva un prodotto
    @PostMapping("/{idCuratore}/prodotti/{idProdotto}/approva")
    public ResponseEntity<Prodotto> approvaProdotto(
            @PathVariable int idCuratore,
            @PathVariable int idProdotto) {
        Prodotto prodotto = handlerCuratore.approvaProdotto(idCuratore, idProdotto);
        return ResponseEntity.ok(prodotto);
    }

    // Approva un pacchetto
    @PostMapping("/{idCuratore}/pacchetti/{idPacchetto}/approva")
    public ResponseEntity<Pacchetto> approvaPacchetto(
            @PathVariable int idCuratore,
            @PathVariable int idPacchetto) {
        Pacchetto pacchetto = handlerCuratore.approvaPacchetto(idCuratore, idPacchetto);
        return ResponseEntity.ok(pacchetto);
    }

    // Rifiuta un prodotto
    @PostMapping("/{idCuratore}/prodotti/{idProdotto}/rifiuta")
    public ResponseEntity<Prodotto> rifiutaProdotto(
            @PathVariable int idCuratore,
            @PathVariable int idProdotto,
            @RequestParam(required = false) String motivazione) {
        Prodotto prodotto = handlerCuratore.rifiutaProdotto(idCuratore, idProdotto);
        return ResponseEntity.ok(prodotto);
    }

    // Rifiuta un pacchetto
    @PostMapping("/{idCuratore}/pacchetti/{idPacchetto}/rifiuta")
    public ResponseEntity<Pacchetto> rifiutaPacchetto(
            @PathVariable int idCuratore,
            @PathVariable int idPacchetto) {
        Pacchetto pacchetto = handlerCuratore.rifiutaPacchetto(idCuratore, idPacchetto);
        return ResponseEntity.ok(pacchetto);
    }
}
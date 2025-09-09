package unicam.filiera_agricola_ids_20242025.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import unicam.filiera_agricola_ids_20242025.models.Carrello;
import unicam.filiera_agricola_ids_20242025.models.Ordine;
import unicam.filiera_agricola_ids_20242025.models.Prodotti.Pacchetto;
import unicam.filiera_agricola_ids_20242025.models.Prodotti.Prodotto;
import unicam.filiera_agricola_ids_20242025.services.HandlerAcquirente;

import java.util.List;

@RestController
@RequestMapping("/acquirente")
public class AcquirenteController {

    private final HandlerAcquirente handlerAcquirente;

    @Autowired
    public AcquirenteController(HandlerAcquirente handlerAcquirente) {
        this.handlerAcquirente = handlerAcquirente;
    }

    //  Mostra solo i prodotti approvati
    @GetMapping("/prodotti")
    public List<Prodotto> getProdottiDisponibili() {
        return handlerAcquirente.getProdottiDisponibili();
    }

    //  Mostra solo i pacchetti approvati
    @GetMapping("/pacchetti")
    public List<Pacchetto> getPacchettiDisponibili() {
        return handlerAcquirente.getPacchettiDisponibili();
    }

    //  Aggiungi prodotto al carrello
    @PostMapping("/{idAcquirente}/carrello/aggiuntaProdotti/{idProdotto}")
    public Carrello aggiungiProdottoAlCarrello(
            @PathVariable int idAcquirente,
            @PathVariable int idProdotto,
            @RequestParam int quantita) {
        return handlerAcquirente.aggiungiProdottoAlCarrello(idAcquirente, idProdotto, quantita);
    }

    //  Aggiungi pacchetto al carrello
    @PostMapping("/{idAcquirente}/carrello/aggiuntaPacchetti/{idPacchetto}")
    public Carrello aggiungiPacchettoAlCarrello(
            @PathVariable int idAcquirente,
            @PathVariable int idPacchetto,
            @RequestParam int quantita) {
        return handlerAcquirente.aggiungiPacchettoAlCarrello(idAcquirente, idPacchetto, quantita);
    }

    //  Rimuovi prodotto dal carrello
    @DeleteMapping("/{idAcquirente}/carrello/rimozioneProdotti/{idProdotto}")
    public Carrello rimuoviProdottoDalCarrello(
            @PathVariable int idAcquirente,
            @PathVariable int idProdotto) {
        return handlerAcquirente.rimuoviProdottoDalCarrello(idAcquirente, idProdotto);
    }

    //  Rimuovi pacchetto dal carrello
    @DeleteMapping("/{idAcquirente}/carrello/rimozionePacchetti/{idPacchetto}")
    public Carrello rimuoviPacchettoDalCarrello(
            @PathVariable int idAcquirente,
            @PathVariable int idPacchetto) {
        return handlerAcquirente.rimuoviPacchettoDalCarrello(idAcquirente, idPacchetto);
    }

    //  Acquista i prodotti nel carrello
    @PostMapping("/{idAcquirente}/acquista")
    public Ordine acquistaCarrello(@PathVariable int idAcquirente) {
        return handlerAcquirente.acquistaCarrello(idAcquirente);
    }
}
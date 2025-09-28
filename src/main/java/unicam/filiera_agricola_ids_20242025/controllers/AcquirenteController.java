package unicam.filiera_agricola_ids_20242025.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import unicam.filiera_agricola_ids_20242025.models.Utenti.Acquirente.Carrello;
import unicam.filiera_agricola_ids_20242025.models.Evento;
import unicam.filiera_agricola_ids_20242025.models.Utenti.Acquirente.CarrelloItem;
import unicam.filiera_agricola_ids_20242025.models.Utenti.Acquirente.Ordine;
import unicam.filiera_agricola_ids_20242025.models.Utenti.Acquirente.PrenotazioneEvento;
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
    @GetMapping("/prodottiDisponibili")
    public List<Prodotto> getProdottiDisponibili() {
        return handlerAcquirente.getProdottiDisponibili();
    }

    //  Mostra solo i pacchetti approvati
    @GetMapping("/pacchettiDisponibili")
    public List<Pacchetto> getPacchettiDisponibili() {
        return handlerAcquirente.getPacchettiDisponibili();
    }

    // Mostra riepilogo del carrello
    @GetMapping("{idAcquirente}/riepilogoCarrello")
    public Carrello getRiepilogoCarrello(
            @PathVariable int idAcquirente
    ) { return handlerAcquirente.riepilogoCarrello(idAcquirente);}

    // Aggiungi un item al carrello (prodotto o pacchetto)
    @PostMapping("/{idAcquirente}/carrello/aggiungiItem")
    public Carrello aggiungiItemAlCarrello(
            @PathVariable int idAcquirente,
            @RequestParam(required = false) Integer idProdotto,
            @RequestParam(required = false) Integer idPacchetto,
            @RequestParam int quantita) {
        return handlerAcquirente.aggiungiItemAlCarrello(idAcquirente, idProdotto, idPacchetto, quantita);
    }

    //  Rimuovi item dal carrello(pacchetto o prodotto)
    @DeleteMapping("/{idAcquirente}/carrello/rimozioneProdotti")
    public Carrello rimuoviProdottoDalCarrello(
            @PathVariable int idAcquirente,
            @RequestParam int idProdotto,
            @RequestParam int idPacchetto) {
        return handlerAcquirente.rimuoviItemDalCarrello(idAcquirente, idProdotto, idPacchetto);
    }

    // Svuota il carrello
    @DeleteMapping("/{idAcquirente}/carrello/svuotaCarrello")
    public void svuotaCarrello(
            @PathVariable int idAcquirente){
        handlerAcquirente.svuotaCarrello(idAcquirente);
    }

    //  Acquista i prodotti nel carrello
    @PostMapping("/{idAcquirente}/acquista")
    public Ordine acquistaCarrello(@PathVariable int idAcquirente) {
        return handlerAcquirente.acquistaCarrello(idAcquirente);
    }

    // Mostra solo gli eventi caricati in piattaforma
    @GetMapping("/eventiDisponibili")
    public List<Evento> getEventiDisponibili() {
        return handlerAcquirente.getEventiDisponibili();
    }


    // Prenota un evento
    @PostMapping("/{idAcquirente}/prenota/{idEvento}")
    public PrenotazioneEvento prenotaEvento(@PathVariable int idAcquirente,
                                            @PathVariable int idEvento,
                                            @RequestParam int posti) {
        return handlerAcquirente.prenotaEvento(idAcquirente, idEvento, posti);
    }
}
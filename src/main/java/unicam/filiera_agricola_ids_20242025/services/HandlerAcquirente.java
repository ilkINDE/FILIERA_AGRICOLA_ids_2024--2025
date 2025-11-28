package unicam.filiera_agricola_ids_20242025.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import unicam.filiera_agricola_ids_20242025.models.MetodoDiPagamento.CartaDiCredito;
import unicam.filiera_agricola_ids_20242025.models.Prodotti.Pacchetto;
import unicam.filiera_agricola_ids_20242025.models.Prodotti.Prodotto;
import unicam.filiera_agricola_ids_20242025.models.State.StateProdotto.StatoProdotto;
import unicam.filiera_agricola_ids_20242025.models.Utenti.Acquirente.*;
import unicam.filiera_agricola_ids_20242025.models.Utenti.AnimatoreDellaFiliera.Evento;
import unicam.filiera_agricola_ids_20242025.repository.*;

import java.time.LocalDate;
import java.util.List;

@Service
public class HandlerAcquirente {

    private final AcquirenteRepository acquirenteRepository;
    private final ProdottoRepository prodottoRepository;
    private final PacchettoRepository pacchettoRepository;
    private final CarrelloRepository carrelloRepository;
    private final OrdineRepository ordineRepository;
    private final EventoRepository eventoRepository;
    private final PrenotazioneEventoRepository prenotazioneEventoRepository;

    @Autowired
    public HandlerAcquirente(AcquirenteRepository acquirenteRepository,
                             ProdottoRepository prodottoRepository,
                             PacchettoRepository pacchettoRepository,
                             CarrelloRepository carrelloRepository,
                             OrdineRepository ordineRepository,
                             EventoRepository eventoRepository,
                             PrenotazioneEventoRepository prenotazioneEventoRepository) {
        this.acquirenteRepository = acquirenteRepository;
        this.prodottoRepository = prodottoRepository;
        this.pacchettoRepository = pacchettoRepository;
        this.carrelloRepository = carrelloRepository;
        this.ordineRepository = ordineRepository;
        this.eventoRepository = eventoRepository;
        this.prenotazioneEventoRepository = prenotazioneEventoRepository;
    }

    //  Mostra solo i prodotti approvati
    public List<Prodotto> getProdottiDisponibili() {
        return prodottoRepository.findAll()
                .stream()
                .filter(p -> p.getStatoProdotto() == StatoProdotto.APPROVATO)
                .toList();
    }

    //  Mostra solo i pacchetti approvati
    public List<Pacchetto> getPacchettiDisponibili() {
        return pacchettoRepository.findAll()
                .stream()
                .filter(p -> p.getStatoProdotto() == StatoProdotto.APPROVATO)
                .toList();
    }

    // Mostra il riepilogo del carrello
    public Carrello riepilogoCarrello(int idAcquirente){

        Acquirente acquirente = acquirenteRepository.findById(idAcquirente)
                .orElseThrow(() -> new RuntimeException("Acquirente non trovato"));

        return acquirente.getCarrello();
    }

    //  Aggiungi prodotto o pacchetto al carrello
    public Carrello aggiungiItemAlCarrello(int idAcquirente, Integer idProdotto, Integer idPacchetto, int quantita) {

        if ((idProdotto == null && idPacchetto == null) ||
                (idProdotto != null && idPacchetto != null)) {
            throw new IllegalArgumentException("Devi specificare SOLO uno tra idProdotto o idPacchetto");
        }

        Acquirente acquirente = acquirenteRepository.findById(idAcquirente)
                .orElseThrow(() -> new RuntimeException("Acquirente non trovato"));

        Carrello carrello = acquirente.getCarrello();

        CarrelloItem item;

        if (idProdotto != null) {

            Prodotto prodotto = prodottoRepository.findById(idProdotto)
                    .orElseThrow(() -> new RuntimeException("Prodotto non trovato"));

            if (prodotto.getStatoProdotto() != StatoProdotto.APPROVATO) {
                throw new IllegalStateException("Questo prodotto non è disponibile per l'acquisto");
            }

            item = new CarrelloItem(prodotto, quantita);

        } else {

            Pacchetto pacchetto = pacchettoRepository.findById(idPacchetto)
                    .orElseThrow(() -> new RuntimeException("Pacchetto non trovato"));

            if (pacchetto.getStatoProdotto() != StatoProdotto.APPROVATO) {
                throw new IllegalStateException("Questo pacchetto non è disponibile per l'acquisto");
            }

            item = new CarrelloItem(pacchetto, quantita);
        }

        carrello.aggiungiItem(item);
        return carrelloRepository.save(carrello);
    }

    //  Rimuovi prodotto o pacchetto dal carrello
    public Carrello rimuoviItemDalCarrello(int idAcquirente, Integer idProdotto, Integer idPacchetto) {

        if ((idProdotto == null && idPacchetto == null) ||
                (idProdotto != null && idPacchetto != null)) {
            throw new IllegalArgumentException("Devi specificare SOLO uno tra idProdotto o idPacchetto");
        }

        Acquirente acquirente = acquirenteRepository.findById(idAcquirente)
                .orElseThrow(() -> new RuntimeException("Acquirente non trovato"));

        Carrello carrello = acquirente.getCarrello();

        if (idProdotto != null) {
            carrello.getItems().removeIf(item ->
                    item.getProdotto() != null && item.getProdotto().getIdProdotto() == idProdotto
            );
        } else {
            carrello.getItems().removeIf(item ->
                    item.getPacchetto() != null && item.getPacchetto().getIdPacchetto() == idPacchetto
            );
        }

        return carrelloRepository.save(carrello);
    }

    // Svuota il carrello
    public void svuotaCarrello(int idAcquirente) {
        Acquirente acquirente = acquirenteRepository.findById(idAcquirente)
                .orElseThrow(() -> new RuntimeException("Acquirente non trovato"));

        Carrello carrello = acquirente.getCarrello();

        carrello.svuotaCarrello();
        carrelloRepository.save(carrello);
    }

    //  Acquista tutto il carrello → genera un Ordine
    public Ordine acquistaCarrello(int idAcquirente, CartaDiCredito cartaDiCredito) {

        Acquirente acquirente = acquirenteRepository.findById(idAcquirente)
                .orElseThrow(() -> new RuntimeException("Acquirente non trovato"));

        Carrello carrello = acquirente.getCarrello();

        if (carrello.getItems().isEmpty()) {
            throw new RuntimeException("Il carrello è vuoto");
        }

        if (cartaDiCredito.getDataScadenza().isBefore(LocalDate.now())) {
            throw new RuntimeException("La carta è scaduta");
        }

        if (String.valueOf(cartaDiCredito.getNumeroCarta()).length() != 16) {
            throw new RuntimeException("numero carta non valido");
        }

        if (String.valueOf(cartaDiCredito.getCvv()).length() != 3) {
            throw new RuntimeException("numero cvv non valido");
        }

        // Crea nuove copie degli item per l'ordine
        List<CarrelloItem> copieItem = carrello.getItems().stream()
                .map(item -> item.getProdotto() != null
                        ? new CarrelloItem(item.getProdotto(), item.getQuantita())
                        : new CarrelloItem(item.getPacchetto(), item.getQuantita()))
                .toList();

        Ordine ordine = new Ordine(acquirente,copieItem);
        ordineRepository.save(ordine);

        // svuota carrello dopo l'acquisto
        carrello.svuotaCarrello();
        carrelloRepository.save(carrello);

        return ordine;
    }

    // Mostra solo eventi caricati in piattaforma
    public List<Evento> getEventiDisponibili() {
        return eventoRepository.findByCaricatoTrue();
    }

    // Prenotazione ad un evento
    public PrenotazioneEvento prenotaEvento(int idAcquirente, int idEvento, int posti) {
        Acquirente acquirente = acquirenteRepository.findById(idAcquirente)
                .orElseThrow(() -> new RuntimeException("Acquirente non trovato"));
        Evento evento = eventoRepository.findById(idEvento)
                .orElseThrow(() -> new RuntimeException("Evento non trovato"));

        if (evento.getPostiDisponibili() < posti) {
            throw new RuntimeException("Posti insufficienti per l'evento");
        }

        if (posti <= 0) {
            throw new RuntimeException("Numero di posti non valido");
        }

        PrenotazioneEvento prenotazione = new PrenotazioneEvento(acquirente, evento, posti);

        evento.setPostiDisponibili(evento.getPostiDisponibili() - posti);

        eventoRepository.save(evento);
        return prenotazioneEventoRepository.save(prenotazione);
    }

}

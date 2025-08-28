package unicam.filiera_agricola_ids_20242025.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import unicam.filiera_agricola_ids_20242025.models.Prodotti.*;
import unicam.filiera_agricola_ids_20242025.models.Utenti.Venditori.Distributore;
import unicam.filiera_agricola_ids_20242025.models.Utenti.Venditori.Produttore;
import unicam.filiera_agricola_ids_20242025.models.Utenti.Venditori.Trasformatore;
import unicam.filiera_agricola_ids_20242025.models.Utenti.Venditori.Venditore;
import unicam.filiera_agricola_ids_20242025.repository.PacchettoRepository;
import unicam.filiera_agricola_ids_20242025.repository.ProdottoRepository;
import unicam.filiera_agricola_ids_20242025.repository.VenditoreRepository;
import java.util.List;

@Service
public class HandlerVenditore {

    private final VenditoreRepository venditoreRepository;
    private final ProdottoRepository prodottoRepository;
    private final PacchettoRepository pacchettoRepository;

    @Autowired
    public HandlerVenditore(VenditoreRepository venditoreRepository,
                            ProdottoRepository prodottoRepository,
                            PacchettoRepository pacchettoRepository) {
        this.venditoreRepository = venditoreRepository;
        this.prodottoRepository = prodottoRepository;
        this.pacchettoRepository = pacchettoRepository;
    }

    // Metodo per recuperare i produttori dai propri Id
    public List<Produttore> getProduttoriById(List<Integer> idProduttori) {
        return idProduttori.stream()
                .map(id -> venditoreRepository.findById(id)
                        .filter(v -> v instanceof Produttore)
                        .map(v -> (Produttore) v)
                        .orElseThrow(() -> new IllegalArgumentException("Produttore con id " + id + " non trovato")))
                .toList();
    }

    public Prodotto creaProdottoProduttore(int idVenditore, String nome, double prezzo, String descrizione, String metodoColtivazione) {
        Venditore venditore = venditoreRepository.findById(idVenditore)
                .orElseThrow(() -> new RuntimeException("Venditore non trovato"));

        if (!(venditore instanceof Produttore produttore))
            throw new IllegalArgumentException("Non è un produttore");

        ProdottoProduttore prodottoProduttore = produttore.creaProdotto(nome, prezzo, descrizione, metodoColtivazione);
        return prodottoRepository.save(prodottoProduttore);
    }

    public Prodotto creaProdottoTrasformatore(int idVenditore, String nome, double prezzo, String descrizione, String processo, List<Produttore> produttoriAssociati) {
        Venditore venditore = venditoreRepository.findById(idVenditore)
                .orElseThrow(() -> new RuntimeException("Venditore non trovato"));

        if (!(venditore instanceof Trasformatore trasformatore))
            throw new IllegalArgumentException("Non è un trasformatore");

        ProdottoTrasformatore prodottoTrafsormatore = trasformatore.creaProdotto(nome, prezzo, descrizione, processo, produttoriAssociati);
        return prodottoRepository.save(prodottoTrafsormatore);
    }

    public Prodotto creaProdottoDistributore(int idVenditore, String nome, double prezzo, String descrizione) {
        Venditore venditore = venditoreRepository.findById(idVenditore)
                .orElseThrow(() -> new RuntimeException("Venditore non trovato"));

        if (!(venditore instanceof Distributore distributore))
            throw new IllegalArgumentException("Non è un distributore");

        Prodotto prodottoDistributore = distributore.creaProdotto(nome, prezzo, descrizione);
        return prodottoRepository.save(prodottoDistributore);
    }

    public Pacchetto creaPacchetto(int idVenditore, String nome, double prezzo, String descrizione, List<Integer> idProdotti) {
        Venditore venditore = venditoreRepository.findById(idVenditore)
                .orElseThrow(() -> new RuntimeException("Venditore non trovato"));

        if (!(venditore instanceof Distributore distributore))
            throw new IllegalArgumentException("Solo distributore può creare pacchetti");

        List<Prodotto> prodotti = prodottoRepository.findAllById(idProdotti);
        if (prodotti.size() != idProdotti.size()) {
            throw new IllegalArgumentException("Alcuni prodotti non esistono.");
        }

        Pacchetto pacchetto = distributore.creaPacchetto(nome, prezzo, descrizione, prodotti);
        return pacchettoRepository.save(pacchetto);
    }

    public void eliminaProdotto(int idVenditore, int idProdotto) {
        Venditore venditore = venditoreRepository.findById(idVenditore)
                .orElseThrow(() -> new RuntimeException("Venditore non trovato"));

        Prodotto prodotto = prodottoRepository.findById(idProdotto)
                .orElseThrow(() -> new RuntimeException("Prodotto non trovato"));

        if (!venditore.getProdotti().contains(prodotto)) {
            throw new IllegalArgumentException("Il prodotto non appartiene al venditore");

        }
        venditore.getProdotti().remove(prodotto);
        prodottoRepository.delete(prodotto);
    }

    public void eliminaPacchetto(int idVenditore, int idPacchetto) {

        Venditore venditore = venditoreRepository.findById(idVenditore)
                .orElseThrow(() -> new RuntimeException("Venditore non trovato"));

        if (!(venditore instanceof Distributore distributore))
            throw new IllegalArgumentException("Solo distributore può eliminare pacchetti");

        Pacchetto pacchetto = pacchettoRepository.findById(idPacchetto)
                .orElseThrow(() -> new RuntimeException("Pacchetto non trovato"));

        ((Distributore) venditore).getPacchetti().remove(pacchetto);
        pacchettoRepository.delete(pacchetto);
    }

    public Prodotto richiestaCaricamentoProdotto(int idProdotto, int idVenditore) {

        Venditore venditore = venditoreRepository.findById(idVenditore)
                .orElseThrow(() -> new RuntimeException("Venditore non trovato"));

        Prodotto prodotto = prodottoRepository.findById(idProdotto)
                .orElseThrow(() -> new IllegalArgumentException("Prodotto non trovato"));

        if (!venditore.getProdotti().contains(prodotto)) {
            throw new IllegalArgumentException("Il prodotto non appartiene al venditore");
        }

        if (prodotto.getStatoProdotto() != StatoProdotto.BOZZA) {
            throw new IllegalStateException("Solo i prodotti in BOZZA possono essere inviati in revisione");
        }

        prodotto.setStatoProdotto(StatoProdotto.IN_REVISIONE);
        return prodottoRepository.save(prodotto);
    }

    public Pacchetto richiestaCaricamentoPacchetto(int idPacchetto, int idVenditore) {

        Venditore venditore = venditoreRepository.findById(idVenditore)
                .orElseThrow(() -> new RuntimeException("Venditore non trovato"));

        Pacchetto pacchetto = pacchettoRepository.findById(idPacchetto)
                .orElseThrow(() -> new IllegalArgumentException("Pacchetto non trovato"));

        if (!((Distributore) venditore).getPacchetti().contains(pacchetto)) {
            throw new IllegalArgumentException("Il pacchetto non appartiene al venditore");
        }

        if (pacchetto.getStatoProdotto() != StatoProdotto.BOZZA) {
            throw new IllegalStateException("Solo i prodotti in BOZZA possono essere inviati in revisione");
        }

        pacchetto.setStatoProdotto(StatoProdotto.IN_REVISIONE);
        return pacchettoRepository.save(pacchetto);
    }
}
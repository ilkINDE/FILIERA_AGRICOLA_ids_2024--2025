package unicam.filiera_agricola_ids_20242025.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import unicam.filiera_agricola_ids_20242025.models.ContenutiSocial.ContenutoSocial;
import unicam.filiera_agricola_ids_20242025.models.Utenti.AnimatoreDellaFiliera.Evento;
import unicam.filiera_agricola_ids_20242025.models.Observer.Invitato;
import unicam.filiera_agricola_ids_20242025.models.Utenti.AnimatoreDellaFiliera.Invito;
import unicam.filiera_agricola_ids_20242025.models.Prodotti.*;
import unicam.filiera_agricola_ids_20242025.models.State.StateProdotto.StatoProdotto;
import unicam.filiera_agricola_ids_20242025.models.Utenti.AnimatoreDellaFiliera.Animatore;
import unicam.filiera_agricola_ids_20242025.models.Utenti.Venditori.Distributore;
import unicam.filiera_agricola_ids_20242025.models.Utenti.Venditori.Produttore;
import unicam.filiera_agricola_ids_20242025.models.Utenti.Venditori.Trasformatore;
import unicam.filiera_agricola_ids_20242025.models.Utenti.Venditori.Venditore;
import unicam.filiera_agricola_ids_20242025.repository.*;

import java.util.List;

@Service
public class HandlerVenditore implements Invitato {

    private final VenditoreRepository venditoreRepository;
    private final ProdottoRepository prodottoRepository;
    private final PacchettoRepository pacchettoRepository;
    private final InvitoRepository invitoRepository;
    private final ContenutoSocialRepository contenutiSocialRepository;
    private Venditore venditore;


    @Autowired
    public HandlerVenditore(VenditoreRepository venditoreRepository,
                            ProdottoRepository prodottoRepository,
                            PacchettoRepository pacchettoRepository,
                            InvitoRepository invitoRepository,
                            ContenutoSocialRepository contenutiSocialRepository
    ) {
        this.venditoreRepository = venditoreRepository;
        this.prodottoRepository = prodottoRepository;
        this.pacchettoRepository = pacchettoRepository;
        this.invitoRepository = invitoRepository;
        this.contenutiSocialRepository = contenutiSocialRepository;
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

    // Creazione prodotto di un produttore
    public Prodotto creaProdottoProduttore(int idVenditore, String nome, double prezzo, String descrizione, String metodoColtivazione) {
        Venditore venditore = venditoreRepository.findById(idVenditore)
                .orElseThrow(() -> new RuntimeException("Venditore non trovato"));

        if (!(venditore instanceof Produttore produttore))
            throw new IllegalArgumentException("Non è un produttore");

        ProdottoProduttore prodottoProduttore = produttore.creaProdotto(nome, prezzo, descrizione, metodoColtivazione);
        return prodottoRepository.save(prodottoProduttore);
    }

    // Creazione prodotto di un trasformatore
    public Prodotto creaProdottoTrasformatore(int idVenditore, String nome, double prezzo, String descrizione, String processo, List<Produttore> produttoriAssociati) {
        Venditore venditore = venditoreRepository.findById(idVenditore)
                .orElseThrow(() -> new RuntimeException("Venditore non trovato"));

        if (!(venditore instanceof Trasformatore trasformatore))
            throw new IllegalArgumentException("Non è un trasformatore");

        ProdottoTrasformatore prodottoTrafsormatore = trasformatore.creaProdotto(nome, prezzo, descrizione, processo, produttoriAssociati);
        return prodottoRepository.save(prodottoTrafsormatore);
    }

    // Creazione prodotto di un distributore di tipicità
    public Prodotto creaProdottoDistributore(int idVenditore, String nome, double prezzo, String descrizione) {
        Venditore venditore = venditoreRepository.findById(idVenditore)
                .orElseThrow(() -> new RuntimeException("Venditore non trovato"));

        if (!(venditore instanceof Distributore distributore))
            throw new IllegalArgumentException("Non è un distributore");

        Prodotto prodottoDistributore = distributore.creaProdotto(nome, prezzo, descrizione);
        return prodottoRepository.save(prodottoDistributore);
    }

    // creazione di un pacchetto
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

    // eliminazione di un prodotto creato
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

    // eliminazione di un pacchetto creato
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

    // inoltra al curatore la richiesta di caricamento sulla piattaforma di un prodotto creato
    public Prodotto richiestaCaricamentoProdotto(int idProdotto, int idVenditore) {

        Venditore venditore = venditoreRepository.findById(idVenditore)
                .orElseThrow(() -> new RuntimeException("Venditore non trovato"));

        Prodotto prodotto = prodottoRepository.findById(idProdotto)
                .orElseThrow(() -> new IllegalArgumentException("Prodotto non trovato"));

        if (!venditore.getProdotti().contains(prodotto)) {
            throw new IllegalArgumentException("Il prodotto non appartiene al venditore");
        }

        if (prodotto.getStatoProdotto()!=StatoProdotto.BOZZA){
            throw new IllegalArgumentException("Il prodotto non è può essere caricato sulla piattaforma");
        }
        prodotto.inviaInRevisione();
        return prodottoRepository.save(prodotto);
    }

    // inoltra al curatore la richiesta di caricamento sulla piattaforma di un pacchetto creato
    public Pacchetto richiestaCaricamentoPacchetto(int idPacchetto, int idVenditore) {

        Venditore venditore = venditoreRepository.findById(idVenditore)
                .orElseThrow(() -> new RuntimeException("Venditore non trovato"));

        Pacchetto pacchetto = pacchettoRepository.findById(idPacchetto)
                .orElseThrow(() -> new IllegalArgumentException("Pacchetto non trovato"));

        if (!((Distributore) venditore).getPacchetti().contains(pacchetto)) {
            throw new IllegalArgumentException("Il pacchetto non appartiene al venditore");
        }

        if (pacchetto.getStatoProdotto() != StatoProdotto.BOZZA) {
            throw new IllegalStateException("Solo i pacchetti in BOZZA possono essere inviati in revisione");
        }


        pacchetto.inviaInRevisione();
        return pacchettoRepository.save(pacchetto);
    }

    public Venditore getVenditore() {
        return venditore;
    }
    public void setVenditore(Venditore venditore) {
        this.venditore = venditore;
    }

    /* Observer pattern
     Questo metodo viene chiamato dall'Animatore quando notifica un nuovo evento.
     Crea un oggetto Invito associando il venditore , l'animatore e l'evento,
     e lo aggiunge alla lista degli inviti ricevuti dal venditore.*/

    @Override
    public void riceviInvito(Evento evento, Animatore animatore) {
        Invito invito = new Invito(venditore, animatore, evento);
        invitoRepository.save(invito);
        venditore.getInvitiRicevuti().add(invito);
    }

    // Gestione inviti
    public void accettaInvito(int idVenditore, int idInvito) {

        Venditore venditore = venditoreRepository.findById(idVenditore)
                .orElseThrow(() -> new RuntimeException("Venditore non trovato"));

        Invito invito = invitoRepository.findById(idInvito)
                .orElseThrow(() -> new RuntimeException("Invito non trovato"));

        if (invito.getVenditore().getIdVenditore() != idVenditore)
            throw new RuntimeException("Invito non appartiene al venditore");

        //invito.setStato("ACCETTATO");
        invito.accetta();
        invitoRepository.save(invito);

        // Rimuove l'invito dalla lista del venditore
        venditore.getInvitiRicevuti().removeIf(i -> i.getIdInvito() == idInvito);

    }

    public void rifiutaInvito(int idVenditore, int idInvito) {

        Venditore venditore = venditoreRepository.findById(idVenditore)
                .orElseThrow(() -> new RuntimeException("Venditore non trovato"));

        Invito invito = invitoRepository.findById(idInvito)
                .orElseThrow(() -> new RuntimeException("Invito non trovato"));

        if (invito.getVenditore().getIdVenditore() != idVenditore)
            throw new RuntimeException("Invito non appartiene al venditore");

        invito.rifiuta();
        invitoRepository.save(invito);

        venditore.getInvitiRicevuti().removeIf(i -> i.getIdInvito() == idInvito);

    }

    // getter per inviti ricevuti
    public List<Invito> getInvitiRicevuti(int idVenditore) {
        return invitoRepository.findByVenditoreIdVenditore(idVenditore);
    }

    // Pubblicazione contenuto social
    public ContenutoSocial pubblicaContenuto(int idVenditore, int idProdotto, String descrizioneContenuto) {

        Venditore venditore = venditoreRepository.findById(idVenditore)
                .orElseThrow(() -> new RuntimeException("Venditore non trovato"));

        Prodotto prodotto = prodottoRepository.findById(idProdotto)
                .orElseThrow(() -> new RuntimeException("Prodotto non trovato"));

        if (prodotto.getVenditore().getIdVenditore() != venditore.getIdVenditore()){
            throw new IllegalStateException("il prodotto non appartiene al venditore");
        }

        if (prodotto.getStatoProdotto() != StatoProdotto.APPROVATO) {
            throw new IllegalStateException("Il prodotto non è caricato nella piattaforma");
        }

        ContenutoSocial contenutoDaCondividere = new ContenutoSocial(prodotto, descrizioneContenuto);

        return contenutiSocialRepository.save(contenutoDaCondividere);
    }

}
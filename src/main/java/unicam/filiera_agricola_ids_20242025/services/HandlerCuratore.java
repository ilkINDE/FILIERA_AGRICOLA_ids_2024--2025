package unicam.filiera_agricola_ids_20242025.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import unicam.filiera_agricola_ids_20242025.models.Amministratori.Curatore;
import unicam.filiera_agricola_ids_20242025.models.Prodotti.Pacchetto;
import unicam.filiera_agricola_ids_20242025.models.Prodotti.Prodotto;
import unicam.filiera_agricola_ids_20242025.models.State.StateProdotto.StatoProdotto;
import unicam.filiera_agricola_ids_20242025.repository.CuratoreRepository;
import unicam.filiera_agricola_ids_20242025.repository.PacchettoRepository;
import unicam.filiera_agricola_ids_20242025.repository.ProdottoRepository;

import java.util.List;

@Service
public class HandlerCuratore {

    private final CuratoreRepository curatoreRepository;
    private final ProdottoRepository prodottoRepository;
    private final PacchettoRepository pacchettoRepository;

    @Autowired
    public HandlerCuratore(CuratoreRepository curatoreRepository, ProdottoRepository prodottoRepository, PacchettoRepository pacchettoRepository){
        this.curatoreRepository = curatoreRepository;
        this.prodottoRepository = prodottoRepository;
        this.pacchettoRepository = pacchettoRepository;
    }

    // lista di prodotti in attesa di approvazione
    public List<Prodotto> getProdottiInAttesa(int idCuratore) {

        Curatore curatore = curatoreRepository.findById(idCuratore)
                .orElseThrow(() -> new RuntimeException("Curatore non trovato"));

        return prodottoRepository.findByStatoProdotto(StatoProdotto.IN_REVISIONE);
    }

    // lista di pacchetti in attesa di approvazione
    public List<Pacchetto> getPacchettiInAttesa(int idCuratore) {

        Curatore curatore = curatoreRepository.findById(idCuratore)
                .orElseThrow(() -> new RuntimeException("Curatore non trovato"));

        return pacchettoRepository.findByStatoProdotto(StatoProdotto.IN_REVISIONE);
    }

    // approva e carica un prodotto sulla piattaforma
    public Prodotto approvaProdotto(int idCuratore, int idProdotto) {

        Curatore curatore = curatoreRepository.findById(idCuratore)
                .orElseThrow(() -> new RuntimeException("Curatore non trovato"));

        Prodotto prodotto = prodottoRepository.findById(idProdotto)
                .orElseThrow(() -> new RuntimeException("Prodotto non trovato"));

        prodotto.approva();
        return prodottoRepository.save(prodotto);
    }

    // approva e carica un pacchetto sulla piattaforma
    public Pacchetto approvaPacchetto(int idCuratore, int idPacchetto) {

        Curatore curatore = curatoreRepository.findById(idCuratore)
                .orElseThrow(() -> new RuntimeException("Curatore non trovato"));

        Pacchetto pacchetto = pacchettoRepository.findById(idPacchetto)
                .orElseThrow(() -> new RuntimeException("Pacchetto non trovato"));

        if (pacchetto.getStatoProdotto() != StatoProdotto.IN_REVISIONE) {
            throw new IllegalStateException("Il pacchetto non è in revisione");
        }

        pacchetto.approva();
        return pacchettoRepository.save(pacchetto);
    }

    // rifiuta un prodotto
    public Prodotto rifiutaProdotto(int idCuratore, int idProdotto) {

        Curatore curatore = curatoreRepository.findById(idCuratore)
                .orElseThrow(() -> new RuntimeException("Curatore non trovato"));

        Prodotto prodotto = prodottoRepository.findById(idProdotto)
                .orElseThrow(() -> new RuntimeException("Prodotto non trovato"));

        prodotto.rifiuta();
        return prodottoRepository.save(prodotto);
    }

    // rifiuta un pacchetto
    public Pacchetto rifiutaPacchetto(int idCuratore, int idPacchetto) {

        Curatore curatore = curatoreRepository.findById(idCuratore)
                .orElseThrow(() -> new RuntimeException("Curatore non trovato"));

        Pacchetto pacchetto = pacchettoRepository.findById(idPacchetto)
                .orElseThrow(() -> new RuntimeException("Pacchetto non trovato"));

        if (pacchetto.getStatoProdotto() != StatoProdotto.IN_REVISIONE) {
            throw new IllegalStateException("Il pacchetto non è in revisione");
        }

        pacchetto.rifiuta();
        return pacchettoRepository.save(pacchetto);
    }

}

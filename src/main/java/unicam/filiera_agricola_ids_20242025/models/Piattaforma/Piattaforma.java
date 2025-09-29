package unicam.filiera_agricola_ids_20242025.models.Piattaforma;

import unicam.filiera_agricola_ids_20242025.models.ContenutiSocial.ContenutoSocial;
import unicam.filiera_agricola_ids_20242025.models.Prodotti.Pacchetto;
import unicam.filiera_agricola_ids_20242025.models.Utenti.AnimatoreDellaFiliera.Evento;
import unicam.filiera_agricola_ids_20242025.models.Prodotti.Prodotto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import unicam.filiera_agricola_ids_20242025.models.State.StateProdotto.StatoProdotto;
import unicam.filiera_agricola_ids_20242025.repository.ContenutoSocialRepository;
import unicam.filiera_agricola_ids_20242025.repository.EventoRepository;
import unicam.filiera_agricola_ids_20242025.repository.PacchettoRepository;
import unicam.filiera_agricola_ids_20242025.repository.ProdottoRepository;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class Piattaforma {

    private final ProdottoRepository prodottoRepository;
    private final PacchettoRepository  pacchettoRepository;
    private final EventoRepository eventoRepository;
    private final ContenutoSocialRepository contenutoSocialRepository;

    @Autowired
    public Piattaforma(ProdottoRepository prodottoRepository,
                       PacchettoRepository pacchettoRepository,
                       EventoRepository eventoRepository,
                       ContenutoSocialRepository contenutoSocialRepository) {
        this.prodottoRepository = prodottoRepository;
        this.pacchettoRepository = pacchettoRepository;
        this.eventoRepository = eventoRepository;
        this.contenutoSocialRepository = contenutoSocialRepository;
    }

    // Prodotti caricati
    public List<Prodotto> getProdottiCaricati() {
        return prodottoRepository.findAll().stream()
                .filter(p -> p.getStatoProdotto() == StatoProdotto.APPROVATO)
                .collect(Collectors.toList());
    }

    // Informazioni su un singolo prodotto
    public Prodotto getInfoProdotto(int idProdotto) {
        return prodottoRepository.findById(idProdotto)
                .filter(p -> p.getStatoProdotto() == StatoProdotto.APPROVATO)
                .orElse(null);
    }

    // Pacchetti caricati
    public List<Pacchetto> getPacchettiCaricati() {
        return pacchettoRepository.findAll().stream()
                .filter(p -> p.getStatoProdotto() == StatoProdotto.APPROVATO)
                .collect(Collectors.toList());
    }

    // Informazioni su un singolo prodotto
    public Pacchetto getInfoPacchetto(int idPacchetto) {
        return pacchettoRepository.findById(idPacchetto)
                .filter(p -> p.getStatoProdotto() == StatoProdotto.APPROVATO)
                .orElse(null);
    }

    // Eventi attualmente attivi
    public List<Evento> getEventiAttivi() {
        return eventoRepository.findAll().stream()
                .filter(Evento::isCaricato)
                .collect(Collectors.toList());
    }

    // informazioni su un singolo evento
    public Evento  getInfoEvento(int idEvento) {
        return eventoRepository.findById(idEvento)
                .filter(Evento::isCaricato)
                .orElse(null);
    }

    // Contenuti social pubblicati
    public List<ContenutoSocial> getContenutiPubblicati() {
        return contenutoSocialRepository.findAll().stream()
                // ad esempio consideri "pubblicato" se è collegato ad un prodotto approvato
                .filter(c -> c.getProdottoDaPubblicare() != null
                        && c.getProdottoDaPubblicare().getStatoProdotto() == StatoProdotto.APPROVATO)
                .collect(Collectors.toList());
    }

    // Accesso a mappa OSM (restituisce la lista di indirizzi degli eventi attivi)
    public List<String> getMappaOSM(){
        return eventoRepository.findByCaricatoTrue()
                .stream()
                .map(Evento::getIndirizzo)
                .filter(indirizzo -> indirizzo != null && !indirizzo.isBlank())
                .toList();
    }
}

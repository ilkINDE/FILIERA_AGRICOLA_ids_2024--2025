package unicam.filiera_agricola_ids_20242025.services;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import unicam.filiera_agricola_ids_20242025.models.Evento;
import unicam.filiera_agricola_ids_20242025.models.Prodotti.Pacchetto;
import unicam.filiera_agricola_ids_20242025.models.Prodotti.Prodotto;
import unicam.filiera_agricola_ids_20242025.models.State.StateProdotto.StatoProdotto;
import unicam.filiera_agricola_ids_20242025.models.Ruolo;
import org.springframework.stereotype.Service;
import unicam.filiera_agricola_ids_20242025.models.Utenti.Acquirente;
import unicam.filiera_agricola_ids_20242025.models.Utenti.Utente;
import unicam.filiera_agricola_ids_20242025.repository.*;

@Service
public class HandlerUtente {

    private final UtenteRepository utenteRepository;
    private final AcquirenteRepository acquirenteRepository;
    private final ProdottoRepository prodottoRepository;
    private final PacchettoRepository pacchettoRepository;
    private final EventoRepository eventoRepository;

    public HandlerUtente(UtenteRepository utenteRepository,
                         AcquirenteRepository acquirenteRepository,
                         ProdottoRepository prodottoRepository,
                         PacchettoRepository pacchettoRepository,
                         EventoRepository eventoRepository) {
        this.utenteRepository = utenteRepository;
        this.acquirenteRepository = acquirenteRepository;
        this.prodottoRepository = prodottoRepository;
        this.pacchettoRepository = pacchettoRepository;
        this.eventoRepository = eventoRepository;
    }

    public List<Ruolo> getRuoliDisponibili() {
        return Arrays.asList(Ruolo.values());
    }

    public Utente registrazione(String nome, String cognome, Date dataNascita, String comuneDiProvenienza, String email, String password) {
        // Controllo se email già presente
        if (utenteRepository.findByEmail(email).isPresent()) {
            throw new IllegalArgumentException("Email già registrata: " + email);
        }
        Utente utente = new Utente(nome, cognome, dataNascita, comuneDiProvenienza, email, password);
        utente = utenteRepository.save(utente);

        Acquirente acquirente = new Acquirente(utente);
        acquirenteRepository.save(acquirente);

        return utente;
    }

    //  Lista prodotti presenti in piattaforma
    public List<Prodotto> getProdottiDisponibili() {
        return prodottoRepository.findByStatoProdotto(StatoProdotto.APPROVATO);
    }

    //  Dettagli singolo prodotto
    public Prodotto esploraProdotto(int idProdotto) {
        return prodottoRepository.findById(idProdotto)
                .filter(p -> p.getStatoProdotto() == StatoProdotto.APPROVATO)
                .orElse(null);
    }

    //  Lista pacchetti presenti in piattaforma
    public List<Pacchetto> getPacchettiDisponibili() {
        return pacchettoRepository.findByStatoProdotto(StatoProdotto.APPROVATO);
    }

    //  Dettagli singolo pacchetto
    public Pacchetto esploraPacchetto(int idPacchetto) {
        return pacchettoRepository.findById(idPacchetto)
                .filter(p -> p.getStatoProdotto() == StatoProdotto.APPROVATO)
                .orElse(null);
    }

    //  Lista eventi caricati in piattaforma
    public List<Evento> getEventiDisponibili() {
        return eventoRepository.findByCaricatoTrue();
    }

    //  Dettagli singolo evento
    public Evento esploraEvento(int idEvento) {
        return eventoRepository.findById(idEvento)
                .filter(Evento::isCaricato)
                .orElse(null);
    }

    // Restituisce gli indirizzi degli eventi caricati
    public List<String> esploraMappa() {
        return eventoRepository.findByCaricatoTrue()
                .stream()
                .map(Evento::getIndirizzo)
                .filter(indirizzo -> indirizzo != null && !indirizzo.isBlank())
                .toList();
    }

}

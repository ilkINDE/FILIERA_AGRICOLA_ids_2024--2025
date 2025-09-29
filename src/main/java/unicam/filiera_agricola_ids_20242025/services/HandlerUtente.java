package unicam.filiera_agricola_ids_20242025.services;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

import unicam.filiera_agricola_ids_20242025.models.Piattaforma.Piattaforma;
import unicam.filiera_agricola_ids_20242025.models.Utenti.AnimatoreDellaFiliera.Evento;
import unicam.filiera_agricola_ids_20242025.models.Prodotti.Pacchetto;
import unicam.filiera_agricola_ids_20242025.models.Prodotti.Prodotto;
import unicam.filiera_agricola_ids_20242025.models.State.StateProdotto.StatoProdotto;
import unicam.filiera_agricola_ids_20242025.models.Utenti.Utente.Ruolo;
import org.springframework.stereotype.Service;
import unicam.filiera_agricola_ids_20242025.models.Utenti.Acquirente.Acquirente;
import unicam.filiera_agricola_ids_20242025.models.Utenti.Utente.Utente;
import unicam.filiera_agricola_ids_20242025.repository.*;

@Service
public class HandlerUtente {

    private final Piattaforma piattaforma;
    private final UtenteRepository utenteRepository;
    private final AcquirenteRepository acquirenteRepository;
    private final ProdottoRepository prodottoRepository;
    private final PacchettoRepository pacchettoRepository;
    private final EventoRepository eventoRepository;

    public HandlerUtente(Piattaforma piattaforma,
                         UtenteRepository utenteRepository,
                         AcquirenteRepository acquirenteRepository,
                         ProdottoRepository prodottoRepository,
                         PacchettoRepository pacchettoRepository,
                         EventoRepository eventoRepository) {
        this.piattaforma = piattaforma;
        this.utenteRepository = utenteRepository;
        this.acquirenteRepository = acquirenteRepository;
        this.prodottoRepository = prodottoRepository;
        this.pacchettoRepository = pacchettoRepository;
        this.eventoRepository = eventoRepository;
    }

    public Set<Ruolo> getRuoliDisponibili(int idUtente) {

        Utente utente = utenteRepository.findById(idUtente)
                .orElseThrow(() -> new RuntimeException("Utente non trovato"));

        return utente.getRuoli();
    }

    public Utente registrazione(String nome, String cognome, LocalDate dataNascita, String comuneDiProvenienza, String email, String password) {
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
    public List<Prodotto> getProdottiDisponibili() {return piattaforma.getProdottiCaricati();}

    //  Dettagli singolo prodotto
    public Prodotto esploraProdotto(int idProdotto) {return piattaforma.getInfoProdotto(idProdotto);}

    //  Lista pacchetti presenti in piattaforma
    public List<Pacchetto> getPacchettiDisponibili() {
        return piattaforma.getPacchettiCaricati();
    }

    //  Dettagli singolo pacchetto
    public Pacchetto esploraPacchetto(int idPacchetto) {
        return piattaforma.getInfoPacchetto(idPacchetto);
    }

    //  Lista eventi caricati in piattaforma
    public List<Evento> getEventiDisponibili() {
        return piattaforma.getEventiAttivi();
    }

    //  Dettagli singolo evento
    public Evento esploraEvento(int idEvento) {
        return piattaforma.getInfoEvento(idEvento);
    }

    // apre mappa OSM
    public List<String> esploraMappaOSM() {
        return piattaforma.getMappaOSM();
    }

}

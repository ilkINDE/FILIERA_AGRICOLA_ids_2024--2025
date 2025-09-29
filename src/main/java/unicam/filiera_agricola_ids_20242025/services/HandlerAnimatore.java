package unicam.filiera_agricola_ids_20242025.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import unicam.filiera_agricola_ids_20242025.models.Observer.Invitato;
import unicam.filiera_agricola_ids_20242025.models.Observer.OrganizzatoreInviti;
import unicam.filiera_agricola_ids_20242025.models.Utenti.AnimatoreDellaFiliera.Animatore;
import unicam.filiera_agricola_ids_20242025.models.Utenti.AnimatoreDellaFiliera.Evento;
import unicam.filiera_agricola_ids_20242025.models.Utenti.Venditori.Venditore;
import unicam.filiera_agricola_ids_20242025.repository.AnimatoreRepository;
import unicam.filiera_agricola_ids_20242025.repository.EventoRepository;
import unicam.filiera_agricola_ids_20242025.repository.VenditoreRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class HandlerAnimatore implements OrganizzatoreInviti {

    private final AnimatoreRepository animatoreRepository;

    private final EventoRepository eventoRepository;

    private final VenditoreRepository venditoreRepository;

    private final HandlerVenditore handlerVenditore;

    // Lista invitati (observer)
    private final List<Invitato> invitati = new ArrayList<>();

    @Autowired
    public HandlerAnimatore(AnimatoreRepository animatoreRepository, EventoRepository eventoRepository, VenditoreRepository venditoreRepository, HandlerVenditore handlerVenditore) {
        this.animatoreRepository = animatoreRepository;
        this.eventoRepository = eventoRepository;
        this.venditoreRepository = venditoreRepository;
        this.handlerVenditore = handlerVenditore;
    }

    // creau un evento, gli eventi creati dall'animatore sono modificabli o eliminabili finchè non vengono ufficialmente caricati nella piattaforma
    public Evento creaEvento(int idAnimatore, String nome, String descrizione, String indirizzo, LocalDate data, int maxPartecipanti) {

        Animatore animatore = animatoreRepository.findById(idAnimatore)
                .orElseThrow(() -> new RuntimeException("Animatore non trovato"));

        if (eventoRepository.existsByIndirizzoIgnoreCaseAndData(indirizzo, data)) {
            throw new RuntimeException("Il luogo non è disponibile alla data selezionata.");
        }


        Evento nuovoEvento = new Evento();
        nuovoEvento.setNome(nome);
        nuovoEvento.setMaxPartecipanti(maxPartecipanti);
        nuovoEvento.setPostiDisponibili(maxPartecipanti);
        nuovoEvento.setData(data);
        nuovoEvento.setDescrizione(descrizione);
        nuovoEvento.setIndirizzo(indirizzo);


        animatore.getEventiCreati().add(nuovoEvento);
        eventoRepository.save(nuovoEvento);
        return nuovoEvento;
    }

    // modifica i dati di un evento creato
    public Evento modificaEvento(int idAnimatore, int idEvento, String nuovoNome, String nuovaDescrizione, String nuovoIndirizzo, LocalDate nuovaData, int nuovoMaxPartecipanti) {

        Animatore animatore = animatoreRepository.findById(idAnimatore)
                .orElseThrow(() -> new RuntimeException("Animatore non trovato"));

        Evento evento = eventoRepository.findById(idEvento)
                .orElseThrow(() -> new RuntimeException("Evento non trovato"));

        if (!animatore.getEventiCreati().contains(evento)) {
            throw new RuntimeException("L'evento non appartiene a questo animatore.");
        }

        if (evento.isCaricato()) {
            throw new RuntimeException("Evento già caricato sulla piattaforma: non modificabile.");
        }

        List<Evento> conflitti = eventoRepository.findConflitti(nuovoIndirizzo, nuovaData, evento.getId());
        if (!conflitti.isEmpty()) {
            throw new RuntimeException("Il luogo non è disponibile alla data selezionata.");
        }

        evento.setNome(nuovoNome);
        evento.setDescrizione(nuovaDescrizione);
        evento.setIndirizzo(nuovoIndirizzo);
        evento.setData(nuovaData);
        evento.setMaxPartecipanti(nuovoMaxPartecipanti);
        evento.setPostiDisponibili(nuovoMaxPartecipanti); // aggiorna anche i posti disponibili

        eventoRepository.save(evento);

        return evento;
    }

    // elimina un evento creato in precedenza
    public void eliminaEvento(int idEvento, int idAnimatore) {

        Animatore animatore = animatoreRepository.findById(idAnimatore).
                orElseThrow(() -> new RuntimeException("Animatore inesistente"));

        Evento evento = eventoRepository.findById(idEvento).
                orElseThrow(() -> new RuntimeException("Evento inesistente"));

        if (!animatore.getEventiCreati().contains(evento)) {
            throw new RuntimeException("L'evento non appartiene a questo animatore.");
        }
        if (evento.isCaricato()) {
            throw new RuntimeException("Evento già caricato sulla piattaforma, non eliminabile.");
        }

        animatore.getEventiCreati().remove(evento);
        eventoRepository.delete(evento);
    }

    // carica un evento sulla piattaforma, una volta caricato l'evento non potrà più essere modificato o eliminato
    public void caricaEvento(int idEvento, int idAnimatore) {

        Animatore animatore = animatoreRepository.findById(idAnimatore).
                orElseThrow(() -> new RuntimeException("Animatore inesistente"));

        Evento evento = eventoRepository.findById(idEvento).
                orElseThrow(() -> new RuntimeException("Evento inesistente"));

        if (!animatore.getEventiCreati().contains(evento)) {
            throw new RuntimeException("L'evento non appartiene a questo animatore.");
        }

        if (evento.isCaricato()) {
            throw new RuntimeException("Evento già caricato sulla piattaforma");
        }

        evento.setCaricato(true);
        eventoRepository.save(evento);
    }

    /* Observer pattern
     Registra un nuovo osservatore (venditore) nella lista degli invitati.
     In questo modo l'animatore sa chi deve notificare quando
     viene organizzato un evento.*/

    @Override
    public void aggiungiInvitato(Invitato i) {
        invitati.add(i);
    }

    // Questo metodo scorre tutti gli osservatori (venditori registrati) e invia loro l'invito
    // chiamando il metodo riceviInvito() su ciascun oggetto Invitato.

    @Override
    public void notificaInvitati(Evento evento, Animatore animatore) {
        for (Invitato i : invitati) {
            i.riceviInvito(evento, animatore);
        }
    }

    // inoltra gli inviti
    public void inviaInviti(int idAnimatore, int idEvento, List<Integer> venditoriIds) {
        Animatore animatore = animatoreRepository.findById(idAnimatore)
                .orElseThrow(() -> new RuntimeException("Animatore non trovato"));

        Evento evento = eventoRepository.findById(idEvento)
                .orElseThrow(() -> new RuntimeException("Evento non trovato"));

        if  (!animatore.getEventiCreati().contains(evento)) {
            throw new RuntimeException("L'evento non appartiene a questo animatore");
        }

        if (evento.isCaricato()) {
            throw new RuntimeException("Non è possibile invitare venditori a un evento già caricato sulla piattaforma.");
        }

        for (Integer venditoreId : venditoriIds) {
            Venditore venditore = venditoreRepository.findById(venditoreId)
                    .orElseThrow(() -> new RuntimeException("Venditore non trovato"));

            handlerVenditore.setVenditore(venditore);
            aggiungiInvitato(handlerVenditore);
        }

        notificaInvitati(evento, animatore);
    }
}
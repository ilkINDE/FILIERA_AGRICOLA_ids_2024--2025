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

    //gli eventi creati dall'animatore sono modificabli o eliminabili finchè non vengono ufficialmente caricati nella piattaforma
    public Evento creaEvento(int idAnimatore, String nome, String descrizione, String indirizzo, LocalDate data, int maxPartecipanti) {

        Animatore animatore = animatoreRepository.findById(idAnimatore)
                .orElseThrow(() -> new RuntimeException("Animatore non trovato"));


        //  Controlla che non ci sia già un evento in quel luogo e data
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

    public Evento modificaEvento(int idAnimatore, int idEvento, String nuovoNome, String nuovaDescrizione, String nuovoIndirizzo, LocalDate nuovaData, int nuovoMaxPartecipanti) {

        // Recupera l'animatore
        Animatore animatore = animatoreRepository.findById(idAnimatore)
                .orElseThrow(() -> new RuntimeException("Animatore non trovato"));

        // Recupera l'evento da modificare
        Evento evento = eventoRepository.findById(idEvento)
                .orElseThrow(() -> new RuntimeException("Evento non trovato"));

        // Controlla che l'evento appartenga all'animatore
        if (!animatore.getEventiCreati().contains(evento)) {
            throw new RuntimeException("L'evento non appartiene a questo animatore.");
        }

        // Verifica se l'evento è già stato caricato sulla piattaforma
        if (evento.isCaricato()) {
            throw new RuntimeException("Evento già caricato sulla piattaforma: non modificabile.");
        }

        // Verifica disponibilità del luogo e data (escludendo l'evento stesso)
        List<Evento> conflitti = eventoRepository.findConflitti(nuovoIndirizzo, nuovaData, evento.getId());
        if (!conflitti.isEmpty()) {
            throw new RuntimeException("Il luogo non è disponibile alla data selezionata.");
        }

        // Modifica i campi dell'evento
        evento.setNome(nuovoNome);
        evento.setDescrizione(nuovaDescrizione);
        evento.setIndirizzo(nuovoIndirizzo);
        evento.setData(nuovaData);
        evento.setMaxPartecipanti(nuovoMaxPartecipanti);
        evento.setPostiDisponibili(nuovoMaxPartecipanti); // aggiorna anche i posti disponibili

        // Salva le modifiche
        eventoRepository.save(evento);

        return evento;
    }

    public void eliminaEvento(int idEvento, int idAnimatore) {

        // Recupera l'animatore
        Animatore animatore = animatoreRepository.findById(idAnimatore).
                orElseThrow(() -> new RuntimeException("Animatore inesistente"));

        // Recupera l'evento da eliminare
        Evento evento = eventoRepository.findById(idEvento).
                orElseThrow(() -> new RuntimeException("Evento inesistente"));

        // Verifica che l'evento appartenga all'animatore
        if (!animatore.getEventiCreati().contains(evento)) {
            throw new RuntimeException("L'evento non appartiene a questo animatore.");
        }
        if (evento.isCaricato()) {
            throw new RuntimeException("Evento già caricato sulla piattaforma, non eliminabile.");
        }

        animatore.getEventiCreati().remove(evento);
        eventoRepository.delete(evento);
    }

    //una volta caricato l'evento non potrà più essere modificato o eliminato
    public void caricaEvento(int idEvento, int idAnimatore) {

        // Recupera l'animatore
        Animatore animatore = animatoreRepository.findById(idAnimatore).
                orElseThrow(() -> new RuntimeException("Animatore inesistente"));

        // Recupera l'evento
        Evento evento = eventoRepository.findById(idEvento).
                orElseThrow(() -> new RuntimeException("Evento inesistente"));

        // Verifica che l'evento appartenga all'animatore
        if (!animatore.getEventiCreati().contains(evento)) {
            throw new RuntimeException("L'evento non appartiene a questo animatore.");
        }
        // Verifica se l'evento è già stato caricato sulla piattaforma
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

    public void inviaInviti(int idAnimatore, int idEvento, List<Integer> venditoriIds) {
        Animatore animatore = animatoreRepository.findById(idAnimatore)
                .orElseThrow(() -> new RuntimeException("Animatore non trovato"));

        Evento evento = eventoRepository.findById(idEvento)
                .orElseThrow(() -> new RuntimeException("Evento non trovato"));


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
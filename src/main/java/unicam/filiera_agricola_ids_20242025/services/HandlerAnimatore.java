package unicam.filiera_agricola_ids_20242025.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import unicam.filiera_agricola_ids_20242025.models.Utenti.Animatore;
import unicam.filiera_agricola_ids_20242025.models.Evento;
import unicam.filiera_agricola_ids_20242025.repository.AnimatoreRepository;
import unicam.filiera_agricola_ids_20242025.repository.EventoRepository;
import java.util.Date;
import java.util.List;

@Service
public class HandlerAnimatore {

    private final AnimatoreRepository animatoreRepository;

    private final EventoRepository eventoRepository;


    @Autowired
    public HandlerAnimatore(AnimatoreRepository animatoreRepository, EventoRepository eventoRepository) {
        this.animatoreRepository = animatoreRepository;
        this.eventoRepository = eventoRepository;
    }

    //gli eventi creati dall'animatore sono modificabli o eliminabili finchè non vengono ufficialmente caricati nella piattaforma
    public void creaEvento(int idAnimatore, String nome, String descrizione, String indirizzo, Date data, int maxPartecipanti) {

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
    }

    public void modificaEvento(int idAnimatore, int idEvento, String nuovoNome, String nuovaDescrizione, String nuovoIndirizzo, Date nuovaData, int nuovoMaxPartecipanti) {

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
}
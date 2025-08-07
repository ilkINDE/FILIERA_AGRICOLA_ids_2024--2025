package unicam.filiera_agricola_ids_20242025.Services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import unicam.filiera_agricola_ids_20242025.models.Animatore;
import unicam.filiera_agricola_ids_20242025.models.Evento;
import unicam.filiera_agricola_ids_20242025.repository.AnimatoreRepository;
import unicam.filiera_agricola_ids_20242025.repository.EventoRepository;

import java.util.Date;

@Service
public class HandlerAnimatore {

    private final AnimatoreRepository animatoreRepository;

    private final EventoRepository eventoRepository;


    @Autowired
    public HandlerAnimatore(AnimatoreRepository animatoreRepository, EventoRepository eventoRepository) {
        this.animatoreRepository = animatoreRepository;
        this.eventoRepository = eventoRepository;
    }

    public void creaEvento(int idAnimatore, String nome, String descrizione, String indirizzo, Date data, int maxPartecipanti) {

        Animatore animatore = animatoreRepository.findById(idAnimatore)
                .orElseThrow(() -> new RuntimeException("Animatore non trovato"));


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

    public void caricaEvento() {
    }

    public void eliminaEvento(int idEvento, int idAnimatore) {

        Animatore animatore = animatoreRepository.findById(idAnimatore).
                orElseThrow(() -> new RuntimeException("Animatore inesistente"));

        Evento evento = eventoRepository.findById(idEvento).
                orElseThrow(() -> new RuntimeException("Evento inesistente"));

        if (animatore.getEventiCreati().contains(evento)) {
            animatore.getEventiCreati().remove(evento);
            eventoRepository.delete(evento);
        }
    }
}
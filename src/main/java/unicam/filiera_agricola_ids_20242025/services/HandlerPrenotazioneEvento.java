package unicam.filiera_agricola_ids_20242025.services;

import org.springframework.stereotype.Service;
import unicam.filiera_agricola_ids_20242025.models.Evento;
import unicam.filiera_agricola_ids_20242025.models.PrenotazioneEvento;
import unicam.filiera_agricola_ids_20242025.models.Utenti.Acquirente;
import unicam.filiera_agricola_ids_20242025.repository.AcquirenteRepository;
import unicam.filiera_agricola_ids_20242025.repository.EventoRepository;
import unicam.filiera_agricola_ids_20242025.repository.PrenotazioneEventoRepository;

import java.util.List;

@Service
public class HandlerPrenotazioneEvento {

    private final PrenotazioneEventoRepository prenotazioneEventoRepository;
    private final AcquirenteRepository acquirenteRepository;
    private final EventoRepository eventoRepository;

    public HandlerPrenotazioneEvento(PrenotazioneEventoRepository prenotazioneEventoRepository,
                                     AcquirenteRepository acquirenteRepository,
                                     EventoRepository eventoRepository) {
        this.prenotazioneEventoRepository = prenotazioneEventoRepository;
        this.acquirenteRepository = acquirenteRepository;
        this.eventoRepository = eventoRepository;
    }

    // mostra solo eventi caricati in piattaforma
    public List<Evento> getEventiDisponibili() {
        return eventoRepository.findByCaricatoTrue();
    }

    public PrenotazioneEvento prenotaEvento(int idAcquirente, int idEvento, int posti) {
        Acquirente acquirente = acquirenteRepository.findById(idAcquirente)
                .orElseThrow(() -> new RuntimeException("Acquirente non trovato"));
        Evento evento = eventoRepository.findById(idEvento)
                .orElseThrow(() -> new RuntimeException("Evento non trovato"));

        //  Controllo disponibilità
        if (evento.getPostiDisponibili() < posti) {
            throw new RuntimeException("Posti insufficienti per l'evento");
        }


        if (posti <= 0) {
            throw new RuntimeException("Numero di posti non valido");
        }

        PrenotazioneEvento prenotazione = new PrenotazioneEvento(acquirente, evento, posti);
        //  Aggiorna i posti rimasti
        evento.setPostiDisponibili(evento.getPostiDisponibili() - posti);

        //  Salva evento aggiornato
        eventoRepository.save(evento);
        return prenotazioneEventoRepository.save(prenotazione);
    }
}

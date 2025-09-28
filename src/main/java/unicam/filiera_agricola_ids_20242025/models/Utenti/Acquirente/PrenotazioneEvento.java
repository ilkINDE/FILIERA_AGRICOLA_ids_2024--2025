package unicam.filiera_agricola_ids_20242025.models.Utenti.Acquirente;

import jakarta.persistence.*;
import unicam.filiera_agricola_ids_20242025.models.Evento;

import java.time.LocalDateTime;

@Entity
public class PrenotazioneEvento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idPrenotazione;

    @ManyToOne
    @JoinColumn(name = "id_acquirente", nullable = false)
    private Acquirente acquirente;

    @ManyToOne
    @JoinColumn(name = "id_evento", nullable = false)
    private Evento evento;

    private int postiPrenotati;

    private LocalDateTime dataPrenotazione = LocalDateTime.now();

    public PrenotazioneEvento() {}

    public PrenotazioneEvento(Acquirente acquirente, Evento evento, int postiPrenotati) {
        this.acquirente = acquirente;
        this.evento = evento;
        this.postiPrenotati = postiPrenotati;
    }

    public int getIdPrenotazione() {
        return idPrenotazione;
    }

    public Acquirente getAcquirente() {
        return acquirente;
    }

    public Evento getEvento() {
        return evento;
    }

    public LocalDateTime getDataPrenotazione() {
        return dataPrenotazione;
    }

    public int getPostiPrenotati() {
        return postiPrenotati;
    }

    public void setPostiPrenotati(int postiPrenotati) {
        this.postiPrenotati = postiPrenotati;
    }
}

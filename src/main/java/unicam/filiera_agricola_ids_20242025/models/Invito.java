package unicam.filiera_agricola_ids_20242025.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import unicam.filiera_agricola_ids_20242025.models.Utenti.Animatore;
import unicam.filiera_agricola_ids_20242025.models.Utenti.Venditori.Venditore;

@Entity
public class Invito {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idInvito;

    @ManyToOne
    @JsonBackReference
    private Venditore venditore;

    @ManyToOne
    @JsonBackReference
    private Animatore animatore;

    @ManyToOne
    private Evento evento;

    private String stato;

    public Invito() {}

    public Invito(Venditore venditore, Animatore animatore, Evento evento) {
        this.venditore = venditore;
        this.animatore = animatore;
        this.evento = evento;
        this.stato = "IN_ATTESA";
    }

    public int getIdInvito() {
        return idInvito;
    }

    public Venditore getVenditore() {
        return venditore;
    }

    public Animatore getAnimatore() {
        return animatore;
    }

    public Evento getEvento() {
        return evento;
    }

    public String getStato() {
        return stato;
    }

    public void setStato(String stato) {
        this.stato = stato;
    }
}

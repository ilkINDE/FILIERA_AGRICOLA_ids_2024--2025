package unicam.filiera_agricola_ids_20242025.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import unicam.filiera_agricola_ids_20242025.models.State.StateInvito.*;
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


    @Enumerated(EnumType.STRING)
    private StatoInvito statoInvito;

    @Transient
    private InvitoState state;

    public Invito() {}

    public Invito(Venditore venditore, Animatore animatore, Evento evento) {
        this.venditore = venditore;
        this.animatore = animatore;
        this.evento = evento;
        this.statoInvito = StatoInvito.IN_ATTESA;
    }

    public void setState(InvitoState state) {
        this.state = state;
    }

    @PostLoad
    public void initState() {
        switch (this.statoInvito) {
            case IN_ATTESA -> this.state = new InAttesaState();
            case ACCETTATO -> this.state = new AccettatoState();
            case RIFIUTATO -> this.state = new RifiutatoState();
        }
    }


    public void accetta() {
        state.accetta(this);
    }

    public void rifiuta() {
        state.rifiuta(this);
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

    public StatoInvito getStatoInvito() {
        return statoInvito;
    }

    public void setStatoInvito(StatoInvito statoInvito) {
        this.statoInvito = statoInvito;
    }


}

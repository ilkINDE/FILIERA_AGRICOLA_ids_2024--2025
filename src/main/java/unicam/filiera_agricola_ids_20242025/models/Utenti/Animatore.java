package unicam.filiera_agricola_ids_20242025.models.Utenti;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import unicam.filiera_agricola_ids_20242025.models.Evento;
import unicam.filiera_agricola_ids_20242025.models.Invitato;
import unicam.filiera_agricola_ids_20242025.models.OrganizzatoreInviti;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Animatore {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private int idAnimatore;

    //collegamento idAnimatore con idUtente
    @OneToOne
    @JoinColumn(name = "id_utente", nullable = false, unique = true)
    private Utente utente;

    private String email;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Evento> eventiCreati;



    public Animatore() {}

    public Animatore(Utente utente) {
        this.utente = utente;
        this.email = utente.getEmail();
    }



    public List<Evento> getEventiCreati() {
        return eventiCreati;
    }

    public void setEventiCreati(List<Evento> eventiCreati) {
        this.eventiCreati = eventiCreati;
    }

    public Utente getUtente() {
        return utente;
    }

    public void setUtente(Utente utente) {
        this.utente = utente;
    }
}

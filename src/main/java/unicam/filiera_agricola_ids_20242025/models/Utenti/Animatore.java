package unicam.filiera_agricola_ids_20242025.models.Utenti;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import unicam.filiera_agricola_ids_20242025.models.Evento;
import unicam.filiera_agricola_ids_20242025.models.Invitato;
import unicam.filiera_agricola_ids_20242025.models.OrganizzatoreInviti;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Animatore implements OrganizzatoreInviti {

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

    // Lista di osservatori (venditori)
    @Transient
    private List<Invitato> invitati = new ArrayList<>();

    public Animatore() {}

    public Animatore(Utente utente) {
        this.utente = utente;
        this.email = utente.getEmail();
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
    public void notificaInvitati(Evento evento) {
        for (Invitato i : invitati) {
            i.riceviInvito(evento, this);
        }
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

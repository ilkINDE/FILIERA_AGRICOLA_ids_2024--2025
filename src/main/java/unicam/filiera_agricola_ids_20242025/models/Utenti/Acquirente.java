package unicam.filiera_agricola_ids_20242025.models.Utenti;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

@Entity
public class Acquirente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private int idAcquirente;

    //collegamento idAcquirente con idUtente
    @OneToOne
    @JoinColumn(name = "id_utente", nullable = false, unique = true)
    private Utente utente;

    private String email;

    public Acquirente(Utente utente) {
        this.utente = utente;
        this.email = utente.getEmail();
    }

    public Acquirente() {}


    public int getIdAcquirente() {
        return idAcquirente;
    }

    public Utente getUtente() {
        return utente;
    }

    public void setUtente(Utente utente) {
        this.utente = utente;
    }
}

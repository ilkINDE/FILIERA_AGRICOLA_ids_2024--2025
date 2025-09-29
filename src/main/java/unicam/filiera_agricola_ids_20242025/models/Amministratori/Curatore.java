package unicam.filiera_agricola_ids_20242025.models.Amministratori;

import jakarta.persistence.*;
import unicam.filiera_agricola_ids_20242025.models.Utenti.Utente.Utente;

@Entity
public class Curatore {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idCuratore;

    //collegamento idCuratore con idUtente
    @OneToOne
    @JoinColumn(name = "id_utente", nullable = false, unique = true)
    private Utente utente;

    private String email;

    public Curatore(Utente utente){
        this.utente = utente;
        this.email = utente.getEmail();
    }

    public Curatore() {}

    public Utente getUtente() {
        return utente;
    }

    public void setUtente(Utente utente) {
        this.utente = utente;
    }
}

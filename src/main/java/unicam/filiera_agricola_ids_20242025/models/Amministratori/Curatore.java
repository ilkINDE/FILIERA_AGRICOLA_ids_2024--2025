package unicam.filiera_agricola_ids_20242025.models.Amministratori;

import jakarta.persistence.*;
import unicam.filiera_agricola_ids_20242025.models.Prodotti.Prodotto;
import unicam.filiera_agricola_ids_20242025.models.Utenti.Utente;

import java.util.List;

@Entity
public class Curatore {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idCuratore;

    //collegamento idCuratore con idUtente
    @OneToOne
    @JoinColumn(name = "id_utente", nullable = false, unique = true)
    private Utente utente;

    public Curatore(Utente utente){
        this.utente = utente;
    }

    public Curatore() {}

    public Utente getUtente() {
        return utente;
    }

    public void setUtente(Utente utente) {
        this.utente = utente;
    }
}

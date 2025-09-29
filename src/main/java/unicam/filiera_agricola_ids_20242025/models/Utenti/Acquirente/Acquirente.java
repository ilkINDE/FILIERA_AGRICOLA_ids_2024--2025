package unicam.filiera_agricola_ids_20242025.models.Utenti.Acquirente;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import unicam.filiera_agricola_ids_20242025.models.Utenti.Utente.Utente;

@Entity
public class Acquirente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idAcquirente;

    //collegamento idAcquirente con idUtente
    @OneToOne
    @JoinColumn(name = "id_utente", nullable = false, unique = true)
    private Utente utente;

    private String email;

    @OneToOne(mappedBy = "acquirente", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private Carrello carrello;

    public Acquirente(Utente utente) {
        this.utente = utente;
        this.email = utente.getEmail();
        this.carrello = new Carrello(this);
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

    public Carrello getCarrello() {
        return carrello;
    }

    public void setCarrello(Carrello carrello) {
        this.carrello = carrello;
    }
}

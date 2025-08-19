package unicam.filiera_agricola_ids_20242025.models;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
public class Utente {
    @Id
    @GeneratedValue
    private int idUtente;

    private String email;
    private String password;

    @ElementCollection(fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    private Set<Ruolo> Ruoli = new HashSet<>();

    // costruttore che assegna sempre ACQUIRENTE come ruolo di default
    public Utente(String email, String password) {
        this.email = email;
        this.password = password;
        this.Ruoli.add(Ruolo.ACQUIRENTE);
    }

    public Utente() {}

    public Set<Ruolo> getRuoli() {
        return Ruoli;
    }

    public void setRuoli(Set<Ruolo> ruoli) {
        Ruoli = ruoli;
    }
}


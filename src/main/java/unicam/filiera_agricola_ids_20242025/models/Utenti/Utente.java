package unicam.filiera_agricola_ids_20242025.models.Utenti;

import jakarta.persistence.*;
import unicam.filiera_agricola_ids_20242025.models.Ruolo;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Utente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idUtente;

    private String nome;
    private String cognome;
    private LocalDate dataNascita;
    private String comuneDiProvenienza;
    private String email;
    private String password;

    @ElementCollection(fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    private Set<Ruolo> Ruoli = new HashSet<>();

    // costruttore che assegna sempre ACQUIRENTE come ruolo di default
    public Utente(String nome, String cognome, LocalDate dataNascita, String comuneDiProvenienza, String email, String password) {
        this.nome = nome;
        this.cognome = cognome;
        this.dataNascita = dataNascita;
        this.comuneDiProvenienza = comuneDiProvenienza;
        this.email = email;
        this.password = password;
        this.Ruoli.add(Ruolo.ACQUIRENTE);
    }

    public Utente() {}

    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }
    public String getCognome() {
        return cognome;
    }
    public void setCognome(String cognome) {
        this.cognome = cognome;
    }
    public LocalDate getDataNascita() {
        return dataNascita;
    }
    public void setDataNascita(LocalDate dataNascita) {
        this.dataNascita = dataNascita;
    }
    public String getComuneDiProvenienza() {
        return comuneDiProvenienza;
    }
    public void setComuneDiProvenienza(String comuneDiProvenienza) {
        this.comuneDiProvenienza = comuneDiProvenienza;
    }

    public String getEmail() {return email;}

    public Set<Ruolo> getRuoli() {
        return Ruoli;
    }

    public void setRuoli(Set<Ruolo> ruoli) {
        Ruoli = ruoli;
    }
}


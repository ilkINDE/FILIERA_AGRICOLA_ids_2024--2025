package unicam.filiera_agricola_ids_20242025.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.List;

@Entity
public class Animatore {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private int idAnimatore;

    private String nome;
    private String cognome;
    //aggiungere dati di login

    @OneToMany
    private List<Evento> eventiCreati;


    public Animatore() {}


    public Animatore(String Nome , String Cognome) {
        this.nome = Nome;
        this.cognome = Cognome;
    }

    public List<Evento> getEventiCreati() {
        return eventiCreati;
    }

    public void setEventiCreati(List<Evento> eventiCreati) {
        this.eventiCreati = eventiCreati;
    }

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
}

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


    //aggiungere dati di login

    @OneToMany
    private List<Evento> eventiCreati;


    public Animatore() {}


    public List<Evento> getEventiCreati() {
        return eventiCreati;
    }

    public void setEventiCreati(List<Evento> eventiCreati) {
        this.eventiCreati = eventiCreati;
    }


}

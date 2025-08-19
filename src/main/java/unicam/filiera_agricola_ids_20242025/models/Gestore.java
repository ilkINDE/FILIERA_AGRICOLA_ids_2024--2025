package unicam.filiera_agricola_ids_20242025.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;

import java.util.List;

@Entity
public class Gestore {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private int idGestore;

    @OneToMany(mappedBy = "gestore", cascade = CascadeType.ALL)
    private List<RichiestaRuolo> richiesteRuolo ;

    private String nome;

    public Gestore(String Nome) {
        this.nome = Nome;
    }

    public Gestore() {}

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public List<RichiestaRuolo> getRichiesteRuolo() {
        return richiesteRuolo;
    }

    public void setRichiesteRuolo(List<RichiestaRuolo> richiesteRuolo) {
        this.richiesteRuolo = richiesteRuolo;
    }
}

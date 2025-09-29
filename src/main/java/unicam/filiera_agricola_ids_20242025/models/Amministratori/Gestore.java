package unicam.filiera_agricola_ids_20242025.models.Amministratori;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import unicam.filiera_agricola_ids_20242025.models.Utenti.Utente.RichiestaRuolo;

import java.util.List;

@Entity
public class Gestore {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private int idGestore = 1;

    @OneToMany(mappedBy = "gestore", cascade = CascadeType.ALL)
    private List<RichiestaRuolo> richiesteRuolo ;

    private String nome;

    public Gestore(String Nome) {

        this.idGestore = 1;
        this.nome = Nome;
    }

    public Gestore() {}

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getIdGestore() {
        return idGestore;
    }

    public void setIdGestore(int idGestore) {}

    public List<RichiestaRuolo> getRichiesteRuolo() {
        return richiesteRuolo;
    }

    public void setRichiesteRuolo(List<RichiestaRuolo> richiesteRuolo) {
        this.richiesteRuolo = richiesteRuolo;
    }
}

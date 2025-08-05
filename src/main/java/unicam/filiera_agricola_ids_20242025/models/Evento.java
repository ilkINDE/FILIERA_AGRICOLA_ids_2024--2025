package unicam.filiera_agricola_ids_20242025.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.util.Date;

@Entity
public class Evento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private int id;
    private String nome;
    private int maxPartecipanti;
    private Date data;
    private String descrizione;
    private int postiDisponibili;

    public Evento() {}
    public Evento(int id, String nome, int maxPartecipanti, Date data, String descrizione, int postiDisponibili) {
        this.id = id;
        this.nome = nome;
        this.maxPartecipanti = maxPartecipanti;
        this.data = data;
        this.descrizione = descrizione;
        this.postiDisponibili = maxPartecipanti;
    }
    public int getId() {
        return id;
    }


    public String getNome() {
        return nome;
    }

    public int getMaxPartecipanti() {
        return maxPartecipanti;
    }

    public Date getData() {
        return data;
    }

    public String getDescrizione() {
        return descrizione;
    }
    public int getPostiDisponibili() {
        return postiDisponibili;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setMaxPartecipanti(int maxPartecipanti) {
        this.maxPartecipanti = maxPartecipanti;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }
    public void setPostiDisponibili(int postiDisponibili) {
        this.postiDisponibili = postiDisponibili;
    }
}

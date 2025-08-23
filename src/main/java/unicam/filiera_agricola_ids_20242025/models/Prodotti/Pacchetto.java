package unicam.filiera_agricola_ids_20242025.models.Prodotti;

import jakarta.persistence.Entity;
import jakarta.persistence.*;
import unicam.filiera_agricola_ids_20242025.models.Venditori.Distributore;

import java.util.List;

@Entity
public class Pacchetto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idPacchetto;

    private String nome;
    private String descrizione;

    @ManyToMany
    @JoinTable(
            name = "pacchetto_prodotti",
            joinColumns = @JoinColumn(name = "pacchetto_id"),
            inverseJoinColumns = @JoinColumn(name = "prodotto_id")
    )
    private List<Prodotto> prodotti;

    @ManyToOne
    @JoinColumn(name = "distributore_id")
    private Distributore distributore;

    public Pacchetto() {}

    public Pacchetto(String nome, String descrizione, Distributore distributore) {
        this.nome = nome;
        this.descrizione = descrizione;
        this.distributore = distributore;
    }

    public int getId() {
        return idPacchetto;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public List<Prodotto> getProdotti() {
        return prodotti;
    }

    public void setProdotti(List<Prodotto> prodotti) {
        this.prodotti = prodotti;
    }

    public Distributore getDistributore() {
        return distributore;
    }

    public void setDistributore(Distributore distributore) {
        this.distributore = distributore;
    }
}

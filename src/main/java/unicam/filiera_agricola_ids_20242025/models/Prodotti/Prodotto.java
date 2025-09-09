package unicam.filiera_agricola_ids_20242025.models.Prodotti;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import unicam.filiera_agricola_ids_20242025.models.Utenti.Venditori.Venditore;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Prodotto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idProdotto;

    private String nome;
    private String descrizione;
    private double prezzo;
    private StatoProdotto statoProdotto;

    @ManyToOne
    @JoinColumn(name = "venditore_id")
    @JsonBackReference
    private Venditore venditore;

    public Prodotto() {}

    public Prodotto(String nome, double prezzo, String descrizione, Venditore venditore) {
        this.nome = nome;
        this.prezzo = prezzo;
        this.descrizione = descrizione;
        this.venditore = venditore;
        this.statoProdotto = StatoProdotto.BOZZA;
    }

    public String getNome() {

        return nome;
    }

    public int getIdProdotto() {
        return idProdotto;
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

    public double getPrezzo() {

        return prezzo;
    }

    public void setPrezzo(double prezzo) {

        this.prezzo = prezzo;
    }

    public Venditore getVenditore() {

        return venditore;
    }

    public void setVenditore(Venditore venditore) {

        this.venditore = venditore;
    }

    public StatoProdotto getStatoProdotto() {
        return statoProdotto;
    }

    public void setStatoProdotto(StatoProdotto statoProdotto) {
        this.statoProdotto = statoProdotto;
    }

}

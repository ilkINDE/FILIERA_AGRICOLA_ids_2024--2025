package unicam.filiera_agricola_ids_20242025.models.Prodotti;

import jakarta.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Prodotto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idProdotto;

    private String nome;
    private String descrizione;
    private double prezzo;
    // attributo Venditore

    public Prodotto() {}

    public Prodotto(String nome, double prezzo, String descrizione) {
        this.nome = nome;
        this.prezzo = prezzo;
        this.descrizione = descrizione;
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

    public double getPrezzo() {
        return prezzo;
    }

    public void setPrezzo(double prezzo) {
        this.prezzo = prezzo;
    }
}

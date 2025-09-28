package unicam.filiera_agricola_ids_20242025.models.Utenti.Acquirente;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import unicam.filiera_agricola_ids_20242025.models.Prodotti.Pacchetto;
import unicam.filiera_agricola_ids_20242025.models.Prodotti.Prodotto;

@Entity
public class CarrelloItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idItem;

    @ManyToOne
    @JoinColumn(name = "carrello_id")
    @JsonBackReference
    private Carrello carrello;

    @ManyToOne
    @JoinColumn(name = "prodotto_id", nullable = true)
    private Prodotto prodotto;

    @ManyToOne
    @JoinColumn(name = "pacchetto_id", nullable = true)
    private Pacchetto pacchetto;

    private int quantita;
    private double prezzoUnitario;

    public CarrelloItem() {}

    // Costruttore per prodotto
    public CarrelloItem(Prodotto prodotto, int quantita) {
        this.prodotto = prodotto;
        this.quantita = quantita;
        this.prezzoUnitario = prodotto.getPrezzo();
    }

    // Costruttore per pacchetto
    public CarrelloItem(Pacchetto pacchetto, int quantita) {
        this.pacchetto = pacchetto;
        this.quantita = quantita;
        this.prezzoUnitario = pacchetto.getPrezzo();
    }

    public int getIdItem() {
        return idItem;
    }

    public Carrello getCarrello() {
        return carrello;
    }

    public void setCarrello(Carrello carrello) {
        this.carrello = carrello;
    }

    public Prodotto getProdotto() {
        return prodotto;
    }

    public Pacchetto getPacchetto() {
        return pacchetto;
    }

    public int getQuantita() {
        return quantita;
    }

    public double getPrezzoUnitario() {
        return prezzoUnitario;
    }

    public double getPrezzoTotale() {
        return prezzoUnitario * quantita;
    }
}

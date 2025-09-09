package unicam.filiera_agricola_ids_20242025.models;

import jakarta.persistence.*;
import unicam.filiera_agricola_ids_20242025.models.Utenti.Acquirente;

import java.time.LocalDateTime;
import java.util.List;

@Entity
public class Ordine {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idOrdine;

    @ManyToOne
    @JoinColumn(name = "acquirente_id")
    private Acquirente acquirente;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "ordine_id")
    private List<CarrelloItem> items;

    private LocalDateTime dataAcquisto;
    private double totale;

    public Ordine() {}

    public Ordine(Acquirente acquirente, List<CarrelloItem> items) {
        this.acquirente = acquirente;
        this.items = items;
        this.dataAcquisto = LocalDateTime.now();
        this.totale = calcolaTotale();
    }

    public int getIdOrdine() {
        return idOrdine;
    }

    public Acquirente getAcquirente() {
        return acquirente;
    }

    public List<CarrelloItem> getItems() {
        return items;
    }

    public LocalDateTime getDataAcquisto() {
        return dataAcquisto;
    }

    public double getTotale() {
        return totale;
    }

    private double calcolaTotale() {
        return items.stream().mapToDouble(CarrelloItem::getPrezzoTotale).sum();
    }
}
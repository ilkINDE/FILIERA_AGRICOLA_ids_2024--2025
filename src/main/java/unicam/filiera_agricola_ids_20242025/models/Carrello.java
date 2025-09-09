package unicam.filiera_agricola_ids_20242025.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import unicam.filiera_agricola_ids_20242025.models.Utenti.Acquirente;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Carrello {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idCarrello;

    @OneToOne
    @JoinColumn(name = "acquirente_id")
    private Acquirente acquirente;

    @OneToMany(mappedBy = "carrello", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<CarrelloItem> items = new ArrayList<>();

    public Carrello() {}

    public Carrello(Acquirente acquirente) {
        this.acquirente = acquirente;
    }

    public int getIdCarrello() {
        return idCarrello;
    }

    public Acquirente getAcquirente() {
        return acquirente;
    }

    public List<CarrelloItem> getItems() {
        return items;
    }

    //  Metodi di utilità
    public void aggiungiItem(CarrelloItem item) {
        items.add(item);
        item.setCarrello(this);
    }

    public void rimuoviItem(CarrelloItem item) {
        items.remove(item);
        item.setCarrello(null);
    }

    public void svuotaCarrello() {
        items.clear();
    }
}
package unicam.filiera_agricola_ids_20242025.models.Prodotti;

import jakarta.persistence.Entity;

@Entity
public class ProdottoProduttore extends Prodotto {

    private String metodoDiColtivazione;

    public ProdottoProduttore() {}

    public ProdottoProduttore(String nome, double prezzo, String descrizione, String metodoDiColtivazione) {
        super(nome, prezzo, descrizione);
        this.metodoDiColtivazione =metodoDiColtivazione ;
    }

    public String getMetodoDiColtivazione() {
        return metodoDiColtivazione;
    }

    public void setMetodoDiColtivazione(String metodoDiColtivazione) {
        this.metodoDiColtivazione = metodoDiColtivazione;
    }
}

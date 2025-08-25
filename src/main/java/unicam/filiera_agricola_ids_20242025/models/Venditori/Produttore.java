package unicam.filiera_agricola_ids_20242025.models.Venditori;

import jakarta.persistence.Entity;
import unicam.filiera_agricola_ids_20242025.models.Prodotti.ProdottoProduttore;
import unicam.filiera_agricola_ids_20242025.models.Utenti.Utente;

@Entity
public class Produttore extends Venditore{

    private String metodoDiColtivazione;

    public Produttore() {}

    public Produttore(Utente utente, String nome, Long Piva, String metodoDiColtivazione) {
        super(utente, nome, Piva);
        this.metodoDiColtivazione = metodoDiColtivazione;
    }

    public ProdottoProduttore creaProdotto(String nome, double prezzo, String descrizione, String metodoColtivazione) {
        ProdottoProduttore prodotto = new ProdottoProduttore(nome, prezzo, descrizione, this, metodoDiColtivazione);
        getProdotti().add(prodotto);
        return prodotto;
    }

    public String getMtodoDiColtivazione() {

        return metodoDiColtivazione;
    }

    public void setMetodoDiColtivazione(String metodoDiColtivazione) {

        this.metodoDiColtivazione = metodoDiColtivazione;
    }
}

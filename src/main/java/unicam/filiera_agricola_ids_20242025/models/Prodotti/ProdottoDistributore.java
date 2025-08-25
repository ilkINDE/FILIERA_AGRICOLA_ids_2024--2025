package unicam.filiera_agricola_ids_20242025.models.Prodotti;

import jakarta.persistence.Entity;
import unicam.filiera_agricola_ids_20242025.models.Venditori.Distributore;

@Entity
public class ProdottoDistributore extends Prodotto {

    public ProdottoDistributore(String nome, double prezzo, String descrizione, Distributore venditore) {
        super(nome, prezzo, descrizione, venditore);
    }

    public ProdottoDistributore() {}
}


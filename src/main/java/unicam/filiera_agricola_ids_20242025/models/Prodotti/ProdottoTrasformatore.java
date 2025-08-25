package unicam.filiera_agricola_ids_20242025.models.Prodotti;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import unicam.filiera_agricola_ids_20242025.models.Venditori.Produttore;
import unicam.filiera_agricola_ids_20242025.models.Venditori.Venditore;

import java.util.ArrayList;
import java.util.List;

@Entity
public class ProdottoTrasformatore extends Prodotto {

    private String processoDiTrasformazione;

    @ManyToMany
    private List<Produttore> produttoriAssociati ;

    public ProdottoTrasformatore() {}

    public ProdottoTrasformatore(String nome, double prezzo, String descrizione, Venditore venditore, String processoDiTrasformazione, List<Produttore> produttoriAssociati) {
        super(nome, prezzo, descrizione, venditore);
        this.processoDiTrasformazione = processoDiTrasformazione;
        this.produttoriAssociati = produttoriAssociati;
    }

    public String getProcessoDiTrasformazione() {

        return processoDiTrasformazione;
    }

    public void setProcessoDiTrasformazione(String processoDiTrasformazione) {

        this.processoDiTrasformazione = processoDiTrasformazione;
    }

    public List<Produttore> getProduttoriAssociati() {

        return produttoriAssociati;
    }

    public void setProduttoriAssociati(List<Produttore> produttoriAssociati) {

        this.produttoriAssociati = produttoriAssociati;
    }
}

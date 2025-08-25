package unicam.filiera_agricola_ids_20242025.models.Venditori;

import jakarta.persistence.Entity;
import unicam.filiera_agricola_ids_20242025.models.Prodotti.ProdottoTrasformatore;

import java.util.List;

@Entity
public class Trasformatore extends Venditore{

    private String processoDiTrasformazione;

    public Trasformatore(){}

    public Trasformatore(String nome, Long Piva, String processoDiTrasformazione){
        super(nome, Piva);
        this.processoDiTrasformazione = processoDiTrasformazione;
    }

    public ProdottoTrasformatore creaProdotto(String nome, double prezzo, String descrizione, String processoTrasformazione, List<Produttore> produttoriAssociati) {
        ProdottoTrasformatore prodotto = new ProdottoTrasformatore(nome, prezzo, descrizione, this, processoTrasformazione, produttoriAssociati);
        getProdotti().add(prodotto);
        return prodotto;
    }

    public String getProcessoDiTrasformazione() {

        return processoDiTrasformazione;
    }

    public void setProcessoDiTrasformazione(String processoDiTrasformazione) {

        this.processoDiTrasformazione = processoDiTrasformazione;
    }
}

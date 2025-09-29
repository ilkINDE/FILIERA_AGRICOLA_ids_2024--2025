package unicam.filiera_agricola_ids_20242025.models.Utenti.Venditori;

import jakarta.persistence.Entity;
import unicam.filiera_agricola_ids_20242025.models.Prodotti.ProdottoTrasformatore;
import unicam.filiera_agricola_ids_20242025.models.Utenti.Utente.Utente;

import java.util.List;

@Entity
public class Trasformatore extends Venditore{

    private String processoDiTrasformazione;

    public Trasformatore(){}

    public Trasformatore(Utente utente, String nome, Long pIva, String processoDiTrasformazione){
        super(utente, nome, pIva);
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

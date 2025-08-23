package unicam.filiera_agricola_ids_20242025.models.Prodotti;

import jakarta.persistence.Entity;

@Entity
public class ProdottoTrasformatore extends Prodotto {

    private String processoDiTrasformazione;
    //attributo Produttori associati

    public ProdottoTrasformatore() {}

    public ProdottoTrasformatore(String nome, double prezzo, String descrizione, String processoDiTrasformazione) {
        super(nome, prezzo, descrizione);
        this.processoDiTrasformazione = processoDiTrasformazione;
    }

    public String getProcessoDiTrasformazione() {
        return processoDiTrasformazione;
    }

    public void setProcessoDiTrasformazione(String processoDiTrasformazione) {
        this.processoDiTrasformazione = processoDiTrasformazione;
    }

}

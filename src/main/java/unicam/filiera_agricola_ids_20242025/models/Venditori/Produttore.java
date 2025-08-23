package unicam.filiera_agricola_ids_20242025.models.Venditori;

import jakarta.persistence.Entity;

@Entity
public class Produttore extends Venditore{

    private String infoColtivazione;

    public Produttore() {}

    public Produttore(String nome, Long Piva, String infoColtivazione) {
        super(nome, Piva);
        this.infoColtivazione = infoColtivazione;
    }

    public String getInfoColtivazione() {
        return infoColtivazione;
    }

    public void setInfoColtivazione(String infoColtivazione) {
        this.infoColtivazione = infoColtivazione;
    }
}

package unicam.filiera_agricola_ids_20242025.models.Venditori;

import org.springframework.data.repository.cdi.Eager;

@Eager
public class Tasformatore extends Venditore{

    private String infoTrasformazione;

    public Tasformatore(){}

    public Tasformatore(String nome, Long Piva, String infoTrasformazione){
        super(nome, Piva);
        this.infoTrasformazione = infoTrasformazione;
    }

    public String getInfoTrasformazione() {
        return infoTrasformazione;
    }

    public void setInfoTrasformazione(String infoTrasformazione) {
        this.infoTrasformazione = infoTrasformazione;
    }
}

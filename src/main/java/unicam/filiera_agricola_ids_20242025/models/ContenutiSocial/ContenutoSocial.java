package unicam.filiera_agricola_ids_20242025.models.ContenutiSocial;

import jakarta.persistence.*;
import unicam.filiera_agricola_ids_20242025.models.Prodotti.Prodotto;

@Entity
public class ContenutoSocial {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idContenutoSocial;

    @ManyToOne
    @JoinColumn(name = "prodotto_da_pubblicare_id_prodotto")
    private Prodotto prodottoDaPubblicare;

    private String descrizioneContenutoSocial;

    public ContenutoSocial(Prodotto prodottoDaPubblicare, String descrizioneContenutoSocial) {
        this.prodottoDaPubblicare = prodottoDaPubblicare;
        this.descrizioneContenutoSocial = descrizioneContenutoSocial;
    }

    public ContenutoSocial() {}

    public Prodotto getProdottoDaPubblicare() {
        return prodottoDaPubblicare;
    }

    public void setProdottoDaPubblicare(Prodotto prodottoDaPubblicare) {
        this.prodottoDaPubblicare = prodottoDaPubblicare;
    }

    public String getDescrizioneContenutoSocial() {
        return descrizioneContenutoSocial;
    }

    public void setDescrizioneContenutoSocial(String descrizioneContenutoSocial) {
        this.descrizioneContenutoSocial = descrizioneContenutoSocial;
    }
}
package unicam.filiera_agricola_ids_20242025.models.Venditori;

import jakarta.persistence.*;
import unicam.filiera_agricola_ids_20242025.models.Prodotti.Prodotto;

import java.util.List;

@Entity
public abstract class Venditore {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idVenditore;

    private String nome;
    private Long Piva;

    @OneToMany(mappedBy = "venditore", cascade = CascadeType.ALL)
    private List<Prodotto> prodotti;

    public Venditore() {}

    public Venditore(String nome, Long Piva) {
        this.nome = nome;
        this.Piva = Piva;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Long getPiva() {
        return Piva;
    }

    public void setPiva(Long piva) {
        Piva = piva;
    }

    public List<Prodotto> getProdotti() {
        return prodotti;
    }

    public void setProdotti(List<Prodotto> prodotti) {
        this.prodotti = prodotti;
    }
}

package unicam.filiera_agricola_ids_20242025.models.Venditori;

import jakarta.persistence.*;
import unicam.filiera_agricola_ids_20242025.models.Prodotti.Prodotto;
import unicam.filiera_agricola_ids_20242025.models.Utenti.Utente;

import java.util.List;

@Entity
public abstract class Venditore {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idVenditore;

    //collegamento idVenditore con idUtente
    @OneToOne
    @JoinColumn(name = "id_utente", nullable = false, unique = true)
    private Utente utente;

    private String nome;
    private Long Piva;

    @OneToMany(mappedBy = "venditore", cascade = CascadeType.ALL)
    private List<Prodotto> prodotti;

    public Venditore() {}

    public Venditore(Utente utente, String nome, Long Piva) {
        this.utente = utente;
        this.nome = nome;
        this.Piva = Piva;
    }

    public String getNome() {
        return nome;}

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

    public Utente getUtente() {
        return utente;
    }

    public void setUtente(Utente utente) {
        this.utente = utente;
    }
}

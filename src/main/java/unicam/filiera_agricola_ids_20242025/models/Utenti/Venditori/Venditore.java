package unicam.filiera_agricola_ids_20242025.models.Utenti.Venditori;

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

    private Long pIva;

    private String email;

    @OneToMany(mappedBy = "venditore", cascade = CascadeType.ALL)
    private List<Prodotto> prodotti;

    public Venditore() {}

    public Venditore(Utente utente, String nome, Long pIva) {
        this.utente = utente;
        this.nome = nome;
        this.pIva = pIva;
        this.email = utente.getEmail();
    }

    public String getNome() {
        return nome;}

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Long getPIva() {
        return pIva;
    }

    public void setPIva(Long pIva) {
        pIva = pIva;
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

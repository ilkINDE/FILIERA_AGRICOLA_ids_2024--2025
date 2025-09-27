package unicam.filiera_agricola_ids_20242025.models.Prodotti;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.Entity;
import jakarta.persistence.*;
import unicam.filiera_agricola_ids_20242025.models.State.StateProdotto.*;
import unicam.filiera_agricola_ids_20242025.models.Utenti.Venditori.Distributore;

import java.util.List;

@Entity
public class Pacchetto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idPacchetto;

    private String nome;
    private double prezzo;
    private String descrizione;

    @Enumerated(EnumType.STRING)
    private StatoProdotto statoProdotto;

    @ManyToMany
    @JoinTable(
            name = "pacchetto_prodotti",
            joinColumns = @JoinColumn(name = "pacchetto_id"),
            inverseJoinColumns = @JoinColumn(name = "prodotto_id")
    )
    private List<Prodotto> prodotti;

    @ManyToOne
    @JoinColumn(name = "distributore_id")
    @JsonBackReference
    private Distributore distributore;


    @Transient
    private ProdottoState<Pacchetto> state;



    public Pacchetto(String nome, double prezzo, String descrizione, List<Prodotto> prodottiInclusi, Distributore distributore) {
        this.nome = nome;
        this.prezzo = prezzo;
        this.descrizione = descrizione;
        this.distributore = distributore;
        this.statoProdotto = StatoProdotto.BOZZA;
    }

    public Pacchetto() {}


    public void setState(ProdottoState state) {
        this.state = state;
    }

    @PostLoad
    public void initState() {
        switch (this.statoProdotto) {
            case BOZZA -> this.state = new BozzaState<>();
            case IN_REVISIONE -> this.state = new InRevisioneState<>();
            case APPROVATO -> this.state = new ApprovatoState<>();
            case RIFIUTATO -> this.state = new RifiutatoState<>();
        }
    }

    public void inviaInRevisione() { state.inviaInRevisione(this); }
    public void approva() { state.approva(this); }
    public void rifiuta() { state.rifiuta(this); }


    public int getIdPacchetto() {return idPacchetto;}

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public double getPrezzo() {return prezzo;}

    public void setPrezzo(double prezzo) {this.prezzo = prezzo;}

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public List<Prodotto> getProdotti() {
        return prodotti;
    }

    public void setProdotti(List<Prodotto> prodotti) {
        this.prodotti = prodotti;
    }

    public Distributore getDistributore() {
        return distributore;
    }

    public void setDistributore(Distributore distributore) {
        this.distributore = distributore;
    }

    public StatoProdotto getStatoProdotto() {return statoProdotto;}

    public void setStatoProdotto(StatoProdotto statoProdotto) {this.statoProdotto = statoProdotto;}
}

package unicam.filiera_agricola_ids_20242025.models.Utenti.Venditori;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import unicam.filiera_agricola_ids_20242025.models.Evento;
import unicam.filiera_agricola_ids_20242025.models.Invitato;
import unicam.filiera_agricola_ids_20242025.models.Invito;
import unicam.filiera_agricola_ids_20242025.models.Prodotti.Prodotto;
import unicam.filiera_agricola_ids_20242025.models.Utenti.Animatore;
import unicam.filiera_agricola_ids_20242025.models.Utenti.Utente;

import java.util.ArrayList;
import java.util.List;

@Entity
public abstract class Venditore implements Invitato {

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
    @JsonManagedReference
    private List<Prodotto> prodotti;

    @OneToMany(mappedBy = "venditore", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Invito> invitiRicevuti = new ArrayList<>();

    public Venditore() {}

    public Venditore(Utente utente, String nome, Long pIva) {
        this.utente = utente;
        this.nome = nome;
        this.pIva = pIva;
        this.email = utente.getEmail();
    }

    /* Observer pattern
     Questo metodo viene chiamato dall'Animatore quando notifica un nuovo evento.
     Crea un oggetto Invito associando il venditore (this), l'animatore e l'evento,
     e lo aggiunge alla lista degli inviti ricevuti dal venditore.*/

    @Override
    public void riceviInvito(Evento evento, Animatore animatore) {
        Invito invito = new Invito(this, animatore, evento);
        invitiRicevuti.add(invito);
    }

    // Gestione inviti
    public void accettaInvito(int idInvito) {
        invitiRicevuti.stream()
                .filter(invito -> invito.getIdInvito() == idInvito)
                .findFirst()
                .ifPresent(invito -> {
                    invito.setStato("ACCETTATO");
                    invitiRicevuti.remove(invito);
                });
    }

    public void rifiutaInvito(int idInvito) {
        invitiRicevuti.stream()
                .filter(invito -> invito.getIdInvito() == idInvito)
                .findFirst()
                .ifPresent(invito -> {
                    invito.setStato("RIFIUTATO");
                    invitiRicevuti.remove(invito);
                });
    }


    public List<Invito> getInvitiRicevuti() {
        return invitiRicevuti;
    }

    public void setInvitiRicevuti(List<Invito> invito) {
        this.invitiRicevuti = invito;
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

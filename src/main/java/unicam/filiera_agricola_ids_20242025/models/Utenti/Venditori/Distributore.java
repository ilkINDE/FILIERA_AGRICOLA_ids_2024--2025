package unicam.filiera_agricola_ids_20242025.models.Utenti.Venditori;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import unicam.filiera_agricola_ids_20242025.models.Prodotti.Pacchetto;
import unicam.filiera_agricola_ids_20242025.models.Prodotti.Prodotto;
import unicam.filiera_agricola_ids_20242025.models.Prodotti.ProdottoDistributore;
import unicam.filiera_agricola_ids_20242025.models.Utenti.Utente.Utente;

import java.util.List;

@Entity
public class Distributore extends Venditore{

        @OneToMany(mappedBy = "distributore", cascade = CascadeType.ALL)
        @JsonManagedReference
        private List<Pacchetto> pacchetti;

        public Distributore() {}

        public Distributore(Utente utente, String nome, Long pIva) {

            super(utente, nome, pIva);
        }

         public Pacchetto creaPacchetto(String nome, double prezzo, String descrizione, List<Prodotto> prodottiInclusi) {
               Pacchetto pacchetto = new Pacchetto(nome, prezzo, descrizione, prodottiInclusi, this);
               getPacchetti().add(pacchetto);
               return pacchetto;
           }

           public ProdottoDistributore creaProdotto(String nome, double prezzo, String descrizione) {
               ProdottoDistributore prodotto = new ProdottoDistributore(nome, prezzo, descrizione, this);
               getProdotti().add(prodotto);
               return prodotto;
           }

              public List<Pacchetto> getPacchetti() {

                  return pacchetti; }

               public void setPacchetti(List<Pacchetto> pacchetti) {

                    this.pacchetti = pacchetti; }
}

package unicam.filiera_agricola_ids_20242025.models.Venditori;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import unicam.filiera_agricola_ids_20242025.models.Prodotti.Pacchetto;

import java.util.List;

@Entity
public class Distributore extends Venditore{

        @OneToMany(mappedBy = "distributore", cascade = CascadeType.ALL)
        private List<Pacchetto> pacchetti;

        public Distributore() {}

        public Distributore(String nome, Long Piva) {
            super(nome, Piva);
        }

        public List<Pacchetto> getPacchetti() { return pacchetti; }
        public void setPacchetti(List<Pacchetto> pacchetti) { this.pacchetti = pacchetti; }
}

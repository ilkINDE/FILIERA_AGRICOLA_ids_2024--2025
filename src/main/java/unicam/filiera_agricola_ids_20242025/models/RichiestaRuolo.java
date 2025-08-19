package unicam.filiera_agricola_ids_20242025.models;

import jakarta.persistence.*;

    @Entity
    public class RichiestaRuolo{
        @Id
        @GeneratedValue
        private int idRichiesta;

        @ManyToOne
        @JoinColumn(name = "utente_id")
        private Utente utente;

        @ManyToOne
        @JoinColumn(name = "id_gestore")
        private Gestore gestore;

        @Enumerated(EnumType.STRING)
        private Ruolo ruoloRichiesto;

        @Enumerated(EnumType.STRING)
        private StatoRichiesta statoRichiesta;

        public RichiestaRuolo(Utente utente, Ruolo ruoloRichiesto) {
            this.utente = utente;
            this.ruoloRichiesto = ruoloRichiesto;
            this.statoRichiesta = StatoRichiesta.IN_ATTESA;
        }

        public Utente getUtente() {
            return utente;
        }

        public void setUtente(Utente utente) {
            this.utente = utente;
        }

        public Ruolo getRuoloRichiesto() {
            return ruoloRichiesto;
        }

        public void setRuoloRichiesto(Ruolo ruoloRichiesto) {
            this.ruoloRichiesto = ruoloRichiesto;
        }

        public StatoRichiesta getStatoRichiesta() {
            return statoRichiesta;
        }

        public void setStatoRichiesta(StatoRichiesta statoRichiesta) {
            this.statoRichiesta = statoRichiesta;
        }

        public RichiestaRuolo(){}
    }
package unicam.filiera_agricola_ids_20242025.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import unicam.filiera_agricola_ids_20242025.models.Prodotti.Pacchetto;
import unicam.filiera_agricola_ids_20242025.models.Prodotti.Prodotto;
import unicam.filiera_agricola_ids_20242025.models.Prodotti.ProdottoProduttore;
import unicam.filiera_agricola_ids_20242025.models.Prodotti.ProdottoTrasformatore;
import unicam.filiera_agricola_ids_20242025.models.Venditori.Distributore;
import unicam.filiera_agricola_ids_20242025.models.Venditori.Produttore;
import unicam.filiera_agricola_ids_20242025.models.Venditori.Trasformatore;
import unicam.filiera_agricola_ids_20242025.models.Venditori.Venditore;
import unicam.filiera_agricola_ids_20242025.repository.PacchettoRepository;
import unicam.filiera_agricola_ids_20242025.repository.ProdottoRepository;
import unicam.filiera_agricola_ids_20242025.repository.VenditoreRepository;
import java.util.List;

@Service
public class HandlerVenditore {

        private final VenditoreRepository venditoreRepository;
        private final ProdottoRepository prodottoRepository;
        private final PacchettoRepository pacchettoRepository;

        @Autowired
        public HandlerVenditore(VenditoreRepository venditoreRepository,
                                ProdottoRepository prodottoRepository,
                                PacchettoRepository pacchettoRepository) {
            this.venditoreRepository = venditoreRepository;
            this.prodottoRepository = prodottoRepository;
            this.pacchettoRepository = pacchettoRepository;
        }

        public Prodotto creaProdottoProduttore(int idVenditore, String nome, double prezzo, String descrizione, String metodoColtivazione) {
            Venditore venditore = venditoreRepository.findById(idVenditore)
                    .orElseThrow(()-> new RuntimeException("Venditore non trovato"));
            if (!(venditore instanceof Produttore produttore))
                throw new IllegalArgumentException("Non è un produttore");
            ProdottoProduttore prodottoProduttore = produttore.creaProdotto(nome, prezzo, descrizione, metodoColtivazione);
            return prodottoRepository.save(prodottoProduttore);
        }

        public Prodotto creaProdottoTrasformatore(int idVenditore, String nome, double prezzo, String descrizione, String processo, List<Produttore> produttoriAssociati) {
            Venditore venditore = venditoreRepository.findById(idVenditore)
                    .orElseThrow(()-> new RuntimeException("Venditore non trovato"));
            if (!(venditore instanceof Trasformatore trasformatore))
                throw new IllegalArgumentException("Non è un trasformatore");
            ProdottoTrasformatore prodottoTrafsormatore = trasformatore.creaProdotto(nome, prezzo, descrizione, processo, produttoriAssociati);
            return prodottoRepository.save(prodottoTrafsormatore);
        }

        public Prodotto creaProdottoDistributore(int idVenditore, String nome, double prezzo, String descrizione) {
            Venditore venditore = venditoreRepository.findById(idVenditore)
                    .orElseThrow(()-> new RuntimeException("Venditore non trovato"));
            if (!(venditore instanceof Distributore distributore))
                throw new IllegalArgumentException("Non è un distributore");
            Prodotto prodottoDistributore = distributore.creaProdotto(nome, prezzo, descrizione);
            return prodottoRepository.save(prodottoDistributore);
        }

        public Pacchetto creaPacchetto(int idVenditore, String nome, double prezzo, String descrizione, List<Integer> idProdotti) {
            Venditore venditore = venditoreRepository.findById(idVenditore)
                    .orElseThrow(()-> new RuntimeException("Venditore non trovato"));
            if (!(venditore instanceof Distributore distributore))
                throw new IllegalArgumentException("Solo distributore può creare pacchetti");
            List<Prodotto> prodotti = prodottoRepository.findAllById(idProdotti);
            Pacchetto pacchetto = distributore.creaPacchetto(nome, prezzo, descrizione, prodotti);
            return pacchettoRepository.save(pacchetto);
        }

        public void eliminaProdotto(int idVenditore, int idProdotto) {
            Venditore venditore = venditoreRepository.findById(idVenditore)
                    .orElseThrow(() -> new RuntimeException("Venditore non trovato"));
            Prodotto prodotto = prodottoRepository.findById(idProdotto)
                    .orElseThrow(() -> new RuntimeException("Prodotto non trovato"));
            if (!venditore.getProdotti().contains(prodotto)) {
                throw new IllegalArgumentException("Il prodotto non appartiene al venditore");
            }
            venditore.getProdotti().remove(prodotto);
            prodottoRepository.delete(prodotto);
        }
}
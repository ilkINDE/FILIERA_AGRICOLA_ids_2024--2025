package unicam.filiera_agricola_ids_20242025.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import unicam.filiera_agricola_ids_20242025.models.Prodotti.Pacchetto;
import unicam.filiera_agricola_ids_20242025.models.Prodotti.Prodotto;
import unicam.filiera_agricola_ids_20242025.models.Venditori.Produttore;
import unicam.filiera_agricola_ids_20242025.services.HandlerVenditore;

import java.util.List;

@RestController
@RequestMapping("/venditori")
public class VenditoreController {

        private final HandlerVenditore handlerVenditore;

        @Autowired
        public VenditoreController(HandlerVenditore handlerVenditore) {
            this.handlerVenditore = handlerVenditore;
    }

    // Creazione prodotto da Produttore
    @PostMapping("/{idVenditore}/creaProdottoProduttore")
    public ResponseEntity<Prodotto> creaProdottoProduttore(
            @PathVariable int idVenditore,
            @RequestParam String nome,
            @RequestParam double prezzo,
            @RequestParam String descrizione,
            @RequestParam String metodoColtivazione) {
        Prodotto prodotto = handlerVenditore.creaProdottoProduttore(idVenditore, nome, prezzo, descrizione, metodoColtivazione);
        return ResponseEntity.ok(prodotto);
    }

    // Creazione prodotto da Trasformatore
    @PostMapping("{idVenditore}/creaProdottoTrasformatore")
    public ResponseEntity<Prodotto> creaProdottoTrasformatore(
            @PathVariable int idVenditore,
            @RequestParam String nome,
            @RequestParam double prezzo,
            @RequestParam String descrizione,
            @RequestParam String processoDiTrasformazione,
            @RequestBody List<Integer> idProduttoriAssociati) {

        // Recupero lista Produttori tramite handler
        List<Produttore> produttoriAssociati = handlerVenditore.getProduttoriById(idProduttoriAssociati);

        Prodotto prodotto = handlerVenditore.creaProdottoTrasformatore(
                idVenditore, nome, prezzo, descrizione, processoDiTrasformazione, produttoriAssociati);

        return ResponseEntity.ok(prodotto);
    }

    // Creazione prodotto da Distributore
    @PostMapping("{idVenditore}/creaProdottoDistributore")
    public ResponseEntity<Prodotto> creaProdottoDistributore(
            @PathVariable int idVenditore,
            @RequestParam String nome,
            @RequestParam double prezzo,
            @RequestParam String descrizione){
            Prodotto prodotto = handlerVenditore.creaProdottoDistributore(idVenditore, nome, prezzo, descrizione);
            return ResponseEntity.ok(prodotto);
    }

    // Creazione pacchetto
    @PostMapping("/{idVenditore}/creaPacchetto")
    public ResponseEntity<Pacchetto> creaPacchetto(
            @PathVariable int idVenditore,
            @RequestParam String nome,
            @RequestParam double prezzo,
            @RequestParam String descrizione,
            @RequestBody List<Integer> idProdotti) {
        Pacchetto pacchetto = handlerVenditore.creaPacchetto(idVenditore, nome, prezzo, descrizione, idProdotti);
        return ResponseEntity.ok(pacchetto);
    }

}

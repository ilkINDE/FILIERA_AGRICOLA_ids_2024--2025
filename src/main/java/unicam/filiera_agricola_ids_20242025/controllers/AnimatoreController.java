package unicam.filiera_agricola_ids_20242025.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import unicam.filiera_agricola_ids_20242025.models.Utenti.AnimatoreDellaFiliera.Invito;
import unicam.filiera_agricola_ids_20242025.services.HandlerAnimatore;
import unicam.filiera_agricola_ids_20242025.models.Utenti.AnimatoreDellaFiliera.Animatore;
import unicam.filiera_agricola_ids_20242025.models.Utenti.AnimatoreDellaFiliera.Evento;
import unicam.filiera_agricola_ids_20242025.repository.AnimatoreRepository;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/animatori")
public class AnimatoreController {
    private final AnimatoreRepository animatoreRepository;
    private final HandlerAnimatore handlerAnimatore;

    @Autowired
    public AnimatoreController(AnimatoreRepository animatoreRepository, HandlerAnimatore handlerAnimatore) {
        this.animatoreRepository = animatoreRepository;
        this.handlerAnimatore = handlerAnimatore;
    }

    @PostMapping("/{idAnimatore}/crea-evento")
    public ResponseEntity<Evento> creaEvento( @PathVariable int idAnimatore,
                                             @RequestParam String nomeEvento,
                                             @RequestParam String descrizioneEvento,
                                             @RequestParam String indirizzoEvento,
                                             @RequestParam LocalDate dataEvento,
                                             @RequestParam int maxPartecipanti) {

        Evento evento = handlerAnimatore.creaEvento(idAnimatore, nomeEvento, descrizioneEvento, indirizzoEvento, dataEvento, maxPartecipanti);

        return ResponseEntity.ok().body( evento );
    }


    @PutMapping("/{idAnimatore}/eventiCreati/{idEvento}/modifica")
    public ResponseEntity<String> modificaEvento(@PathVariable int idAnimatore,
                                                 @PathVariable int idEvento,
                                                 @RequestParam String nomeEvento,
                                                 @RequestParam String descrizioneEvento,
                                                 @RequestParam String indirizzoEvento,
                                                 @RequestParam LocalDate dataEvento,
                                                 @RequestParam int maxPartecipanti) {

        Evento evento = handlerAnimatore.modificaEvento(
                idAnimatore, idEvento, nomeEvento, descrizioneEvento, indirizzoEvento, dataEvento, maxPartecipanti);

        return ResponseEntity.ok("Evento modificato con successo.");
    }

    @PutMapping("/{idAnimatore}/eventiCreati/{idEvento}/caricaEvento")
    public ResponseEntity<String> caricaEvento(@PathVariable int idAnimatore, @PathVariable int idEvento) {
        handlerAnimatore.caricaEvento(idEvento, idAnimatore);
        return ResponseEntity.ok("Evento caricato sulla piattaforma.");
    }

    @GetMapping("/{idAnimatore}/eventiCreati")
    public ResponseEntity<List<Evento>> getEventiCreati(@PathVariable int idAnimatore) {
        Animatore animatore = animatoreRepository.findById(idAnimatore)
                .orElseThrow(() -> new RuntimeException("Animatore non trovato"));

        List<Evento> eventiCreati = animatore.getEventiCreati();

        return ResponseEntity.ok(eventiCreati);
    }

    @DeleteMapping("/{idAnimatore}/eventiCreati/{idEvento}/eliminaEvento")
    public ResponseEntity<String> eliminaEvento(@PathVariable int idEvento,
                                                @PathVariable int idAnimatore){

        handlerAnimatore.eliminaEvento(idEvento, idAnimatore);
        return ResponseEntity.ok().body(" evento eliminato. ");
    }

    @PostMapping("/{idAnimatore}/eventi/{idEvento}/invia-inviti")
    public ResponseEntity<String> inviaInviti(@PathVariable  int idAnimatore,
                                              @PathVariable int idEvento,
                                              @RequestBody List<Integer> idvenditori) {

        handlerAnimatore.inviaInviti(idAnimatore, idEvento, idvenditori);
        return ResponseEntity.ok().body("Inviti inviati con successo.");
    }


    @GetMapping("{idAnimatore}/eventi/{idEvento}/inviti-accettati")
    public ResponseEntity<List<Invito>> getInvitiAccettati(@PathVariable int idEvento, @PathVariable int idAnimatore) {
        List<Invito> invitiAccettati = handlerAnimatore.getInvitiAccettati(idEvento, idAnimatore);
        return ResponseEntity.ok(invitiAccettati);
    }

}
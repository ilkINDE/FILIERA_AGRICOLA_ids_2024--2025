package unicam.filiera_agricola_ids_20242025.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import unicam.filiera_agricola_ids_20242025.Services.HandlerAnimatore;
import unicam.filiera_agricola_ids_20242025.models.Animatore;
import unicam.filiera_agricola_ids_20242025.models.Evento;
import unicam.filiera_agricola_ids_20242025.repository.AnimatoreRepository;

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

    @PostMapping("/add")
    public ResponseEntity<Animatore> addAnimatore(@RequestBody Animatore animatore) {
        Animatore nuovoAnimatore = animatoreRepository.save(animatore);
        return ResponseEntity.ok(nuovoAnimatore);

    }

    @PostMapping("/{idAnimatore}/crea-evento")
    public ResponseEntity<String> creaEvento(@RequestBody Evento evento, @PathVariable int idAnimatore) {

        handlerAnimatore.creaEvento(
                idAnimatore,
                evento.getNome(),
                evento.getDescrizione(),
                evento.getIndirizzo(),
                evento.getData(),
                evento.getMaxPartecipanti());

        return ResponseEntity.ok().body( evento + " creato.");
    }


    @PutMapping("/{idAnimatore}/eventiCreati/{idEvento}/modifica")
    public ResponseEntity<String> modificaEvento(@PathVariable int idAnimatore, @PathVariable int idEvento, @RequestBody Evento eventoModificato) {

        handlerAnimatore.modificaEvento(
                idAnimatore,
                idEvento,
                eventoModificato.getNome(),
                eventoModificato.getDescrizione(),
                eventoModificato.getIndirizzo(),
                eventoModificato.getData(),
                eventoModificato.getMaxPartecipanti()
        );

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

    @DeleteMapping("/{idAnimatore}/eventiCreati/{idEvento}/eliminaEvento/")
    public ResponseEntity<String> eliminaEvento(@PathVariable int idEvento, @PathVariable int idAnimatore){

        handlerAnimatore.eliminaEvento(idEvento, idAnimatore);
        return ResponseEntity.ok().body(" evento eliminato. ");
    }

}
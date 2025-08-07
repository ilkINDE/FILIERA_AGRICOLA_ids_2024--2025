package unicam.filiera_agricola_ids_20242025.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import unicam.filiera_agricola_ids_20242025.Services.HandlerAnimatore;
import unicam.filiera_agricola_ids_20242025.models.Animatore;
import unicam.filiera_agricola_ids_20242025.models.Evento;
import unicam.filiera_agricola_ids_20242025.repository.AnimatoreRepository;

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

    @PostMapping("/crea-evento")
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

    @DeleteMapping("/eventiCreati/{idEvento}/eliminaEvento/")
    public ResponseEntity<String> eliminaEvento(@PathVariable int idEvento, @PathVariable int idAnimatore){

        handlerAnimatore.eliminaEvento(idEvento, idAnimatore);
        return ResponseEntity.ok().body(" evento eliminato. ");
    }

}
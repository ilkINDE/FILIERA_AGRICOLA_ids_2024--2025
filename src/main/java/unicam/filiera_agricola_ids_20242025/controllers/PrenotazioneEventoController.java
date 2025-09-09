package unicam.filiera_agricola_ids_20242025.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import unicam.filiera_agricola_ids_20242025.models.Evento;
import unicam.filiera_agricola_ids_20242025.models.PrenotazioneEvento;
import unicam.filiera_agricola_ids_20242025.services.HandlerPrenotazioneEvento;

import java.util.List;

@RestController
@RequestMapping("eventi")
public class PrenotazioneEventoController {

    private final HandlerPrenotazioneEvento handlerPrenotazioneEvento;

    @Autowired
    public PrenotazioneEventoController(HandlerPrenotazioneEvento handlerPrenotazioneEvento) {
        this.handlerPrenotazioneEvento = handlerPrenotazioneEvento;
    }

    @GetMapping("/disponibili")
    public List<Evento> getEventiDisponibili() {
        return handlerPrenotazioneEvento.getEventiDisponibili();
    }

    @PostMapping("/{idAcquirente}/prenota/{idEvento}")
    public PrenotazioneEvento prenotaEvento(@PathVariable int idAcquirente,
                                            @PathVariable int idEvento,
                                            @RequestParam int posti) {
        return handlerPrenotazioneEvento.prenotaEvento(idAcquirente, idEvento, posti);
    }
}

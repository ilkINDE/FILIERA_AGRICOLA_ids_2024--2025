package unicam.filiera_agricola_ids_20242025.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import unicam.filiera_agricola_ids_20242025.DTO.InvitoRequestDTO;
import unicam.filiera_agricola_ids_20242025.models.Evento;
import unicam.filiera_agricola_ids_20242025.models.Invito;
import unicam.filiera_agricola_ids_20242025.models.Utenti.Animatore;
import unicam.filiera_agricola_ids_20242025.models.Utenti.Venditori.Venditore;
import unicam.filiera_agricola_ids_20242025.repository.AnimatoreRepository;
import unicam.filiera_agricola_ids_20242025.repository.EventoRepository;
import unicam.filiera_agricola_ids_20242025.repository.InvitoRepository;
import unicam.filiera_agricola_ids_20242025.repository.VenditoreRepository;

import java.util.List;

@RestController
@RequestMapping("/inviti")
public class InvitoController {


    private AnimatoreRepository animatoreRepository;

    private VenditoreRepository venditoreRepository;

    private EventoRepository eventoRepository;

    private InvitoRepository invitoRepository;

    @Autowired
    public InvitoController(AnimatoreRepository animatoreRepository, VenditoreRepository venditoreRepository, EventoRepository eventoRepository, InvitoRepository invitoRepository) {
        this.animatoreRepository = animatoreRepository;
        this.venditoreRepository = venditoreRepository;
        this.eventoRepository = eventoRepository;
        this.invitoRepository = invitoRepository;
    }
     // L’animatore invita una lista di venditori a un evento
    @PostMapping("/invia")
    public String inviaInviti(@RequestBody InvitoRequestDTO request) {

        Animatore animatore = animatoreRepository.findById(request.getIdAnimatore())
                .orElseThrow(() -> new RuntimeException("Animatore non trovato"));

        Evento evento = eventoRepository.findById(request.getIdEvento())
                .orElseThrow(() -> new RuntimeException("Evento non trovato"));

        for (Integer idVenditore : request.getVenditoriIds()) {
            Venditore v = venditoreRepository.findById(idVenditore)
                    .orElseThrow(() -> new RuntimeException("Venditore non trovato"));
            animatore.aggiungiInvitato(v); // registrazione come observer

            // crea e salva l'invito
            Invito invito = new Invito(v, animatore, evento);
            invitoRepository.save(invito);
        }

        // notifica tutti i venditori selezionati
        animatore.notificaInvitati(evento);

        return "Inviti inviati ai venditori selezionati.";
    }


      // Il venditore accetta l’invito

    @PostMapping("/accetta")
    public String accettaInvito(@RequestParam int idVenditore, @RequestParam int idInvito) {
        Venditore v = venditoreRepository.findById(idVenditore)
                .orElseThrow(() -> new RuntimeException("Venditore non trovato"));

        v.accettaInvito(idInvito);
        venditoreRepository.save(v);

        return "Invito accettato dal venditore " + v.getNome();
    }


      // Il venditore rifiuta l’invito

    @PostMapping("/rifiuta")
    public String rifiutaInvito(@RequestParam int idVenditore, @RequestParam int idInvito) {
        Venditore v = venditoreRepository.findById(idVenditore)
                .orElseThrow(() -> new RuntimeException("Venditore non trovato"));

        v.rifiutaInvito(idInvito);
        venditoreRepository.save(v);

        return "Invito rifiutato dal venditore " + v.getNome();
    }

    //  Lista inviti ricevuti da un venditore
    @GetMapping("/ricevuti/{idVenditore}")
    public List<Invito> getInvitiRicevuti(@PathVariable int idVenditore) {

        return invitoRepository.findByVenditoreIdVenditoreAndStato(idVenditore, "IN_ATTESA");
    }
}

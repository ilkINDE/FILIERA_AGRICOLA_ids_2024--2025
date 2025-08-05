package unicam.filiera_agricola_ids_20242025.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import unicam.filiera_agricola_ids_20242025.models.Animatore;
import unicam.filiera_agricola_ids_20242025.repository.AnimatoreRepository;

@RestController
@RequestMapping("/animatore")
public class AnimatoreController {
    private final AnimatoreRepository animatoreRepository;

    @Autowired
    public AnimatoreController(AnimatoreRepository animatoreRepository) {
        this.animatoreRepository = animatoreRepository;
    }

    @PostMapping("/aggiungi")
    public ResponseEntity<Animatore> add(@RequestBody Animatore animatore) {
        animatoreRepository.save(animatore);
        return ResponseEntity.ok(animatore);
    }
}
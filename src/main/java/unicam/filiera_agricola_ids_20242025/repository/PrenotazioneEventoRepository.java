package unicam.filiera_agricola_ids_20242025.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import unicam.filiera_agricola_ids_20242025.models.PrenotazioneEvento;
import unicam.filiera_agricola_ids_20242025.models.Utenti.Acquirente;

import java.util.List;

public interface PrenotazioneEventoRepository extends JpaRepository<PrenotazioneEvento, Integer> {

    List<PrenotazioneEvento> findByAcquirente(Acquirente acquirente);
}

package unicam.filiera_agricola_ids_20242025.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import unicam.filiera_agricola_ids_20242025.models.Utenti.Acquirente.PrenotazioneEvento;
import unicam.filiera_agricola_ids_20242025.models.Utenti.Acquirente.Acquirente;

import java.util.List;

public interface PrenotazioneEventoRepository extends JpaRepository<PrenotazioneEvento, Integer> {

    List<PrenotazioneEvento> findByAcquirente(Acquirente acquirente);
}

package unicam.filiera_agricola_ids_20242025.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import unicam.filiera_agricola_ids_20242025.models.Utenti.Acquirente.Carrello;
import unicam.filiera_agricola_ids_20242025.models.Utenti.Acquirente.Acquirente;

import java.util.Optional;

public interface CarrelloRepository extends JpaRepository<Carrello, Integer> {

    Optional<Carrello> findByAcquirente(Acquirente acquirente);
}

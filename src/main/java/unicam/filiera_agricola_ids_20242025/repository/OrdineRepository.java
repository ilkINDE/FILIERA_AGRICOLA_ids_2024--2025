package unicam.filiera_agricola_ids_20242025.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import unicam.filiera_agricola_ids_20242025.models.Utenti.Acquirente.Ordine;

public interface OrdineRepository extends JpaRepository<Ordine, Integer> {
}

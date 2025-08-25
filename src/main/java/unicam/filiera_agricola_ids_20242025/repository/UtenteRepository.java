package unicam.filiera_agricola_ids_20242025.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import unicam.filiera_agricola_ids_20242025.models.Utenti.Utente;

public interface UtenteRepository extends JpaRepository<Utente, Integer> {
    Integer idUtente(int idUtente);
}

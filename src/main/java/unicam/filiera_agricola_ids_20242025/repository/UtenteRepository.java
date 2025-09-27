package unicam.filiera_agricola_ids_20242025.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import unicam.filiera_agricola_ids_20242025.models.Utenti.Utente;

import java.util.Optional;

public interface UtenteRepository extends JpaRepository<Utente, Integer> {
    Integer idUtente(int idUtente);
    Optional<Utente> findByEmail(String email);
}

package unicam.filiera_agricola_ids_20242025.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import unicam.filiera_agricola_ids_20242025.models.RichiestaRuolo;
import unicam.filiera_agricola_ids_20242025.models.Ruolo;
import unicam.filiera_agricola_ids_20242025.models.StatoRichiesta;
import unicam.filiera_agricola_ids_20242025.models.Utenti.Utente;

import java.util.Optional;

public interface RichiestaRuoloRepository extends JpaRepository<RichiestaRuolo,Integer> {

    //List<RichiestaRuolo> findByStatoRichiesta(StatoRichiesta stato);

    Optional<RichiestaRuolo> findByUtenteAndRuoloRichiestoAndStatoRichiesta(Utente utente, Ruolo ruolo, StatoRichiesta stato);
}

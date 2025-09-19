package unicam.filiera_agricola_ids_20242025.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import unicam.filiera_agricola_ids_20242025.models.Invito;

import java.util.List;

public interface InvitoRepository extends JpaRepository<Invito, Integer> {
    // Tutti gli inviti ricevuti da un venditore
    List<Invito> findByVenditoreIdVenditore(int idVenditore);

    List<Invito> findByVenditoreIdVenditoreAndStato(int idVenditore, String stato);
}

package unicam.filiera_agricola_ids_20242025.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import unicam.filiera_agricola_ids_20242025.models.State.StateInvito.StatoInvito;
import unicam.filiera_agricola_ids_20242025.models.Utenti.AnimatoreDellaFiliera.Invito;

import java.util.List;

public interface InvitoRepository extends JpaRepository<Invito, Integer> {
    // Tutti gli inviti ricevuti da un venditore
    List<Invito> findByVenditoreIdVenditore(int idVenditore);

    List<Invito> findByVenditoreIdVenditoreAndStatoInvito(int idVenditore, String stato);

    List<Invito> findByEvento_IdAndStatoInvito(int idEvento, StatoInvito statoInvito);
}

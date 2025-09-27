package unicam.filiera_agricola_ids_20242025.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import unicam.filiera_agricola_ids_20242025.models.Prodotti.Pacchetto;
import unicam.filiera_agricola_ids_20242025.models.State.StateProdotto.StatoProdotto;

import java.util.List;

public interface PacchettoRepository extends JpaRepository<Pacchetto, Integer> {
    List<Pacchetto> findByStatoProdotto(StatoProdotto statoProdotto);
}

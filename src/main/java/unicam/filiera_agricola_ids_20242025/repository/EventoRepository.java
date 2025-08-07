package unicam.filiera_agricola_ids_20242025.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import unicam.filiera_agricola_ids_20242025.models.Evento;
import java.util.Date;


public interface EventoRepository extends JpaRepository<Evento, Integer> {

    boolean existsByIndirizzoIgnoreCaseAndData(String indirizzo, Date data);

}

package unicam.filiera_agricola_ids_20242025.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import unicam.filiera_agricola_ids_20242025.models.Evento;
import java.util.Date;
import java.util.List;


public interface EventoRepository extends JpaRepository<Evento, Integer> {

    boolean existsByIndirizzoIgnoreCaseAndData(String indirizzo, Date data);

    @Query("SELECT e FROM Evento e WHERE LOWER(e.indirizzo) = LOWER(:indirizzo) AND e.data = :data AND e.id <> :id")
    List<Evento> findConflitti(@Param("indirizzo") String indirizzo, @Param("data") Date data, @Param("id") Integer id);

    List<Evento> findByCaricatoTrue();
}

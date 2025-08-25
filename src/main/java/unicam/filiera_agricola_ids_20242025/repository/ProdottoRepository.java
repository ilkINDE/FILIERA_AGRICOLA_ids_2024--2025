package unicam.filiera_agricola_ids_20242025.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import unicam.filiera_agricola_ids_20242025.models.Prodotti.Prodotto;

import java.util.List;

public interface ProdottoRepository extends JpaRepository<Prodotto, Integer> {
}

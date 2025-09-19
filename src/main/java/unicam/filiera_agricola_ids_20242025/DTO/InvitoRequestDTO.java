package unicam.filiera_agricola_ids_20242025.DTO;

import java.util.List;

public class InvitoRequestDTO {

    private int idAnimatore;
    private int idEvento;
    private List<Integer> venditoriIds;

    public int getIdAnimatore() {
        return idAnimatore;
    }

    public void setIdAnimatore(int idAnimatore) {
        this.idAnimatore = idAnimatore;
    }

    public int getIdEvento() {
        return idEvento;
    }

    public void setIdEvento(int idEvento) {
        this.idEvento = idEvento;
    }

    public List<Integer> getVenditoriIds() {
        return venditoriIds;
    }

    public void setVenditoriIds(List<Integer> venditoriIds) {
        this.venditoriIds = venditoriIds;
    }
}

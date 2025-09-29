package unicam.filiera_agricola_ids_20242025.models.MetodoDiPagamento;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.time.LocalDate;

@Entity
public class CartaDiCredito {

    @Id
    private long numeroCarta;

    private LocalDate dataScadenza;

    private String nomeIntestatario;

    private String cognomeIntestatario;

    private int cvv;

    public CartaDiCredito(long numeroCarta, LocalDate dataScadenza, String nomeIntestatario, String cognomeIntestatario,  int cvv) {
        this.numeroCarta = numeroCarta;
        this.dataScadenza = dataScadenza;
        this.nomeIntestatario = nomeIntestatario;
        this.cognomeIntestatario = cognomeIntestatario;
        this.cvv = cvv;
    }

    public CartaDiCredito() {}

    public long getNumeroCarta() {
        return numeroCarta;
    }

    public void setNumeroCarta(long numeroCarta) {
        this.numeroCarta = numeroCarta;
    }

    public LocalDate getDataScadenza() {
        return dataScadenza;
    }

    public void setDataScadenza(LocalDate dataScadenza) {
        this.dataScadenza = dataScadenza;
    }

    public String getNomeIntestatario() {
        return nomeIntestatario;
    }

    public void setNomeIntestatario(String nomeIntestatario) {
        this.nomeIntestatario = nomeIntestatario;
    }

    public String getCognomeIntestatario() {
        return cognomeIntestatario;
    }

    public void setCognomeIntestatario(String cognomeIntestatario) {
        this.cognomeIntestatario = cognomeIntestatario;
    }

    public int getCvv() {
        return cvv;
    }

    public void setCvv(int cvv) {
        this.cvv = cvv;
    }
}
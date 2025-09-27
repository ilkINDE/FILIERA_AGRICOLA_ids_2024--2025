package unicam.filiera_agricola_ids_20242025.models.State.StateProdotto;


public class RifiutatoState<T> implements ProdottoState<T> {

    @Override
    public void inviaInRevisione(T oggetto) {
        throw new IllegalStateException("Un oggetto rifiutato non può tornare in revisione");
    }

    @Override
    public void approva(T oggetto) {
        throw new IllegalStateException("Un oggetto rifiutato non può essere approvato direttamente");
    }

    @Override
    public void rifiuta(T oggetto) {
        throw new IllegalStateException("L'oggetto è già rifiutato");
    }

}

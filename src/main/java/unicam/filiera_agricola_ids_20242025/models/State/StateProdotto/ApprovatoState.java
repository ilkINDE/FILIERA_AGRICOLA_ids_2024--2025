package unicam.filiera_agricola_ids_20242025.models.State.StateProdotto;


public class ApprovatoState<T> implements ProdottoState<T> {


    @Override
    public void inviaInRevisione(T oggetto) {
        throw new IllegalStateException("Un oggetto approvato non può tornare in revisione");
    }

    @Override
    public void approva(T oggetto) {
        throw new IllegalStateException("L'oggetto è già approvato");
    }

    @Override
    public void rifiuta(T oggetto) {
        throw new IllegalStateException("Un oggetto approvato non può essere rifiutato");
    }

}

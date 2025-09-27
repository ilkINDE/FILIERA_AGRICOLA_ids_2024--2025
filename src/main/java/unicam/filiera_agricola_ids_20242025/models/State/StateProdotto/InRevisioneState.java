package unicam.filiera_agricola_ids_20242025.models.State.StateProdotto;

import unicam.filiera_agricola_ids_20242025.models.Prodotti.Pacchetto;
import unicam.filiera_agricola_ids_20242025.models.Prodotti.Prodotto;

public class InRevisioneState<T> implements ProdottoState<T> {

    @Override
    public void inviaInRevisione(T oggetto) {
        throw new IllegalStateException("L'oggetto è già in revisione");
    }


    @Override
    public void approva(T oggetto) {
        if (oggetto instanceof Prodotto prodotto) {
            prodotto.setStatoProdotto(StatoProdotto.APPROVATO);
            prodotto.setState(new ApprovatoState<>());
        } else if (oggetto instanceof Pacchetto pacchetto) {
            pacchetto.setStatoProdotto(StatoProdotto.APPROVATO);
            pacchetto.setState(new ApprovatoState<>());
        } else {
            throw new IllegalArgumentException("Tipo non supportato");
        }
    }

    @Override
    public void rifiuta(T oggetto) {
        if (oggetto instanceof Prodotto prodotto) {
            prodotto.setStatoProdotto(StatoProdotto.RIFIUTATO);
            prodotto.setState(new RifiutatoState<>());
        } else if (oggetto instanceof Pacchetto pacchetto) {
            pacchetto.setStatoProdotto(StatoProdotto.RIFIUTATO);
            pacchetto.setState(new RifiutatoState<>());
        } else {
            throw new IllegalArgumentException("Tipo non supportato");
        }
    }

}

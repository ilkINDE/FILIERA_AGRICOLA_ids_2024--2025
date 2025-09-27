package unicam.filiera_agricola_ids_20242025.models.State.StateProdotto;

import unicam.filiera_agricola_ids_20242025.models.Prodotti.Pacchetto;
import unicam.filiera_agricola_ids_20242025.models.Prodotti.Prodotto;

public class BozzaState<T> implements ProdottoState<T> {

    @Override
    public void inviaInRevisione(T oggetto) {
        if (oggetto instanceof Prodotto prodotto) {
            prodotto.setStatoProdotto(StatoProdotto.IN_REVISIONE);
            prodotto.setState(new InRevisioneState<>());
        } else if (oggetto instanceof Pacchetto pacchetto) {
            pacchetto.setStatoProdotto(StatoProdotto.IN_REVISIONE);
            pacchetto.setState(new InRevisioneState<>());
        } else {
            throw new IllegalArgumentException("Tipo non supportato");
        }
    }

    @Override
    public void approva(T oggetto) {
        throw new IllegalStateException("Non si può approvare direttamente da BOZZA");
    }

    @Override
    public void rifiuta(T oggetto) {
        throw new IllegalStateException("Non si può rifiutare direttamente da BOZZA");
    }

}

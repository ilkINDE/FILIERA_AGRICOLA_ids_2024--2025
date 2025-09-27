package unicam.filiera_agricola_ids_20242025.models.State.StateRichiesta;

import unicam.filiera_agricola_ids_20242025.models.RichiestaRuolo;

public class RifiutataState implements RichiestaState {


    @Override
    public void approva(RichiestaRuolo richiesta) {
        throw new IllegalStateException("Una richiesta rifiutata non può essere approvata");
    }

    @Override
    public void rifiuta(RichiestaRuolo richiesta) {
        throw new IllegalStateException("La richiesta è già rifiutata");
    }

}

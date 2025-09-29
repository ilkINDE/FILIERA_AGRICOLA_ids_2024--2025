package unicam.filiera_agricola_ids_20242025.models.State.StateRichiesta;

import unicam.filiera_agricola_ids_20242025.models.Utenti.Utente.RichiestaRuolo;

public class ApprovataState implements RichiestaState{


    @Override
    public void approva(RichiestaRuolo richiesta) {
        throw new IllegalStateException("La richiesta è già approvata");
    }

    @Override
    public void rifiuta(RichiestaRuolo richiesta) {
        throw new IllegalStateException("Una richiesta approvata non può essere rifiutata");
    }

}

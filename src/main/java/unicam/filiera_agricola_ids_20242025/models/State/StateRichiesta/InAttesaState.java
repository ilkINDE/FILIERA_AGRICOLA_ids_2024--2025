package unicam.filiera_agricola_ids_20242025.models.State.StateRichiesta;

import unicam.filiera_agricola_ids_20242025.models.Utenti.Utente.RichiestaRuolo;

public class InAttesaState implements RichiestaState{


    @Override
    public void approva(RichiestaRuolo richiesta) {
        richiesta.setStatoRichiesta(StatoRichiesta.APPROVATA);
        richiesta.setState(new ApprovataState());
    }

    @Override
    public void rifiuta(RichiestaRuolo richiesta) {
        richiesta.setStatoRichiesta(StatoRichiesta.RIFIUTATA);
        richiesta.setState(new RifiutataState());
    }

}

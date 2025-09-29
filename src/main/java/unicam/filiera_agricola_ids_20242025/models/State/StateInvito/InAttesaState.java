package unicam.filiera_agricola_ids_20242025.models.State.StateInvito;

import unicam.filiera_agricola_ids_20242025.models.Utenti.AnimatoreDellaFiliera.Invito;

public class InAttesaState implements InvitoState {

    @Override
    public void accetta(Invito invito) {
        invito.setStatoInvito(StatoInvito.ACCETTATO);
        invito.setState(new AccettatoState());
    }

    @Override
    public void rifiuta(Invito invito) {
        invito.setStatoInvito(StatoInvito.RIFIUTATO);
        invito.setState(new RifiutatoState());
    }

}

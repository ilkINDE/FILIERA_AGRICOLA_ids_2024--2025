package unicam.filiera_agricola_ids_20242025.models.State.StateInvito;

import unicam.filiera_agricola_ids_20242025.models.Invito;

public class AccettatoState implements InvitoState {


    @Override
    public void accetta(Invito invito) {
        throw new IllegalStateException("L'invito è già accettato");
    }

    @Override
    public void rifiuta(Invito invito) {
        throw new IllegalStateException("Un invito accettato non può essere rifiutato");
    }

}

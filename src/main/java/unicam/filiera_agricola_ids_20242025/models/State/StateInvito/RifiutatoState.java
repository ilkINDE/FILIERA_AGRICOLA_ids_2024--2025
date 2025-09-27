package unicam.filiera_agricola_ids_20242025.models.State.StateInvito;

import unicam.filiera_agricola_ids_20242025.models.Invito;

public class RifiutatoState implements InvitoState {

    @Override
    public void accetta(Invito invito) {
        throw new IllegalStateException("Un invito rifiutato non può essere accettato");
    }

    @Override
    public void rifiuta(Invito invito) {
        throw new IllegalStateException("L'invito è già rifiutato");
    }
}

package unicam.filiera_agricola_ids_20242025.models.Observer;

import unicam.filiera_agricola_ids_20242025.models.Utenti.AnimatoreDellaFiliera.Evento;
import unicam.filiera_agricola_ids_20242025.models.Utenti.AnimatoreDellaFiliera.Animatore;

public interface Invitato {
    void riceviInvito(Evento evento, Animatore animatore);
}

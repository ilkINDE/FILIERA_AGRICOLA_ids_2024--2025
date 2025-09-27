package unicam.filiera_agricola_ids_20242025.models.Observer;

import unicam.filiera_agricola_ids_20242025.models.Evento;
import unicam.filiera_agricola_ids_20242025.models.Utenti.Animatore;

public interface Invitato {
    void riceviInvito(Evento evento, Animatore animatore);
}

package unicam.filiera_agricola_ids_20242025.models.Observer;

import unicam.filiera_agricola_ids_20242025.models.Evento;
import unicam.filiera_agricola_ids_20242025.models.Utenti.Animatore;

public interface OrganizzatoreInviti {

    void aggiungiInvitato(Invitato i);
    void notificaInvitati(Evento evento, Animatore animatore);
}

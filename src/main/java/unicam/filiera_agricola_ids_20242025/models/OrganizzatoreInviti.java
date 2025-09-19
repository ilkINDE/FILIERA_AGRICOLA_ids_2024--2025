package unicam.filiera_agricola_ids_20242025.models;

public interface OrganizzatoreInviti {

    void aggiungiInvitato(Invitato i);
    void notificaInvitati(Evento evento);
}

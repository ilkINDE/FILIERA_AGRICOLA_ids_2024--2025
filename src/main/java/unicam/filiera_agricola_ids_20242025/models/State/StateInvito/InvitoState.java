package unicam.filiera_agricola_ids_20242025.models.State.StateInvito;

import unicam.filiera_agricola_ids_20242025.models.Invito;

/*
  design pattern STATE
  Interfaccia che definisce gli stati di un invito
  ogni stato deve implementare tutti i possibili comportamenti: accetta, rifiuta
  ogni stato gestisce il comportamento della richiesta in modo diverso
 */

public interface InvitoState {

    void accetta(Invito invito);
    void rifiuta(Invito invito);
}

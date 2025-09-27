package unicam.filiera_agricola_ids_20242025.models.State.StateRichiesta;

import unicam.filiera_agricola_ids_20242025.models.RichiestaRuolo;

/*
  design pattern STATE
  Interfaccia che definisce gli stati di una richiesta di un ruolo
  ogni stato deve implementare tutti i possibili comportamenti: approva, rifiuta
  ogni stato gestisce il comportamento della richiesta in modo diverso
 */
public interface RichiestaState {

    void approva(RichiestaRuolo richiesta);
    void rifiuta(RichiestaRuolo richiesta);

}

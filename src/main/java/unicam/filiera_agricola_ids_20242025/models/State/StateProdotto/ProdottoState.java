package unicam.filiera_agricola_ids_20242025.models.State.StateProdotto;


/*
  design pattern STATE
  Interfaccia che definisce gli stati di un oggetto (Prodotto o Pacchetto)
  ogni stato deve implementare tutti i possibili comportamenti: inviaInRevisione, approva, rifiuta
  ogni stato gestisce il comportamento dell'oggetto in modo diverso
 */
public interface ProdottoState<T> {
    void inviaInRevisione(T oggetto);
    void approva(T oggetto);
    void rifiuta(T oggetto);
}


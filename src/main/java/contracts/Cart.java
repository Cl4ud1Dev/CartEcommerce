package contracts;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

public class Cart implements CartUtils {
    //Generare getter setter e costruttore
    //Implements CartUtils
    private Map<SupermarketItemEnum, List<SuperMarketItem>> supermarketItemMap;
    public Cart() {
        this.supermarketItemMap = new EnumMap<SupermarketItemEnum, List<SuperMarketItem>>(SupermarketItemEnum.class);
    }
    //Stampare per tutte le tipologie gli item associati
    public void printCart(){
        for (SupermarketItemEnum tipologia : supermarketItemMap.keySet()) {
            printCartByType(tipologia );
        }
    }
    //Stampare la tipologia e poi tutti gli item associati
    public void printCartByType(SupermarketItemEnum type){
        System.out.println( " tipologia " + type  );
        List<SuperMarketItem> prodotti = supermarketItemMap.get(type);
        for (SuperMarketItem prodotto : prodotti){
            System.out.println( prodotto );
        }
    }
    //Calcolare il costo totale del carrello
    @Override
    public float calculateCartTotalCost() {
        float totCost = 0;
        for (SupermarketItemEnum tipologia : supermarketItemMap.keySet()) {
            List<SuperMarketItem> prodotti = supermarketItemMap.get(tipologia);
            for (SuperMarketItem prodotto : prodotti){
                totCost = totCost + (prodotto.getUnitCost() * prodotto.getQuantity());
            }
        } System.out.println("Totale " + totCost + "€");
        return totCost;
    }
    //Contare il numero di oggetti con il type in input
    @Override
    public int countSameTypeItem(SupermarketItemEnum supermarketItemEnum) {
        // inizializzo la variabile che sarà restituita
        int count = 0;
        // CHIEDO ( GET ) alla map la lista dei prodotti di quella tipologia
        List<SuperMarketItem> prodotti = supermarketItemMap.get(supermarketItemEnum);
        // se esistono dei prodotti
        if( prodotti != null )
            // scorro l'elenco dei prodotti e sommo le quantità per ognuno
            for (SuperMarketItem prodotto : prodotti) {
                count = count + prodotto.getQuantity();
            }
        return count;
    }
    //Rimuovere un  oggetto dal carrelo se la quantità passata è uguale o maggiore alla quantità attuale
    // altrimenti aggiornare solo la quantità
    @Override
    public Map<SupermarketItemEnum, List<SuperMarketItem>> removeItemByNameAndQty(SuperMarketItem item) {
        // mi faccio dare tutti i prodotti della categoria di appartenenza dell item ottenuto come parametro
        List<SuperMarketItem> prodotti = supermarketItemMap.get(item.getSupermarketItemEnum());
         if (prodotti !=null) {
             int index = prodotti.indexOf(item);
             if ( index != -1) {
                 // vuol dire se esiste un prodotto nel carrello
                 SuperMarketItem prodotto = prodotti.get(index);
                 // se prodotto e maggiore della quantita iniziale di prodotti
                 if (prodotto.getQuantity() > item.getQuantity()) {
                     // se la quantita` del carrello e maggiore e va di quella da eliminare riduco la quantita nel carrello
                     prodotto.setQuantity(prodotto.getQuantity() - item.getQuantity());
                 } else { // altrimenti lo rimuovo
                     prodotti.remove(prodotto);
                 }
             }
         }
        return supermarketItemMap;
    }
    //Aggiungere un nuovo oggetto nel carrelo se non esiste altrimenti aggiornare solo la quantità
    @Override
    public Map<SupermarketItemEnum, List<SuperMarketItem>> addOrUpdateItemByNameAndQty(SuperMarketItem item) {
        List<SuperMarketItem> prodotti = supermarketItemMap.get(item.getSupermarketItemEnum());
        // il carrelol esiste?
        if (prodotti == null) {
            // creo un carrello
            prodotti = new ArrayList<>();
            // aggiungo prodotti
            supermarketItemMap.put(item.getSupermarketItemEnum(),prodotti);
        } // verifico se ci sono prodotti
            int index = prodotti.indexOf(item);
            if ( index != -1) {
                SuperMarketItem prodotto = prodotti.get(index);
                //aggiorna la quantita`
                prodotto.setQuantity(prodotto.getQuantity() + item.getQuantity());
            }else {
                prodotti.add(item);
            }
            return supermarketItemMap;
    }
}

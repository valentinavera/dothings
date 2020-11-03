package edu.unicauca.main.patterns.observer;

import java.util.ArrayList;


 public abstract class Observed {
    private ArrayList<Observer> observers=new ArrayList<Observer>();
    /**
     * Notifica a todos sus observadores mandandoles una referencia de si mismo
     * y un codigo que identifica el cambio en el observado.
     *
     */
    public void notify_observers(){
        this.notify_observers(this);
    }
    /**
     * Notifica a todos sus observadores mandandoles una referencia de un objeto observable
     * y un codigo que identifica que cambio en  el observado.
     *
     */

    public void notify_observers(Observed observed){
        for(Observer o: observers){
            o.notify(observed);
        }
    }
    /**
     * Anade un observador, que sera notificado cuando  el observado cambie.
     *
     */
    public void addObserver(Observer o){
        if(!isObserver(o))
            observers.add(o);
    }
    /**
     * Determina si un objeto es observador del objeto observado.
     *
     */
    public boolean isObserver(Observer o){
        if(o == null)
            return false;
        return observers.contains(o);
    }
    /**
     * Elimina el observador o, que no volvera a ser notificado cuando cambie el observado
     */
    public void delObserver(Observer o){
        observers.remove(o);
    }
    public ArrayList<Observer> getObservers(){
        return observers;
    }


}

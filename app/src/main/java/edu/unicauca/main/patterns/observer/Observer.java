package edu.unicauca.main.patterns.observer;


public interface Observer {
    /**
     * Este metodo es llamado cuando un observado cambia,
     *  entonces el observador obtiene la informacion que quiere del observado.
     */
    public void notify(Observed observed);
}

package entities;

public class EntitieGeneric<T> {
    private T entitie;

    public EntitieGeneric(T entitie) {
        this.entitie = entitie;
    }

    public T getEntitie() {
        return entitie;
    }

    public void setEntitie(T entitie) {
        this.entitie = entitie;
    }
    
    
}

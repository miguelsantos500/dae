package entities;

public class EntitieGeneric<T> {
    private T entitie;

    public EntitieGeneric(T entitie) {
        this.entitie = entitie;
    }

    public T getEntity() {
        return entitie;
    }

    public void setEntitie(T entitie) {
        this.entitie = entitie;
    }
    
    
}

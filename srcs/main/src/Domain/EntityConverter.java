package Domain;

public abstract class EntityConverter<T extends Entity> {
    public abstract String toString(T entity);

    public abstract T fromString(String string);
}

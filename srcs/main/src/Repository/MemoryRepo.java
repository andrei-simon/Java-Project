package Repository;

import Domain.Entity;

import java.util.ArrayList;
import java.util.Collection;


public class MemoryRepo<T extends Entity> implements Iterable<T> {
    protected Collection<T> entities = new ArrayList<>();

    public void add(T item) throws RepoException {
        if (entities.contains(item)) {
            throw new RepoException("Entitatea deja exista");
        }
        for(T entity : entities) {
            if (entity.getId() == item.getId()) {
                throw new RepoException("Entitatea cu acest id deja exista");
            }
        }
        entities.add(item);
    }

    public void remove(int id) throws RepoException {
        if(entities.isEmpty()) {
            throw new RepoException("Nicio entitate in repository");
        }
        for (T entity : entities) {
            if (entity.getId() == id) {
                entities.remove(entity);
                return;
            }
        }
        throw new RepoException("Entitatea cu acest id nu exista");
    }

    public Collection<T> getAll() throws RepoException {
        if(entities.isEmpty()) {
            throw new RepoException("Nicio entitate in repository");
        }
        return entities;
    }

    public T get(int id) throws RepoException {
        if(entities.isEmpty()) {
            throw new RepoException("Nicio entitate in repository");
        }
        for (T entity : entities) {
            if (entity.getId() == id) {
                return entity;
            }
        }
        throw new RepoException("Entitatea cu acest id nu exista");
    }

    public void update(T item) throws RepoException {
        if(entities.isEmpty()) {
            throw new RepoException("Nicio entitate in repository");
        }
        for (T entity : entities) {
            if (entity.getId() == item.getId()) {
                entities.remove(entity);
                entities.add(item);
                return;
            }
        }
        throw new RepoException("Entitatea cu acest id nu exista");
    }

    public java.util.Iterator<T> iterator() {
        return entities.iterator();
    }
}

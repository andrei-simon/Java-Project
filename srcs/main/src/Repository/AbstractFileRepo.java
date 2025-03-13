package Repository;

import Domain.Entity;

import java.util.Collection;

public abstract class AbstractFileRepo<T extends Entity> extends MemoryRepo<T> {
    protected String file;

    public AbstractFileRepo(String file) {
        this.file = file;
    }

    public void add(T item) throws RepoException{
        super.add(item);
        saveFile();
    }

    public void remove(int id) throws RepoException {
        super.remove(id);
        saveFile();
    }

    public void update(T item) throws RepoException {
        super.update(item);
        saveFile();
    }

    public Collection<T> getAll() throws RepoException {
        return super.getAll();
    }

    public T get(int id) throws RepoException {
        return super.get(id);
    }


    protected abstract void saveFile() throws RepoException;

    protected abstract void loadFile() throws RepoException;
}

package Repository;

import Domain.Entity;

import java.io.*;
import java.util.Collection;


public class BinaryFileRepo<T extends Entity> extends AbstractFileRepo<T> {
    public BinaryFileRepo(String file) {
        super(file);
        try {
            loadFile();
        } catch (RepoException e) {
            throw new RuntimeException(e);
        }
    }

    protected void saveFile() throws RepoException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file))) {
            oos.writeObject(entities);
        } catch (IOException e) {
            throw new RepoException(e.getMessage(), e);
        }
    }

    protected void loadFile() throws RepoException {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))){
            this.entities = (Collection<T>) ois.readObject();
        } catch (FileNotFoundException e) {
            System.out.println("Fisierul a fost initializat");
        } catch (IOException | ClassNotFoundException e) {
            throw new RepoException("Eroare la incarcarea fisierului", e);
        }
    }

    public void add(T item) throws RepoException {
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

}

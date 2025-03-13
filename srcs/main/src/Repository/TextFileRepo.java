package Repository;

import Domain.Entity;
import Domain.EntityConverter;

import java.io.*;
import java.util.Collection;

public class TextFileRepo<T extends Entity> extends AbstractFileRepo<T> {
    protected EntityConverter<T> converter;

    public TextFileRepo(String file, EntityConverter<T> converter) {
        super(file);
        this.converter = converter;
        try {
            loadFile();
        } catch (RepoException e) {
            throw new RuntimeException(e);
        }
    }

    protected void saveFile() throws RepoException {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(this.file))) {
            for (var element : this.entities) {
                String asString = converter.toString((T) element);
                bw.write(asString);
                bw.newLine();
            }
        } catch (IOException e) {
            throw new RepoException("Eroare la scrierea fisierului", e);
        }
    }

    protected void loadFile() throws RepoException {
        try (BufferedReader br = new BufferedReader(new FileReader(this.file))) {
            String line = br.readLine();
            while (line != null) {
                entities.add(converter.fromString(line));
                line = br.readLine();
            }
        } catch (IOException e) {
            throw new RepoException("Eroare la citirea fisierului", e);
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

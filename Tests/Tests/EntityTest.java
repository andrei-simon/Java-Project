package Tests;
import Domain.Entity;
import Domain.Patient;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
class EntityTest {
    Patient entity = new Patient(1, "Popescu", "Ion", 23);
    @Test
    void getId() {
        assertEquals(1, entity.getId());
    }
}
package server.entity.habit.transport;

import org.junit.jupiter.api.Test;
import server.exception.habit.transport.TransportTypeNotFoundException;

import static org.junit.jupiter.api.Assertions.*;

class TransportTypeTest {

    @Test
    void fromString() {
        boolean thrown = false;

        try {
            TransportType.fromString("Not a transport type");
        } catch (TransportTypeNotFoundException e) {
            thrown = true;
        }

        assertTrue(thrown);
    }
}
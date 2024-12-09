package unit.repository;

import model.DiagnosticResult;
import org.junit.jupiter.api.Test;
import repository.DiagnosticRepository;
import static org.junit.jupiter.api.Assertions.*;
import java.util.List;

class DiagnosticRepositoryTest {

    @Test
    void testAddANDGetReportsForTicketId() {
        // Przygotowanie
        DiagnosticRepository repository = new DiagnosticRepository();
        DiagnosticResult wynik = new DiagnosticResult(1, "tech123", "Wymiana baterii", true);

        // Działanie
        repository.addReportsForTicketId(1, List.of(wynik));
        List<DiagnosticResult> raport = repository.getReportsForTicketId(1);

        // Sprawdzenie
        assertEquals("tech123", raport.get(0).getTechnicianId());
    }

    @Test
    void testHasReportsForTicketId() {
        // Przygotowanie
        DiagnosticRepository repository = new DiagnosticRepository();
        DiagnosticResult wynik = new DiagnosticResult(5, "tech456", "Naprawa ekranu", false);
        repository.addReportsForTicketId(5, List.of(wynik));

        // Działanie
        boolean maRaporty = repository.hasReportsForTicketId(5);
        boolean nieMaRaportów = repository.hasReportsForTicketId(4); // Ticket ID 4 nie istnieje

        // Sprawdzenie
        assertTrue(maRaporty);
        assertFalse(nieMaRaportów);
    }
}
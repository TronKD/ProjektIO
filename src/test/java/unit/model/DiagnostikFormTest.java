package unit.model;

import model.DiagnosticResult;
import model.DiagnostikForm;
import repository.DiagnosticRepository;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

public class DiagnostikFormTest {

    @Test
    public void testRozpocznijFormularzDiagnostycznyZIstniejącymiRaportami() {
        // Przygotowanie
        DiagnosticRepository repository = new DiagnosticRepository();
        DiagnostikForm diagnostikForm = new DiagnostikForm(repository);

        // Działanie
        // Symulacja wywołania metody startDiagnosticForm z ticketId, dla którego istnieją raporty
        diagnostikForm.startDiagnosticForm(1);

        // Sprawdzenie
        // Sprawdzamy, czy nie dodano nowych raportów
        List<DiagnosticResult> raporty = repository.getReportsForTicketId(1);
        assertEquals(1, raporty.size()); // Powinno być tylko 1 raport
    }

    @Test
    public void testWalidacjaIDTechnika() {
        // Przygotowanie
        DiagnostikForm diagnostikForm = new DiagnostikForm(new DiagnosticRepository());

        // Działanie i Sprawdzenie
        assertTrue(diagnostikForm.validateTechnicianId("tech123")); // Przykładowe poprawne ID
        assertFalse(diagnostikForm.validateTechnicianId("")); // Puste ID
    }
}
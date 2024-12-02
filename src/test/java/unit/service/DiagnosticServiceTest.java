package unit.service;

import model.DiagnosticResult;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import service.DiagnosticService;
import com.github.javafaker.Faker;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

class DiagnosticServiceTest {

    private DiagnosticService diagnosticService;
    private Faker faker;
    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();

    @BeforeEach
    public void setup() {
        diagnosticService = new DiagnosticService();
        faker = new Faker();
        System.setOut(new PrintStream(outputStreamCaptor)); // Przechwytywanie wyjścia do konsoli
    }

    @Test
    public void testGetDiagnosticResult() {
        // Generowanie losowych danych za pomocą Faker
        int ticketId = Integer.parseInt(faker.idNumber().valid());
        String technicianId = faker.idNumber().valid();
        String result = faker.lorem().sentence();

        // Dodajemy wynik diagnostyki
        diagnosticService.addDiagnosticResult(ticketId, technicianId, result, true);

        // Teraz pobieramy wynik diagnostyki
        DiagnosticResult diagnosticResult = diagnosticService.getDiagnosticResult(ticketId);

        // Sprawdzamy, czy wynik nie jest pusty i czy zawiera odpowiednie wartości
        assertNotNull(diagnosticResult);
        assertEquals(ticketId, diagnosticResult.getTicketId());
        assertEquals(technicianId, diagnosticResult.getTechnicianId());
        assertEquals(result, diagnosticResult.getNotes());
        assertTrue(diagnosticResult.isComplete());
    }

    @Test
    public void testGetDiagnosticResult_NotFound() {

        int ticketId = Integer.parseInt(faker.idNumber().valid());

        DiagnosticResult diagnosticResult = diagnosticService.getDiagnosticResult(ticketId);

        assertNull(diagnosticResult);
    }

    @Test
    public void testDisplayCompletedReports() {
        // Dodajmy kilka wyników diagnostyki
        String ticketId1 = faker.idNumber().valid();
        String ticketId2 = faker.idNumber().valid();
        String technicianId1 = faker.idNumber().valid();
        String technicianId2 = faker.idNumber().valid();
        String result1 = faker.lorem().sentence();
        String result2 = faker.lorem().sentence();

        diagnosticService.addDiagnosticResult(Integer.parseInt(ticketId1), technicianId1, result1, true);
        diagnosticService.addDiagnosticResult(Integer.parseInt(ticketId2), technicianId2, result2, false);

        // Testujemy metodę displayCompletedReports
        diagnosticService.displayCompletedReports();

        // Sprawdzamy, czy wyświetlono poprawny raport
        String output = outputStreamCaptor.toString().trim();
        assertTrue(output.contains(ticketId1));  // Tylko ticketId1 powinno być wyświetlone
        assertFalse(output.contains(ticketId2)); // TicketId2 nie jest zakończony, więc nie powinno być wyświetlone
    }

    @Test
    public void testDisplayCompletedReportsSummary() {
        // Dodajmy kilka wyników diagnostyki
        diagnosticService.addDiagnosticResult(Integer.parseInt(faker.idNumber().valid()), faker.idNumber().valid(), faker.lorem().sentence(), true);
        diagnosticService.addDiagnosticResult(Integer.parseInt(faker.idNumber().valid()), faker.idNumber().valid(), faker.lorem().sentence(), false);
        diagnosticService.addDiagnosticResult(Integer.parseInt(faker.idNumber().valid()), faker.idNumber().valid(), faker.lorem().sentence(), true);

        // Testujemy metodę displayCompletedReportsSummary
        diagnosticService.displayCompletedReportsSummary();

        // Sprawdzamy, czy wyświetlono poprawną liczbę zakończonych raportów
        String output = outputStreamCaptor.toString();
        assertTrue(output.contains("Number of completed diagnostic reports: 2"));
    }

    @Test
    public void testFindReportByTicketId() {
        // Dodajemy wyniki diagnostyki
        String ticketId = faker.idNumber().valid();
        String technicianId = faker.idNumber().valid();
        String result = faker.lorem().sentence();
        diagnosticService.addDiagnosticResult(Integer.parseInt(ticketId), technicianId, result, true);

        // Testujemy metodę findReportByTicketId
        diagnosticService.findReportByTicketId(Integer.parseInt(ticketId));

        // Sprawdzamy, czy wyświetlono odpowiedni raport
        String output = outputStreamCaptor.toString();
        assertTrue(output.contains(ticketId));
        assertTrue(output.contains(technicianId));
        assertTrue(output.contains(result));
    }
}

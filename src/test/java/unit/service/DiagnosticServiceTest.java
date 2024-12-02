package unit.service;

import model.DiagnosticResult;
import org.junit.jupiter.api.AfterEach;
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
    private final PrintStream originalOut = System.out;

    @BeforeEach
    public void setup() {
        diagnosticService = new DiagnosticService();
        faker = new Faker();
        System.setOut(new PrintStream(outputStreamCaptor)); // Przechwytywanie wyjścia do konsoli
    }

    @AfterEach
    public void tearDown() {
        System.setOut(originalOut); // Przywrócenie oryginalnego strumienia wyjściowego
    }

    @Test
    public void testGetDiagnosticResult() {
        // Generowanie losowych danych za pomocą Faker
        int ticketId = faker.number().randomDigitNotZero();
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
        int ticketId = faker.number().randomDigitNotZero();
        DiagnosticResult diagnosticResult = diagnosticService.getDiagnosticResult(ticketId);
        assertNull(diagnosticResult);
    }

    @Test
    public void testDisplayCompletedReports() {
        int ticketId1 = faker.number().randomDigitNotZero();
        int ticketId2 = faker.number().randomDigitNotZero();
        String technicianId1 = faker.idNumber().valid();
        String technicianId2 = faker.idNumber().valid();
        String result1 = faker.lorem().sentence();
        String result2 = faker.lorem().sentence();

        // Dodajemy wyniki diagnostyki
        diagnosticService.addDiagnosticResult(ticketId1, technicianId1, result1, true);
        diagnosticService.addDiagnosticResult(ticketId2, technicianId2, result2, false);

        // Testujemy metodę displayCompletedReports
        diagnosticService.displayCompletedReports();

        // Sprawdzamy, czy wyświetlono poprawny raport
        String output = outputStreamCaptor.toString().trim();
        assertTrue(output.contains(String.valueOf(ticketId1)), "Oczekiwany wynik powinien zawierać ticketId1");  // Tylko ticketId1 powinno być wyświetlone
        assertFalse(output.contains(String.valueOf(ticketId2)), "Oczekiwane wynik nie powinien zawierać ticketId2"); // TicketId2 nie jest zakończony, więc nie powinno być wyświetlone
    }

    @Test
    public void testFindReportByTicketId() {
        // Dodajemy wyniki diagnostyki
        int ticketId = faker.number().randomDigitNotZero();
        String technicianId = faker.idNumber().valid();
        String result = faker.lorem().sentence();

        diagnosticService.addDiagnosticResult(ticketId, technicianId, result, true);

        // Testujemy metodę findReportByTicketId
        diagnosticService.findReportByTicketId(ticketId);

        // Sprawdzamy, czy wyświetlono odpowiedni raport
        String output = outputStreamCaptor.toString();
        assertTrue(output.contains(String.valueOf(ticketId)));
        assertTrue(output.contains(technicianId));
        assertTrue(output.contains(result));
    }
}
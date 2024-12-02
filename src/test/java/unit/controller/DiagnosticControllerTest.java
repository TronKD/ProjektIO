package unit.controller;

import controller.DiagnosticController;
import model.DiagnosticResult;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import service.DiagnosticService;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class DiagnosticControllerTest {

    private DiagnosticService diagnosticService;
    private DiagnosticController diagnosticController;

    @BeforeEach
    void setup() {
        diagnosticService = mock(DiagnosticService.class);
        diagnosticController = new DiagnosticController(diagnosticService);
    }

    @Test
    void testAddDiagnosticResult() {
        // Test dla metody dodającej wynik diagnostyki
        diagnosticController.addDiagnosticResult(01, "TECH-123", "No issues", true);
        verify(diagnosticService, times(1)).addDiagnosticResult(01, "TECH-123", "No issues", true);
    }

    @Test
    void testGetDiagnosticResult() {
        // Mockowanie wyniku diagnostyki
        DiagnosticResult mockResult = new DiagnosticResult(02, "TECH-124", "Issue found", false);
        when(diagnosticService.getDiagnosticResult(02)).thenReturn(mockResult);

        // Wywołanie metody kontrolera
        DiagnosticResult result = diagnosticController.getDiagnosticResult(02);

        // Assercje
        assertNotNull(result);
        assertEquals("02", result.getTicketId());
        assertEquals("TECH-124", result.getTechnicianId());
        assertEquals("Issue found", result.getNotes());
        assertFalse(result.isComplete());

        // Weryfikacja, że serwis został wywołany
        verify(diagnosticService, times(1)).getDiagnosticResult(02);
    }

    @Test
    void testUpdateDiagnosticResult() {
        // Test dla aktualizacji wyniku diagnostyki
        diagnosticController.updateDiagnosticResult(03, "TECH-125", "Updated notes", true);
        verify(diagnosticService, times(1)).updateDiagnosticResult(03, "TECH-125", "Updated notes", true);
    }

    @Test
    void testDeleteDiagnosticResult() {
        // Test dla usunięcia wyniku diagnostyki
        diagnosticController.deleteDiagnosticResult(04);
        verify(diagnosticService, times(1)).deleteDiagnosticResult(04);
    }

    @Test
    void testUpdateDiagnosticResult_ValidationFailure() {
        try {
            // Próbujemy wykonać metodę z pustym ticketId, co powinno rzucić wyjątek
            diagnosticController.updateDiagnosticResult(0, "TECH-125", "Updated notes", true);

            // Jeśli wyjątek nie zostanie rzucony, test nie powinien przejść
            fail("Expected IllegalArgumentException was not thrown");
        } catch (IllegalArgumentException exception) {
            // Sprawdzamy, czy wyjątek jest zgodny z oczekiwanym
            assertEquals("Ticket ID cannot be empty", exception.getMessage());
        }
    }
}

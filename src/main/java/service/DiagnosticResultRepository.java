package service;

import model.DiagnosticResult;
import java.util.HashMap;
import java.util.Map;

public class DiagnosticResultRepository {

    private Map<Integer, DiagnosticResult> diagnosticResults;

    public DiagnosticResultRepository() {
        this.diagnosticResults = new HashMap<>();
    }

    // Dodawanie wyniku diagnostycznego
    public void addDiagnosticResult(DiagnosticResult diagnosticResult) {
        diagnosticResults.put(diagnosticResult.getTicketId(), diagnosticResult);
    }

    // Pobieranie wyniku diagnostycznego
    public DiagnosticResult getDiagnosticResult(int ticketId) {
        return diagnosticResults.get(ticketId);
    }

    // Usuwanie wyniku diagnostycznego
    public void deleteDiagnosticResult(int ticketId) {
        diagnosticResults.remove(ticketId);
    }

    // Pobieranie wszystkich wyników diagnostycznych
    public Map<Integer, DiagnosticResult> getAllDiagnosticResults() {
        return diagnosticResults;
    }
}

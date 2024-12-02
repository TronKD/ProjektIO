package service;

import model.DiagnosticResult;

public class DiagnosticResultService {

    private DiagnosticResultRepository diagnosticResultRepository;

    public DiagnosticResultService(DiagnosticResultRepository diagnosticResultRepository) {
        this.diagnosticResultRepository = diagnosticResultRepository;
    }

    // Aktualizacja wyniku diagnostycznego
    public void updateDiagnosticResult(int ticketId, String technicianId, String result, boolean isComplete) {
        if (ticketId == 0) {
            throw new IllegalArgumentException("Ticket ID nie może być pusty");
        }

        DiagnosticResult existingResult = diagnosticResultRepository.getDiagnosticResult(ticketId);
        if (existingResult == null) {
            throw new IllegalArgumentException("Nie znaleziono wyników diagnostyki dla numeru: " + ticketId);
        }

        existingResult.setTechnicianId(technicianId);
        existingResult.setNotes(result);
        existingResult.setComplete(isComplete);
    }
}

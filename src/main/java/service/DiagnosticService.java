package service;

import model.DiagnosticResult;
import repository.DiagnosticResultRepository;

public class DiagnosticService {

    private DiagnosticResultRepository diagnosticResultRepository;
    private DiagnosticResultService diagnosticResultService;
    private DiagnosticReportPrinter diagnosticReportPrinter;

    public DiagnosticService() {
        this.diagnosticResultRepository = new DiagnosticResultRepository();
        this.diagnosticResultService = new DiagnosticResultService(diagnosticResultRepository);
        this.diagnosticReportPrinter = new DiagnosticReportPrinter();
    }

    public void addDiagnosticResult(int ticketId, String technicianId, String result, boolean isComplete) {
        DiagnosticResult diagnosticResult = new DiagnosticResult(ticketId, technicianId, result, isComplete);
        diagnosticResultRepository.addDiagnosticResult(diagnosticResult);
    }

    // Pobieranie wyniku diagnostycznego
    public DiagnosticResult getDiagnosticResult(int ticketId) {
        return diagnosticResultRepository.getDiagnosticResult(ticketId);
    }

    public void deleteDiagnosticResult(int ticketId) {
        diagnosticResultRepository.deleteDiagnosticResult(ticketId);
    }

    // Aktualizacja wyniku diagnostycznego
    public void updateDiagnosticResult(int ticketId, String technicianId, String result, boolean isComplete) {
        diagnosticResultService.updateDiagnosticResult(ticketId, technicianId, result, isComplete);
    }

    // Wyświetlanie zakończonych raportów diagnostycznych
    public void displayCompletedReports() {
        diagnosticReportPrinter.displayCompletedReports(diagnosticResultRepository.getAllDiagnosticResults());
    }

    // Podsumowanie zakończonych raportów
    public void displayCompletedReportsSummary() {
        diagnosticReportPrinter.displayCompletedReportsSummary(diagnosticResultRepository.getAllDiagnosticResults());
    }

    // Wyszukiwanie raportu po ticketId
    public void findReportByTicketId(int ticketId) {
        diagnosticReportPrinter.findReportByTicketId(diagnosticResultRepository.getAllDiagnosticResults(), ticketId);
    }
}

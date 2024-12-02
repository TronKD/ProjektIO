package service;

import model.DiagnosticResult;

import java.util.Map;
import java.util.stream.Collectors;

public class DiagnosticReportPrinter {

    // Wyświetlanie zakończonych raportów diagnostycznych
    public void displayCompletedReports(Map<Integer, DiagnosticResult> diagnosticResults) {
        diagnosticResults.values().stream()
                .filter(DiagnosticResult::isComplete)
                .sorted((r1, r2) -> Integer.compare(r1.getTicketId(), r2.getTicketId()))
                .forEach(report -> {
                    System.out.println("ID zgłoszenia: " + report.getTicketId());
                    System.out.println("ID technika: " + report.getTechnicianId());
                    System.out.println("Wyniki diagnostyki: " + report.getNotes());
                    System.out.println("Zakończono: Tak\n");
                });
    }

    // Podsumowanie liczby zakończonych raportów
    public void displayCompletedReportsSummary(Map<Integer, DiagnosticResult> diagnosticResults) {
        long completedReportsCount = diagnosticResults.values().stream()
                .filter(DiagnosticResult::isComplete)
                .count();
        System.out.println("Liczba zakończonych raportów diagnostycznych: " + completedReportsCount);
    }

    // Wyszukiwanie raportu po ticketId
    public void findReportByTicketId(Map<Integer, DiagnosticResult> diagnosticResults, int ticketId) {
        diagnosticResults.values().stream()
                .filter(report -> report.getTicketId() == ticketId)
                .findFirst()
                .ifPresentOrElse(
                        report -> {
                            System.out.println("Znaleziony raport:");
                            System.out.println("ID zgłoszenia: " + report.getTicketId());
                            System.out.println("ID technika: " + report.getTechnicianId());
                            System.out.println("Wyniki diagnostyki: " + report.getNotes());
                            System.out.println("Zakończono: " + (report.isComplete() ? "Tak" : "Nie"));
                        },
                        () -> System.out.println("Nie znaleziono raportu dla ID zgłoszenia: " + ticketId)
                );
    }
}

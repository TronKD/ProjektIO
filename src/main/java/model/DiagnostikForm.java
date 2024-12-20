package model;

import com.github.javafaker.Faker;
import repository.DiagnosticRepository;

import java.util.List;
import java.util.Scanner;
import java.util.Locale;

public class DiagnostikForm {

    private final DiagnosticRepository diagnosticRepository;
    private final Faker faker = new Faker(new Locale("pl", "PL"));

    public DiagnostikForm(DiagnosticRepository diagnosticRepository) {
        this.diagnosticRepository = diagnosticRepository;
    }

    public void startDiagnosticForm(int ticketId) {
        Scanner scanner = new Scanner(System.in);

        // Sprawdzanie, czy istnieją już raporty dla danego ticketId
        if (diagnosticRepository.hasReportsForTicketId(ticketId)) {
            System.out.println("Dla tego ID zgłoszenia już istnieją zapisane raporty.");
            System.out.println("Zakończono formularz diagnostyczny.");
            return; // Zakończenie formularza, jeśli raporty już istnieją
        }

        System.out.println("Brak zapisanych raportów dla tego ID zgłoszenia.");
        List<DiagnosticResult> generatedReports = SimpleDiagnosticDataGenerator.generateSampleDataForTicketId(ticketId, 0);

        // Zbieranie danych użytkownika
        System.out.println("\n=== Formularz Diagnostyki Serwisowej ===");
        System.out.println("ID zgłoszenia: " + ticketId);

        // Generowanie przykładowych danych urządzenia na podstawie ticketId
        String equipmentType = faker.commerce().productName();
        String equipmentBrand = faker.company().name();
        String equipmentDescription = faker.lorem().sentence();

        System.out.println("Sprzęt: " + equipmentType);
        System.out.println("Firma sprzętu: " + equipmentBrand);
        System.out.println("Opis problemu: " + equipmentDescription);

        String technicianId;
        String disassembled;
        int choice;
        String result;

        while (true) {
            System.out.print("ID technika(lub wpisz 'exit' aby zakończyć): ");
            technicianId = scanner.nextLine();
            if (technicianId.equalsIgnoreCase("exit")) {
                System.out.println("Zakończono formularz diagnostyczny.");
                return; // Zakończenie formularza
            }
            if (!validateTechnicianId(technicianId)) {
                System.out.println("Nieprawidłowe ID technika. Spróbuj ponownie.");
                continue; // Powrót do początku pętli
            }

            System.out.print("Rodzaj sprzętu (jeśli nie znasz, wpisz 'inne'): ");
            equipmentType = scanner.nextLine();

            System.out.print("Firma sprzętu (jeśli nie znasz, wpisz 'nieznana'): ");
            equipmentBrand = scanner.nextLine();

            System.out.print("Czy technik musiał rozebrać sprzęt? (tak/nie): ");
            disassembled = scanner.nextLine();
            if (!disassembled.equals("tak") && !disassembled.equals("nie")) {
                System.out.println("Nieprawidłowa odpowiedź. Użyj 'tak' lub 'nie'. Spróbuj ponownie.");
                continue; // Powrót do początku pętli
            }

            System.out.print("Wybierz typ diagnostyki (1 - pełna, 2 - częściowa): ");
            choice = scanner.nextInt();
            scanner.nextLine(); // Konsumowanie znaku nowej linii

            if (choice != 1 && choice != 2) {
                System.out.println("Nieprawidłowy wybór. Użyj 1 lub 2. Spróbuj ponownie.");
                continue; // Powrót do początku pętli
            }

            System.out.print("Wyniki diagnostyki: ");
            result = scanner.nextLine();
            if (result == null || result.trim().isEmpty()) {
                System.out.println("Wynik diagnostyki nie może być pusty. Spróbuj ponownie.");
                continue; // Powrót do początku pętli
            }

            // Zapisanie nowych danych diagnostycznych do mapy
            DiagnosticResult newDiagnosticResult = new DiagnosticResult(ticketId, technicianId, result, choice == 1);

            System.out.println("\nCzy na pewno chcesz zapisać ten raport? (tak/nie): ");
            String confirmation = scanner.nextLine().trim().toLowerCase();

            if (confirmation.equals("tak")) {
                diagnosticRepository.addReportsForTicketId(ticketId, generatedReports);
                generatedReports.add(newDiagnosticResult);
                System.out.println("\nRaport został zapisany.");

                System.out.println("\n=== Podsumowanie Diagnostyki ===");
                printSummary(ticketId, technicianId, equipmentType, equipmentBrand, disassembled, result);

                if (choice == 2) {
                    System.out.println("---------------------------------------------------------------");
                    System.out.println("\nPowiadomienie: Zgłoszenie częściowej diagnostyki zostało wysłane do pracownika serwisu.");
                    System.out.println("");
                    System.out.println("---------------------------------------------------------------");
                }
                break;
            } else {
                System.out.println("\nRaport nie został zapisany.");
                break;
            }
        }
    }

    public boolean validateTechnicianId(String technicianId) {
        return technicianId != null && !technicianId.trim().isEmpty();
    }

    public void displaySimpleReports(int ticketId) {
        List<DiagnosticResult> reports = diagnosticRepository.getReportsForTicketId(ticketId);

        if (reports != null && !reports.isEmpty()) {
            System.out.println("\n=== Istniejące raporty diagnostyczne ===");
            for (DiagnosticResult report : reports) {
                System.out.println("\nID zgłoszenia: " + report.getTicketId());
                System.out.println("ID technika: " + report.getTechnicianId());
                System.out.println("Wyniki diagnostyki: " + report.getNotes());
                System.out.println("Zakończono: " + (report.isComplete() ? "Tak" : "Nie"));
            }
        } else {
            System.out.println("Brak dostępnych raportów dla tego ID zgłoszenia.");
        }
    }

    private void printSummary(int ticketId, String technicianId, String equipmentType,
                              String equipmentBrand, String disassembled, String result) {
        System.out.println("\nPodsumowanie diagnostyki:");
        System.out.println("ID zgłoszenia: " + ticketId);
        System.out.println("ID technika: " + technicianId);
        System.out.println("Rodzaj sprzętu: " + equipmentType);
        System.out.println("Firma sprzętu: " + equipmentBrand);
        System.out.println("Czy sprzęt był rozebrany: " + (disassembled.equals("tak") ? "Tak" : "Nie"));
        System.out.println("Wyniki diagnostyki: " + result);
    }
}
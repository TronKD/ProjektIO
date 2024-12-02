package model;

import com.github.javafaker.Faker;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class SimpleDiagnosticDataGenerator {

    private static final Faker faker = new Faker(new Locale("pl", "PL"));

    // Generowanie danych tylko dla konkretnego ticketId
    public static List<DiagnosticResult> generateSampleDataForTicketId(int ticketId, int numberOfReports) {
        List<DiagnosticResult> diagnosticResults = new ArrayList<>();

        // Wygenerowanie przykładowych danych diagnostycznych tylko dla tego ticketId
        for (int i = 0; i < numberOfReports; i++) {
            String technicianId = faker.idNumber().valid();
            String equipmentType = faker.commerce().productName();
            String equipmentBrand = faker.company().name();
            String disassembled = faker.options().option("tak", "nie");
            String result = faker.lorem().sentence();
            boolean isComplete = faker.bool().bool(); // Losowa wartość true/false

            DiagnosticResult diagnosticResult = new DiagnosticResult(ticketId, technicianId, result, isComplete);
            diagnosticResults.add(diagnosticResult);
        }

        return diagnosticResults;
    }
}

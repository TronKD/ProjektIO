package repository;

import com.github.javafaker.Faker;
import model.DiagnosticResult;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class DiagnosticRepository {

    private static final Map<Integer, List<DiagnosticResult>> ticketReportsMap = new HashMap<>();
    private static final Faker faker = new Faker(new Locale("pl", "PL"));

    // Inicjalizacja przykładowych raportów
    static {
        for (int ticketId = 1; ticketId <= 3; ticketId++) {
            List<DiagnosticResult> reports = new ArrayList<>();
            String technicianId = faker.idNumber().valid();
            String result = faker.lorem().sentence();
            boolean isComplete = faker.bool().bool();
            reports.add(new DiagnosticResult(ticketId, technicianId, result, isComplete));
            ticketReportsMap.put(ticketId, reports);
        }
    }

    // Dodawanie raportów dla danego ticketId
    public void addReportsForTicketId(int ticketId, List<DiagnosticResult> reports) {
        ticketReportsMap.put(ticketId, reports);
    }

    // Pobieranie raportów dla danego ticketId
    public List<DiagnosticResult> getReportsForTicketId(int ticketId) {
        return ticketReportsMap.getOrDefault(ticketId, new ArrayList<>());
    }

    // Sprawdzanie, czy istnieją raporty dla danego ticketId
    public boolean hasReportsForTicketId(int ticketId) {
        return ticketReportsMap.containsKey(ticketId);
    }
}

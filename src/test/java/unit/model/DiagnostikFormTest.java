package unit.model;

import com.github.javafaker.Faker;
import org.junit.jupiter.api.Test;
import model.DiagnostikForm;
import repository.DiagnosticRepository;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

public class DiagnostikFormTest {

    @Test
    public void testStartDiagnosticFormFullDiagnostic() {
        // Użycie Faker do generowania danych
        Faker faker = new Faker();
        String ticketId = faker.idNumber().valid();
        String technicianId = faker.idNumber().valid();
        String equipmentType = faker.commerce().productName();
        String equipmentBrand = faker.company().name();
        String disassembled = faker.options().option("tak", "nie");
        int choice = faker.number().numberBetween(1, 3);  // Wybór między 1 (pełna) i 2 (częściowa)
        String result = faker.lorem().sentence();

        // Symulacja danych wejściowych przez System.setIn
        String simulatedInput = ticketId + "\n" + technicianId + "\n" + equipmentType + "\n" + equipmentBrand + "\n" + disassembled + "\n" + choice + "\n" + result + "\n";
        InputStream in = new ByteArrayInputStream(simulatedInput.getBytes());
        System.setIn(in);

        // Zmieniamy strumień wyjściowy na nasz ByteArrayOutputStream
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        DiagnosticRepository repository = new DiagnosticRepository();
        DiagnostikForm formDiagnostik = new DiagnostikForm(repository);
        formDiagnostik.startDiagnosticForm(Integer.parseInt(ticketId));

        // Sprawdzamy, czy metoda drukuje oczekiwane dane
        assertTrue(outContent.toString().contains("=== Formularz Diagnostyki Serwisowej ==="));
        assertTrue(outContent.toString().contains("Wyniki diagnostyki: " + result));
    }

    @Test
    public void testStartDiagnosticFormPartialDiagnostic() {
        // Użycie Faker do generowania danych
        Faker faker = new Faker();
        String ticketId = faker.idNumber().valid();
        String technicianId = faker.idNumber().valid();
        String equipmentType = faker.commerce().productName();
        String equipmentBrand = faker.company().name();
        String disassembled = faker.options().option("tak", "nie");
        int choice = 2;  // Częściowa diagnostyka
        String result = faker.lorem().sentence();

        // Symulacja danych wejściowych przez System.setIn
        String simulatedInput = ticketId + "\n" + technicianId + "\n" + equipmentType + "\n" + equipmentBrand + "\n" + disassembled + "\n" + choice + "\n" + result + "\n";
        InputStream in = new ByteArrayInputStream(simulatedInput.getBytes());
        System.setIn(in);

        // Zmieniamy strumień wyjściowy na nasz ByteArrayOutputStream
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        DiagnostikForm formDiagnostik = new DiagnostikForm();
        formDiagnostik.startDiagnosticForm(Integer.parseInt(ticketId));

        // Sprawdzamy, czy metoda drukuje oczekiwane dane
        assertTrue(outContent.toString().contains("=== Formularz Diagnostyki Serwisowej ==="));
        assertTrue(outContent.toString().contains("Powód, dlaczego nie można zakończyć diagnostyki: " + result));
    }
}

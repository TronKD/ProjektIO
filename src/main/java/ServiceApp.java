import model.DiagnostikForm;
import repository.DiagnosticRepository;
import java.util.Scanner;

public class ServiceApp {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        DiagnosticRepository repository = new DiagnosticRepository();
        DiagnostikForm diagnostikForm = new DiagnostikForm(repository);

        System.out.println("======== Aplikacja Serwisowa ========");
        System.out.println("Witaj w systemie diagnostyki!");

        boolean continueDiagnostics = true;

        while (continueDiagnostics) {
            System.out.println("\nWybierz opcję:");
            System.out.println("1. Rozpocznij formularz diagnostyczny");
            System.out.println("2. Wyświetl raporty diagnostyczne");
            System.out.println("3. Wyjdź z systemu");

            System.out.print("Twój wybór: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1 -> {
                    System.out.print("Podaj ID zgłoszenia (cyfra): ");
                    int ticketId = scanner.nextInt();
                    scanner.nextLine();
                    diagnostikForm.startDiagnosticForm(ticketId);
                }
                case 2 -> {
                    System.out.print("Podaj ID zgłoszenia (cyfra): ");
                    int ticketId = scanner.nextInt();
                    scanner.nextLine();
                    diagnostikForm.displaySimpleReports(ticketId);
                }
                case 3 -> {
                    continueDiagnostics = false;
                    System.out.println("Dziękujemy za skorzystanie z systemu diagnostyki.");
                }
                default -> System.out.println("Nieprawidłowy wybór. Spróbuj ponownie.");
            }
        }
        scanner.close();
    }
}
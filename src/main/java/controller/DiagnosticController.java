package controller;

import model.DiagnosticResult;
import service.DiagnosticService;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@RestController
@RequestMapping("/api/diagnostic")
public class DiagnosticController {

    private final DiagnosticService diagnosticService;

    public DiagnosticController(DiagnosticService diagnosticService) {
        this.diagnosticService = diagnosticService;
    }

    @PostMapping("/add")
    public ResponseEntity<String> addDiagnosticResult(
            @RequestParam int ticketId,
            @RequestParam String technicianId,
            @RequestParam String result,
            @RequestParam boolean isComplete) {

        // Walidacja danych
        if (ticketId <= 0) {
            return ResponseEntity.badRequest().body("Nieprawidłowe ID zgłoszenia.");
        }
        if (technicianId == null || technicianId.trim().isEmpty()) {
            return ResponseEntity.badRequest().body("ID technika nie może być puste.");
        }
        if (result == null || result.trim().isEmpty()) {
            return ResponseEntity.badRequest().body("Wynik diagnostyki nie może być pusty.");
        }

        diagnosticService.addDiagnosticResult(ticketId, technicianId, result, isComplete);
        return ResponseEntity.status(HttpStatus.CREATED).body("Wynik diagnostyki został zapisany!");
    }

    @GetMapping("/{ticketId}")
    public ResponseEntity<DiagnosticResult> getDiagnosticResult(@PathVariable int ticketId) {
        DiagnosticResult result = diagnosticService.getDiagnosticResult(ticketId);
        if (result == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(result);
    }

    @PutMapping("/update/{ticketId}")
    public ResponseEntity<String> updateDiagnosticResult(
            @PathVariable int ticketId,
            @RequestParam String technicianId,
            @RequestParam String result,
            @RequestParam boolean isComplete) {

        // Walidacja danych
        if (ticketId <= 0) {
            return ResponseEntity.badRequest().body("Nieprawidłowe ID zgłoszenia.");
        }
        if (technicianId == null || technicianId.trim().isEmpty()) {
            return ResponseEntity.badRequest().body("ID technika nie może być puste.");
        }
        if (result == null || result.trim().isEmpty()) {
            return ResponseEntity.badRequest().body("Wynik diagnostyki nie może być pusty.");
        }

        diagnosticService.updateDiagnosticResult(ticketId, technicianId, result, isComplete);
        return ResponseEntity.ok("Wynik diagnostyki został zaktualizowany!");
    }

    @DeleteMapping("/delete/{ticketId}")
    public ResponseEntity<String> deleteDiagnosticResult(@PathVariable int ticketId) {
        if (ticketId <= 0) {
            return ResponseEntity.badRequest().body("Nieprawidłowe ID zgłoszenia.");
        }

        diagnosticService.deleteDiagnosticResult(ticketId);
        return ResponseEntity.ok("Wynik diagnostyki został usunięty!");
    }
}
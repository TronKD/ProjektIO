package controller;

import model.DiagnosticResult;
import service.DiagnosticService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/diagnostic")
public class DiagnosticController {

    private final DiagnosticService diagnosticService;

    public DiagnosticController(DiagnosticService diagnosticService) {
        this.diagnosticService = diagnosticService;
    }

    @PostMapping("/add")
    public String addDiagnosticResult(
            @RequestParam int ticketId,
            @RequestParam String technicianId,
            @RequestParam String result,
            @RequestParam boolean isComplete) {
        diagnosticService.addDiagnosticResult(ticketId, technicianId, result, isComplete);
        return "Wynik diagnostyki został zapisany!";
    }

    @GetMapping("/{ticketId}")
    public DiagnosticResult getDiagnosticResult(@PathVariable int ticketId) {
        return diagnosticService.getDiagnosticResult(ticketId);
    }

    @PutMapping("/update/{ticketId}")
    public String updateDiagnosticResult(
            @PathVariable int ticketId,
            @RequestParam String technicianId,
            @RequestParam String result,
            @RequestParam boolean isComplete) {
        diagnosticService.updateDiagnosticResult(ticketId, technicianId, result, isComplete);
        return "Wynik diagnostyki został zaktualizowany!";
    }

    @DeleteMapping("/delete/{ticketId}")
    public String deleteDiagnosticResult(@PathVariable int ticketId) {
        diagnosticService.deleteDiagnosticResult(ticketId);
        return "Wynik diagnostyki został usunięty!";
    }
}

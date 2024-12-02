package model;

public class DiagnosticResult {
    private int ticketId;
    private String technicianId;
    private String notes;
    private boolean isComplete;

    // Konstruktor do tworzenia obiektów DiagnosticResult
    public DiagnosticResult(int ticketId, String technicianId, String notes, boolean isComplete) {
        this.ticketId = ticketId;
        this.technicianId = technicianId;
        this.notes = notes;
        this.isComplete = isComplete;
    }

    // Gettery
    public int getTicketId() {
        return ticketId;
    }

    public String getTechnicianId() {
        return technicianId;
    }

    public String getNotes() {
        return notes;
    }

    public boolean isComplete() {
        return isComplete;
    }

    // Settery - dodane metody, które umożliwiają aktualizację wartości
    public void setTechnicianId(String technicianId) {
        this.technicianId = technicianId;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public void setComplete(boolean isComplete) {
        this.isComplete = isComplete;
    }
}

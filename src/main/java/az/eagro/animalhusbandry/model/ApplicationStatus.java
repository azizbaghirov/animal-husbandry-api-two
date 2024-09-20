package az.eagro.animalhusbandry.model;

public enum ApplicationStatus {

    REGISTERED("Müraciət edilib"),
    MONITORING_IN_PROGRESS("Yoxlanılır"),
    CERTIFIED("Uğurlu"),
    REJECTED("İmtina edilib");

    private final String label;

    ApplicationStatus(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

    public static ApplicationStatus getByLabel(String label) {
        for (ApplicationStatus status : ApplicationStatus.values()) {
            if (status.getLabel().equalsIgnoreCase(label)) {
                return status;
            }
        }
        throw new IllegalArgumentException("Invalid ApplicationStatus label: " + label);
    }
}

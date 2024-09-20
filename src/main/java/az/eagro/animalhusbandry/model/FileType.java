package az.eagro.animalhusbandry.model;

import az.eagro.animalhusbandry.business.BusinessException;

public enum FileType {

    INITIAL_MONITORING_DECISION("initial-monitoring-decision"),
    FINAL_MONITORING_DECISION("final-monitoring-decision"),
    BREEDING_ANIMAL_FARM_CERTIFICATE("breeding-animal-farm-certificate");

    private final String label;

    FileType(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

    public static FileType getByLabel(String label) {
        for (FileType status : FileType.values()) {
            if (status.getLabel().equalsIgnoreCase(label)) {
                return status;
            }
        }
        throw new BusinessException("Invalid FileType label: " + label);
    }
}

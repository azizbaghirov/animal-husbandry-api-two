package az.eagro.animalhusbandry.model;

import az.eagro.animalhusbandry.business.BusinessException;

public enum DocumentTypeLabel {
    ACT("Akt"),
    PROTOCOL("Protokol"),
    CERTIFICATE("Şəhadətnamə"),
    REGISTRY_EXCERPT("Reyestrdən çıxarış");

    private final String label;

    DocumentTypeLabel(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

    public static DocumentTypeLabel getByLabel(String label) {
        for (DocumentTypeLabel l : DocumentTypeLabel.values()) {
            if (l.getLabel().equalsIgnoreCase(label)) {
                return l;
            }
        }
        throw new BusinessException("Invalid DocumentTypeLabel: " + label);
    }
}

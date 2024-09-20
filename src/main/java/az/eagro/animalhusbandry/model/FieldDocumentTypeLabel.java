package az.eagro.animalhusbandry.model;

import az.eagro.animalhusbandry.business.BusinessException;

public enum FieldDocumentTypeLabel {

    EXTRACT_ACT("extract_act", "Çıxarış (ƏMDK)"),
    STATE_ACT("state_act", "Dövlət aktı/Şəhadətnamə"),
    RENT_ACT("rent_act", "Müqavilə");

    private final String label;
    private final String name;

    FieldDocumentTypeLabel(String label, String name) {
        this.label = label;
        this.name = name;
    }

    public String getLabel() {
        return label;
    }

    public String getName() {
        return name;
    }

    public static FieldDocumentTypeLabel getByLabel(String label) {
        for (FieldDocumentTypeLabel l : FieldDocumentTypeLabel.values()) {
            if (l.getLabel().equalsIgnoreCase(label)) {
                return l;
            }
        }
        throw new BusinessException("Invalid FieldDocumentTypeLabel: " + label);
    }
}

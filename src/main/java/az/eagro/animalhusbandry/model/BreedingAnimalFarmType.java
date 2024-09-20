package az.eagro.animalhusbandry.model;

import az.eagro.animalhusbandry.business.BusinessException;

public enum BreedingAnimalFarmType {

    CATTLE_BREEDING("İribuynuzlu"),
    SMALL_RUMINANT_BREEDING("Xırdabuynuzlu"),
    CAMEL_BREEDING("Dəvəçilik"),
    PIG_BREEDING("Donuzçuluq"),
    HORSE_BREEDING("Atçılıq"),
    AVICULTURE("Quşçuluq"),
    APICULTURE("Arıçılıq");

    private final String label;

    BreedingAnimalFarmType(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

    public static BreedingAnimalFarmType getByLabel(String label) {
        for (BreedingAnimalFarmType l : BreedingAnimalFarmType.values()) {
            if (l.getLabel().equalsIgnoreCase(label)) {
                return l;
            }
        }
        throw new BusinessException("Invalid DocumentTypeLabel: " + label);
    }

}

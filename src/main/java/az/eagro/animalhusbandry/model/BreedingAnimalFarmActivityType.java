package az.eagro.animalhusbandry.model;

public enum BreedingAnimalFarmActivityType {

    FARM("Ferma"),
    FACTORY("Zavod"),
    REPRODUCTOR("Reproduktor");

    private final String label;

    BreedingAnimalFarmActivityType(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}

package az.eagro.animalhusbandry.model;

public enum FileExtension {

    DOC("doc"),
    DOCX("docx"),
    PDF("pdf"),
    JPEG("jpeg"),
    JPG("jpg"),
    PNG("png");

    private final String extension;

    FileExtension(String extension) {
        this.extension = extension;
    }

    public String getExtension() {
        return extension;
    }

}

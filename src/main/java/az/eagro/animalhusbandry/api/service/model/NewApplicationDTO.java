package az.eagro.animalhusbandry.api.service.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NewApplicationDTO {

    @NotNull(message = "Təsərrüfat seçilməyib")
    private Integer farmId;

    @NotEmpty(message = "Sahə dəyəri vacib məlumatdır, boş ola bilməz")
    private List<NewFieldValueDTO> fieldValues = new ArrayList<>();

    @NotNull(message = "Müqavilə seçilməyib")
    private NewFileDTO file;

    @Size(max = 1000, message = "Yazılmış qeyd xanası üzrə maksimum simvol həddi (1000) aşılmışdır")
    private String note;

    private boolean selectedAll;

    private String nicknameOrTagId;

    private Set<Integer> markedIds = new HashSet<>();

    private Set<Integer> ignoredIds = new HashSet<>();

}

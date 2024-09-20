package az.eagro.animalhusbandry.api;

import javax.validation.constraints.Min;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PageRequest {

    @Min(value = 0, message = "Sıfır və ya müsbət tam ədəd təyin edilməlidir.")
    private int page = 0;

    @Min(value = 1, message = "Sıfırdan böyük tam ədəd təyin edilməlidir.")
    private int size = 50;

}

package az.eagro.animalhusbandry.shared;

import az.eagro.animalhusbandry.api.service.model.LegalPersonDTO;
import az.eagro.animalhusbandry.api.service.model.PersonDTO;
import java.util.Base64;
import java.util.UUID;
import lombok.experimental.UtilityClass;
import org.json.JSONException;
import org.json.JSONObject;

@UtilityClass
public class TokenParser {

    public static PersonDTO parse(String token) throws JSONException {
        Base64.Decoder decoder = Base64.getUrlDecoder();
        String[] chunks = token.split("\\.");

        String payload = new String(decoder.decode(chunks[1]));
        JSONObject jsonObject = new JSONObject(payload);

        JSONObject personObjectMain = jsonObject.getJSONObject("main");
        JSONObject personObject = personObjectMain.getJSONObject("person");

        var person = PersonDTO.builder()
                .userId(UUID.fromString(personObjectMain.getString("userId").toUpperCase()))
                .pin(personObject.getString("pin"))
                .name(personObject.getString("name"))
                .surname(personObject.getString("surname"))
                .patronymic(personObject.getString("fatherName"))
                .build();

        if (personObject.has("structureData") && !personObject.isNull("structureData")) {
            JSONObject structureObject = personObject.getJSONObject("structureData");
            person.setLegalPerson(LegalPersonDTO.builder()
                    .name(structureObject.getString("structureName"))
                    .taxPayerIdentificationNumber(structureObject.getString("voen"))
                    .hasStamp(structureObject.getBoolean("hasStamp"))
                    .legal(structureObject.getBoolean("legal")).build());
        }

        return person;
    }
}

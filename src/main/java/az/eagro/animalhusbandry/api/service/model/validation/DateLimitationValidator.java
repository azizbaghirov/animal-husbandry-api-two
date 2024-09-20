package az.eagro.animalhusbandry.api.service.model.validation;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class DateLimitationValidator implements ConstraintValidator<DateLimitation, Instant> {

    @Override
    public boolean isValid(Instant dateTime, ConstraintValidatorContext context) {

        ZoneId zoneId = ZoneId.systemDefault();
        ZonedDateTime currentDateTime = ZonedDateTime.now(zoneId);
        ZonedDateTime thirtyDaysAgo = currentDateTime.minusDays(31);
        ZonedDateTime registeredAtDateTime = dateTime.atZone(zoneId);

        return !registeredAtDateTime.isBefore(thirtyDaysAgo) && !registeredAtDateTime.isAfter(currentDateTime);
    }
}
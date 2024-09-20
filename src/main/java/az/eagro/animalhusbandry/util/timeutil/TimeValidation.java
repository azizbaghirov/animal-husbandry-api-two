package az.eagro.animalhusbandry.util.timeutil;

import az.eagro.animalhusbandry.business.BusinessException;
import java.time.Duration;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;

public class TimeValidation {

    public static final int HOURS_AFTER_CREATION = 24;

    public static void validate(Instant instant) {
        ZoneId systemTimeZone = ZoneId.systemDefault();
        ZonedDateTime currentDateTime = ZonedDateTime.now(systemTimeZone);
        ZonedDateTime twentyFourHoursAfterCreatedAt = ZonedDateTime.ofInstant(instant, systemTimeZone).plus(Duration.ofHours(HOURS_AFTER_CREATION));

        if (currentDateTime.isAfter(twentyFourHoursAfterCreatedAt)) {
            throw new BusinessException("The current time is NOT within the 24-hour time limit...");
        }
    }

    public static boolean twentyFourHoursPassed(Instant instant) {
        ZoneId systemTimeZone = ZoneId.systemDefault();
        ZonedDateTime twentyFourHoursAfterCreatedAt = ZonedDateTime.ofInstant(instant, systemTimeZone).plusHours(HOURS_AFTER_CREATION);

        return ZonedDateTime.now(systemTimeZone).isBefore(twentyFourHoursAfterCreatedAt);
    }

}

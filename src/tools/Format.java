package tools;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Format {
	private static final String DATE_FORMATTER_FOR_STRING = "dd-MM-yyyy HH:mm:ss";
	
	/**
	 * Convert LocalDateTime to dd-MM-yyyy HH:mm:ss String
	 * @param dateTime
	 * @return
	 */
	public static String convertLocalDateTimeToString(LocalDateTime dateTime) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_FORMATTER_FOR_STRING);
        String formatDateTime = dateTime.format(formatter);
        return formatDateTime;
	}
	
	/**
	 * Convert String dd-MM-yyyy HH:mm:ss to LocalDateTime
	 * @param dateTime
	 * @return
	 */
	public static LocalDateTime convertStringToLocalDateTime(String dateTime) {
		LocalDateTime formatDateTime = LocalDateTime.parse(dateTime, DateTimeFormatter.ISO_DATE_TIME);
		return formatDateTime;
	}

}

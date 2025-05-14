package util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

//Helper class to handle dates

public class DateUtil {
	
	//date pattern that we use
	private static final String DATE_PATTERN = "dd.MM.yyyy";
	
	//date formatter
	private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern(DATE_PATTERN);
	
	//returns the given date in the specified format DATE_PATTERN
	public static String format(LocalDate date) {
		if (date == null) {
			return null;
		}
		return DATE_FORMATTER.format(date);
	}
	
	//converts from String in the format DATE_PATTERN to a LocaDate object
	public static LocalDate parse(String dateString) {
		try {
			return DATE_FORMATTER.parse(dateString, LocalDate::from);
		}catch(DateTimeParseException e) {
			return null;
		}
	}
	
	//check whether the string is a valid date
	public static boolean validDate(String dateString) {
		//try to parse the string
		return DateUtil.parse(dateString) != null;
	}
}

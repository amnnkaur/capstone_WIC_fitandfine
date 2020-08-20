package com.lambton.capstone_wic_fitandfine.util;


import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidationUtil {

	private static final String EMAIL_PATTERN = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
    private static final String BASE64_PATTERN ="^([A-Za-z0-9+/]{4})*([A-Za-z0-9+/]{4}|[A-Za-z0-9+/]{3}=|[A-Za-z0-9+/]{2}==)$";

    public static boolean isValidText(String text) {
		boolean isValid = false;

		if (text != null && text.trim().length() > 0) {
			isValid = true;
		}

		return isValid;
	}

	/**
	 * Method to check if postal code is valid or not.
	 * 
	 * @param postalCode
	 * @return
	 */
	public static boolean isPostalCodeValid(String postalCode) {

		boolean isValid = false;

		if ((postalCode.startsWith("K") || postalCode.startsWith("L")
				|| postalCode.startsWith("M") || postalCode.startsWith("N")
				|| postalCode.startsWith("P") || postalCode.startsWith("k")
				|| postalCode.startsWith("l") || postalCode.startsWith("m")
				|| postalCode.startsWith("n") || postalCode.startsWith("p"))
				&& Character.isDigit(postalCode.charAt(1))
				&& Character.isLetter(postalCode.charAt(2))
				&& Character.isDigit(postalCode.charAt(3))
				&& Character.isLetter(postalCode.charAt(4))
				&& Character.isDigit(postalCode.charAt(5))) {
			isValid = true;
		}

		return isValid;
	}

	/**
	 * Method to check the input string has special character and numeric values
	 * or not
	 */
	public static boolean isContainAnySpecialCharacterOrNumericValuesExceptSingleQuotes(
			String string) {
		Pattern pattern = Pattern.compile("[^a-z ']", Pattern.CASE_INSENSITIVE);
		Matcher matcher = pattern.matcher(string);
		boolean findSpecialCharacter = matcher.find();

		return findSpecialCharacter;

	}

	/**
	 * Method to check the input string has special character or not
	 */
	public static boolean isContainAnySpecialCharacterExceptSingleQuotes(
			String string) {
		Pattern pattern = Pattern.compile("[^a-z0-9 ']",
				Pattern.CASE_INSENSITIVE);
		Matcher matcher = pattern.matcher(string);
		boolean findSpecialCharacter = matcher.find();

		return findSpecialCharacter;

	}

	/**
	 * Method to check whether email is valid or not
	 * 
	 * @param email
	 * @return
	 */
	public static boolean isEmailAddressValid(String email) {
		boolean isValid = false;

		if (isValidText(email)) {
			// "\\b[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,4}\\b"
			isValid = email.matches(EMAIL_PATTERN);
		}

		return isValid;

	}

    /**
     * Method to check whether base64String is valid or not
     *
     * @param base64String
     * @return
     */
    public static boolean isBase64String(String base64String) {
        boolean isValid = false;

//        if (isValidText(base64String)) {
//            isValid = base64String.matches(BASE64_PATTERN);
//        }

//        return Base64.isBase64(base64String.getBytes());

//        return isValid;

        return true;
    }

	/**
	 * Method to validate phone number
	 */
	public static boolean isValidPhoneNumber(String phoneNumber) {

		Character ch = null;
		int count = 0;
		boolean isValid = false;

		for (int charIndex = 0; charIndex < phoneNumber.length(); charIndex++) {
			ch = phoneNumber.charAt(charIndex);

			if (ch != null && Character.isDigit(ch)) {
				count++;
			}
		}

        isValid = count >= 10;

		return isValid;

	}

	/**
	 * Method to validate credit card number
	 */
	public static boolean isValidCreditCardNumber(String cardType,
                                                  String cardNumber) {
		if (cardType != null && cardType.equalsIgnoreCase("VISA")) {
			if (cardNumber.charAt(0) == '4') {
                return cardNumber.length() == 16;

			} else {
				return false;
			}
		} else if (cardType != null && cardType.equalsIgnoreCase("MASTERCARD")) {
			int firstTwoDigit = Integer.parseInt(cardNumber.substring(0, 2));
			if (firstTwoDigit >= 51 && firstTwoDigit <= 55) {
                return cardNumber.length() == 16;

			} else {
				return false;
			}
		} else if (cardType != null
				&& cardType.equalsIgnoreCase("AMERICAN EXPRESS")) {
			int firstTwoDigit = Integer.parseInt(cardNumber.substring(0, 2));
			if (firstTwoDigit == 34 || firstTwoDigit == 37) {
				if (cardNumber.length() == 15) {
					return true;
				} else {
                    return false;
				}
			} else {
				return false;
			}
		}
		return false;

	}

	/**
	 * Method to validate expiry month
	 */
	public static boolean isValidExpiryMonth(String month, String year) {
		Date date = new Date();
		SimpleDateFormat sdf;

		sdf = new SimpleDateFormat("MM");
		int currentMonth = Integer.parseInt(sdf.format(date));

		sdf = new SimpleDateFormat("yyyy");
		int currentYear = Integer.parseInt(sdf.format(date));

		int inputMonth = Integer.parseInt(month);
		int inputYear = Integer.parseInt(year);
		inputYear = inputYear + 2000;
		if (inputMonth >= currentMonth && inputYear == currentYear) {
			return true;
        } else return inputMonth >= currentMonth || inputYear != currentYear;

    }

	/**
	 * Method to validate expiry year
	 */
	public static boolean isValidExpiryYear(String year) {
		Date date = new Date();
		SimpleDateFormat sdf;

		sdf = new SimpleDateFormat("yy");
		int currentYear = Integer.parseInt(sdf.format(date));
		int inputYear = Integer.parseInt(year);
        return inputYear >= currentYear;

    }

	/**
	 * Method to validate month.
	 * 
	 * @param month
	 * @return
	 */
	public static boolean isValidMonth(String month) {
		if (month.length() > 0 && month.length() <= 2) {
			int inputMonth = Integer.parseInt(month);
            return inputMonth >= 1 && inputMonth <= 12;
		}
		return false;
	}

	/**
	 * Method to validate year
	 * 
	 * @param year
	 * @return
	 */
	public static boolean isValidYear(String year) {
        return year.length() == 2;
    }

	public static boolean isValidIndianPhoneNumber(String phoneNumber) {

		if (phoneNumber == null) {
			return false;
		}

		if (phoneNumber.trim().length() < 10) {
			return false;
		}

		if (phoneNumber.trim().length() == 10) {
			return true;
		}

		if (phoneNumber.trim().length() == 12) {

            return phoneNumber.startsWith("91");

        }

		if (phoneNumber.trim().length() == 13) {

            return phoneNumber.startsWith("+91");

        }
		return false;
	}

	public static boolean isValidPassword(String password){
        return password.length() >= 8;
    }

}

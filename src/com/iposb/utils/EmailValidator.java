package com.iposb.utils;

/**
 *  參考自：https://www.mkyong.com/regular-expressions/how-to-validate-email-address-with-regular-expression/
 */

import java.util.regex.Matcher;
import java.util.regex.Pattern;
 
public class EmailValidator {
 
	private static final String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
	private static Pattern pattern = Pattern.compile(EMAIL_PATTERN);;
	private static Matcher matcher;
 
 
	/**
	 * Validate hex with regular expression
	 * 
	 * @param hex
	 *            hex for validation
	 * @return true valid hex, false invalid hex
	 */
	public static boolean validate(final String hex) {
 
		matcher = pattern.matcher(hex);
		return matcher.matches();
 
	}
}
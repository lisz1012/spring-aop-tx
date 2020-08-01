package com.lisz;

import java.util.Locale;

public class T1 {
	public static void main(String[] args) {
		Locale locale = Locale.forLanguageTag("en-US");
		System.out.println(locale.getDisplayLanguage());
		System.out.println(locale.getLanguage());
		System.out.println(locale.getCountry());
	}
}

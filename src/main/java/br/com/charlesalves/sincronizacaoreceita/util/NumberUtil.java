package br.com.charlesalves.sincronizacaoreceita.util;

import org.apache.commons.lang3.math.NumberUtils;

public class NumberUtil {

	public static double toDouble(String number) {
		number = number.replace(",", ".");

		return NumberUtils.toDouble(number);
	}

}

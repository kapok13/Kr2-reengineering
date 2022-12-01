package org.example;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

public class Utils {
    public static String formatMoney(double sum) {
        DecimalFormatSymbols symbols = new DecimalFormatSymbols();
        symbols.setDecimalSeparator('.');
        return new DecimalFormat("$#.00", symbols).format(sum);
    }
}

package com.picsart;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidationDate {
    public boolean validDateFormat(String date) {
        String str = "^(19[5-9][0-9]|20(0[0-9]|1[0-9]|2[0-4]))-(0[1-9]|1[0-2])-(0[1-9]|[12][0-9]|3[01])$";
        Pattern pattern = Pattern.compile(str);
        Matcher matcher = pattern.matcher(date);
        if (matcher.matches()) {
            String[] split = date.split("-");
            int year = Integer.parseInt(split[0]);
            int month = Integer.parseInt(split[1]);
            int day = Integer.parseInt(split[2]);

            if (((month == 1 || month == 3 || month == 5 || month == 7 || month == 8 || month == 10 || month == 12)
                    && (day >= 1 && day <= 31)) || ((month == 4 || month == 6 || month == 9 || month == 11) && day >= 1 && day <= 30)) {
                return true;
            } else return (month == 2 && (year % 4 == 0 && year % 100 != 0) && (day >= 1 && day <= 29)) || (month == 2 && day >= 1 && day <= 28);
        }
        return false;
    }
}

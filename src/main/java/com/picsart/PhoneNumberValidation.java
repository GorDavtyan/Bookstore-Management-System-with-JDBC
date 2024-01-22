package com.picsart;

import java.util.ArrayList;


public class PhoneNumberValidation {
    public boolean phoneNumberValid(String phoneNumber) {
        ArrayList<String> phoneNumberCode = new ArrayList<>();
        try {
            checkPhoneNumberSize(phoneNumber);
            plus(phoneNumber);
            codePhoneNumber(phoneNumber);
            checkSymbols(phoneNumber);
            checkZero(phoneNumber);
            addPhoneNumberCode(phoneNumberCode);
            codePhoneNumbers(phoneNumberCode, phoneNumber);
            return true;
        } catch (Exception ex) {
            System.out.println("Invalid phone number, please the valid phone number.");
            return false;
        }
    }

    public void plus(String phoneNumber) throws Exception{
            if (phoneNumber.charAt(0) != '+') {
                throw new Exception();
            }
    }


    public void codePhoneNumber(String phoneNumber) throws Exception {
            if (!(phoneNumber.startsWith("374", 1))) {
                throw new Exception();
            }
    }

    public void checkPhoneNumberSize(String phoneNumber) throws Exception {
            if (phoneNumber.length() != 12) {
                throw new Exception();
            }
    }

    public void checkSymbols(String phoneNumber) throws Exception {
        boolean test = false;
        for (int i = 1; i < phoneNumber.length(); i++) {
            if (phoneNumber.charAt(i) < 47 || phoneNumber.charAt(i) > 58) {
                test = true;
                break;
            }
        }
            if (test) {
                throw new Exception();
            }
    }

    public void checkZero(String phoneNumber) throws Exception {
            if (phoneNumber.charAt(4) == '0') {
                throw new Exception();
            }
    }

    public void addPhoneNumberCode(ArrayList<String> phoneNumberCode) {
        phoneNumberCode.add("91");
        phoneNumberCode.add("96");
        phoneNumberCode.add("99");
        phoneNumberCode.add("43");
        phoneNumberCode.add("77");
        phoneNumberCode.add("93");
        phoneNumberCode.add("94");
        phoneNumberCode.add("55");
        phoneNumberCode.add("95");
        phoneNumberCode.add("41");
        phoneNumberCode.add("33");
    }

    public void codePhoneNumbers(ArrayList<String> phoneNumberCode, String phoneNumber) throws Exception {
        boolean test = false;
        for (String s : phoneNumberCode) {
            if (phoneNumber.substring(4, 6).equals(s)) {
                test = true;
                break;
            }
        }
            if (!test) {
                throw new Exception();
            }
    }
}

package com.pechatkin.sbt.keyboardtrans;

public class PasswordsHelper {

    private final String[] russians;
    private final String[] latins;
    private final String[] checkPass;

    public PasswordsHelper(String[] russians, String[] latins, String[] checkPass) {
        this.russians = russians;
        this.latins = latins;
        this.checkPass = checkPass;
    }

    public String convert(CharSequence sourse) {
        StringBuilder result = new StringBuilder();
        for(int i=0; i<sourse.length();i++) {
            char c = sourse.charAt(i);
            String s = String.valueOf(c);
            boolean found = false;
            for (int j=0; j< russians.length; j++) {
                if(russians[j].equals(s.toLowerCase())) {
                    result.append(Character.isUpperCase(c) ? latins[j].toUpperCase() : latins[j]);
                    found = true;
                    break;
                }
            }
            if(!found) {
                result.append(s);
            }
        }
        return result.toString();
    }

    public String check(CharSequence sourse) {
        int passLen = sourse.length();
        if(passLen == 0) {
            // CHECK: никогда, ни при каких условиях не следует хардкодить UI-текст!
            return "Ожидание ввода";
        }else if((passLen > 0) && (passLen < 4)) {
            return checkPass[1];
        }else if((passLen >= 4) && (passLen < 8)) {
            return checkPass[2];
        } else if(passLen == 8) {
            return checkPass[3];
        } else if((passLen > 8) && (passLen < 12)) {
            return checkPass[4];
        } else {
            return checkPass[5];
        }
    }
}

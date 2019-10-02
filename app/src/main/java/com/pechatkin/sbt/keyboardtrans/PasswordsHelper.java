package com.pechatkin.sbt.keyboardtrans;

public class PasswordsHelper {

    private final String[] russians;
    private final String[] latins;

    public PasswordsHelper(String[] russians, String[] latins) {
        this.russians = russians;
        this.latins = latins;
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
            return "Ожидание ввода";
        }else if((passLen > 0) && (passLen < 4)) {
            return "Плохо";
        }else if((passLen >= 4) && (passLen < 8)) {
            return "Слишком простой";
        } else if(passLen == 8) {
            return "Неплохо";
        } else if((passLen > 8) && (passLen < 12)) {
            return "Хорошо";
        } else {
            return "Пентагон";
        }
    }
}

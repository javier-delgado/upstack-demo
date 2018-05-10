package com.javierdelgado.upstack_demo.utils;

import android.widget.EditText;

public class Validate {

    public static boolean notEmpty(EditText edt) {
        return notEmpty(edt.getText().toString());
    }

    public static boolean notEmpty(String s) {
        return !s.isEmpty();
    }

    public static boolean validEmail(EditText edt) {
        return validEmail(edt.getText().toString());
    }

    public static boolean validEmail(String s) {
        return !s.isEmpty() && android.util.Patterns.EMAIL_ADDRESS.matcher(s).matches();
    }

    public static boolean validPhone(EditText edt) {
        if (notEmpty(edt))
            return android.util.Patterns.PHONE.matcher(edt.getText().toString()).matches() && edt.getText().toString().length() > 7;
        else
            return false;
    }

    public static boolean minLength(EditText edt, int length) {
        return minLength(edt.getText().toString(), length);
    }

    public static boolean minLength(String s, int length) {
        return s.length() >= length;
    }

    public static boolean maxLength(EditText edt, int length) {
        return edt.getText().toString().length() <= length;
    }

    public static boolean areEqual(EditText edt1, EditText edt2) {
        return edt1.getText().toString().toLowerCase().equals(edt2.getText().toString().toLowerCase());
    }

    public static boolean onlyNumbers(EditText edt) {
        return notEmpty(edt) && edt.getText().toString().matches("[0-9]+");
    }

    public static boolean minLengthAllowEmpty(EditText edt, int length) {
        return edt.getText().length() == 0 || minLength(edt, length);
    }
}

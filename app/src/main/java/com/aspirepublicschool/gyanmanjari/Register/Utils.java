package com.aspirepublicschool.gyanmanjari.Register;

class Utils {

    //Email Validation pattern
    public static final String regEx = "\\b[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}\\b";
    public static final String password = "^" +
          //  "(?=.*?[A-Z])" +
         //   "(?=.*?[a-z])" +
            "(?=.*?[a-zA-Z])" +
            "(?=.*?[0-9])" +
            "(?=.*?[#?!@$%^&*-])" +
            ".{8,}" +
            "$";
    public static final String phonenumber = "(0/91)?[6-9][0-9]{9}";


    //Fragments Tags
    public static final String Login_Fragment = "Login_Fragment";
    public static final String SignUp_Fragment = "SignUp_Fragment";
    public static final String ForgotPassword_Fragment = "ForgotPassword_Fragment";
}

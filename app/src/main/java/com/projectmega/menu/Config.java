package com.projectmega.menu;

/**
 * Created by Justin on 12/23/2015.
 */
public class Config {

    //URL to our login.php file
    public static final String LOGIN_URL = "http://192.168.1.14/mega/login.php";
    //URL to register
    public static final String REGISTER_URL = "http://192.168.1.14/mega/register.php";
    //URL of API
    public static final String DATA_URL = "http://192.168.1.14/mega/Subjects.php?id=";
    //URL for getting Scores
    public static final String SCORE_URL = "http://192.168.1.14/mega/exam.php?id=";

    //Keys for email and password as defined in our $_POST['key'] in login.php
    public static final String KEY_USER = "username";
    public static final String KEY_PASSWORD = "password";
    public static final String KEY_USER1 = "username";
    public static final String KEY_PASSWORD1 = "password";
    public static final String KEY_STUDENT = "studentnum";
    public static final String KEY_STUDENT1 = "studentnum";
    public static final String KEY_CONFIRM = "confirmpass";
    public static final String KEY_FIRSTNAME = "firstname";
    public static final String KEY_LASTNAME = "lastname";
    public static final String KEY_MIDDLENAME = "middlename";
    public static final String KEY_AGE = "age";
    public static final String KEY_SECTION = "section";
    public static final String KEY_EMAIL = "emailadd";

    //Tags for my JSON
    public static final String TAG_SUBJECT = "subject";
    public static final String TAG_SUBJECT_TITLE = "SubjectName";
    public static final String TAG_EXAM_NAME = "ExamName";
    public static final String TAG_SCORE = "Score";
    public static final String TAG_ITEMS = "Items";
    public static final String TAG_PERCENT = "Percent";

    public static final String REGISTER_SUCCESS = "Successfully Registered";
    //If server response is equal to this that means login is successful
    public static final String LOGIN_SUCCESS = "Success";

    //Keys for Sharedpreferences
    //This would be the name of our shared preferences
    public static final String SHARED_PREF_NAME = "myloginapp";

    //This would be used to store the username of current logged in user
    public static final String USER_SHARED_PREF = "user";

    //Store the Student number
    public static final String STUDENT_NUMBER = "Student ID";

    //Store the Subject name clicked
    public static final String SUBJECT = "SubjectName";

    //We will use this to store the boolean in sharedpreference to track user is loggedin or not
    public static final String LOGGEDIN_SHARED_PREF = "loggedin";

}

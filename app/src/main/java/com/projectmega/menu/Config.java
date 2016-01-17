package com.projectmega.menu;

/**
 * Created by Justin on 12/23/2015.
 */
public class Config {

    //URL to our login.php file
    public static final String LOGIN_URL = "http://192.168.43.166/mega/login.php";
    //URL to register
    public static final String REGISTER_URL = "http://192.168.43.166/mega/register.php";
    //URL of API
    public static final String DATA_URL = "http://192.168.43.166/mega/Subjects.php?id=";
    //URL for getting Scores
    public static final String SCORE_URL = "http://192.168.43.166/mega/exam.php?id=";
    //URL for getting Account Info
    public static final String ACCOUNT_URL = "http://192.168.43.166/mega/users.php?id=";
    //URL for parsing data for updates
    public static final String GET_URL = "http://192.168.43.166/mega/update.php?id=";
    //URL for final grade
    public static final String GRADE_URL = "http://192.168.43.166/mega/finalgrade.php?id=";

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
    //Tags for JSOn users
    public static final String TAG_ID = "studentNumber";
    public static final String TAG_USER = "User";
    public static final String TAG_FIRSTNAME = "FirstName";
    public static final String TAG_MIDDLENAME = "MiddleName";
    public static final String TAG_LASTNAME = "LastName";
    public static final String TAG_AGE = "Age";
    public static final String TAG_SECTION = "Section";
    public static final String TAG_EMAIL = "Email";
    public static final String TAG_PASSWORD = "Password";
    //Tags for Final Grade
    public static final String TAG_GRADE = "grade";

    public static final String UPDATE_SUCCESS = "Successfully Updated!";
    public static final String REGISTER_SUCCESS = "Successfully Registered!";
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
    public static final String SUBJECT1 = "SubjectName";

    //We will use this to store the boolean in sharedpreference to track user is loggedin or not
    public static final String LOGGEDIN_SHARED_PREF = "loggedin";

}

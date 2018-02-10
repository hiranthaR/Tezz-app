package com.tezz.tezzapp.utils;

/**
 * Created by User on 12/29/2017.
 */
public class URLs {
    public static String IP = "";
    public static String TAILOR_REGISTER = "";
    public static String TAILOR_LOGIN = "";
    public static String ALL_TAILOR= "";
    public static String DELETE = "";
    public static String UPDATE = "";
    public static String ADD_ODER = "";
    public static String ALL_ODERS = "";
    public static String GET_CUSTOMER= "";
    public static String ADD_TOKEN= "";
    public static String DELETE_TOKEN= "";
    public static String SEND_FCM= "";

    public static void setIP(String ip){
        IP = ip;
        TAILOR_REGISTER = "http://" + IP + "/tezz/reg.php";
        TAILOR_LOGIN= "http://" + IP + "/tezz/login.php";
        ALL_TAILOR = "http://" + IP + "/tezz/alltailors.php";
        ALL_ODERS = "http://" + IP + "/tezz/alloders.php";
        GET_CUSTOMER = "http://" + IP + "/tezz/getcustomer.php";
//        DELETE = "http://" + IP + "/login/delete.php";
//        UPDATE = "http://" + IP + "/login/update.php";
        ADD_ODER = "http://" + IP + "/tezz/addoder.php";
        ADD_TOKEN = "http://" + IP + "/tezz/addtoken.php";
        DELETE_TOKEN= "http://" + IP + "/tezz/deletetoken.php";
        SEND_FCM= "http://" + IP + "/tezz/sendfcm.php";
    }
}

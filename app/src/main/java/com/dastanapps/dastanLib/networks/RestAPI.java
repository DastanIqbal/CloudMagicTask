package com.dastanapps.dastanLib.networks;

/**
 * Created by IQBAL-MEBELKART on 8/16/2016.
 */

public class RestAPI {
    private static String BASE_URL = "http://192.168.0.6:8088";
    private static String MAIL_PATH = "/api/message/";

    public static String GET_ALL_MAIL = BASE_URL + MAIL_PATH;
    public static String GET_MAIL = BASE_URL + MAIL_PATH + "%s";
    public static String DELETE_MAIL = BASE_URL + MAIL_PATH + "%s";


    public static int ID_ALL_MAIL=1001;
    public static int ID_GET_MAIL=1002;
    public static int ID_DELETE_MAIL=1003;
}

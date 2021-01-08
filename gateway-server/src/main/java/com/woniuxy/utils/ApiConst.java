package com.woniuxy.utils;

import java.util.ArrayList;
import java.util.List;

public class ApiConst {

    public static ArrayList<String> list;

    public static final String LOGIN = "/login";
    public static final String REGISTER = "/register";
    public static final String SHOW_MOVIE = "/showMovie";

    static {
        list.add(LOGIN);
        list.add(REGISTER);
        list.add(SHOW_MOVIE);
    }

}


package com.gmail.kirill.ked.telegram.web.tbot.constant;

public class MessagesConstant {
    public static final String COMMAND_NOT_EXIST = "this command doesnt exist, type /showCommands to see all available commands";
    public static final String CITY_NOT_FOUND = "city with this name doesnt exist in database yet, enter /showcities for all available cities";
    public static final String CITY_INFO = "%s is a great city";
    public static final String CITY_WITH_ONE_ATTRACTION = ", don't forget to visit %s";
    public static final String CITY_WITH_MANY_ATTRACTION = ", don't forget to visit %s or %s";

    private MessagesConstant() {
    }
}
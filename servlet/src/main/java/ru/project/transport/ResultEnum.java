package ru.project.transport;

public enum ResultEnum {
    OK              (0, "все хорошо"),
    DUPLICATE_AGENT (1, "такой агент уже зарегистрирован"),
    WRONG_LOGIN     (2, "неверный формат телефона"),
    BAD_PASSWORD    (3, "плохой пароль"),
    OTHER           (5, "другая ошибка повторите позже"),

    AGENT_NOT_EXITS  (11, "Агент с данным логином не существует"),
    ACCOUNT_NOT_EXITS(12, "Счет для агента не существует");

    private final int code;
    private final String description;

    ResultEnum(int code, String description) {
        this.code = code;
        this.description = description;
    }

    public int getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return code + ": " + description;
    }

}

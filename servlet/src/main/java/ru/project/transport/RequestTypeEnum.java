package ru.project.transport;

import org.apache.commons.lang3.StringUtils;

public enum RequestTypeEnum {
    NEW_AGENT("new-agt"),
    AGT_BALANCE("agt-bal");

    private String type;

    RequestTypeEnum(String type) {
        this.type = type;
    }


    public static RequestTypeEnum fromString(String text) {
        if (StringUtils.isBlank(text)) return null;
        for (RequestTypeEnum item : RequestTypeEnum.values()) {
            if (text.equalsIgnoreCase(item.type)) {
                return item;
            }
        }
        return null;
    }
}

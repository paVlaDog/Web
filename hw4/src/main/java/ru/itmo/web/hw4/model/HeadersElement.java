package ru.itmo.web.hw4.model;

public class HeadersElement {
    private final String href;
    private final String name;

    public HeadersElement(String href, String name) {
        this.href = href;
        this.name = name;
    }

    public String getHref() {
        return href;
    }

    public String getName() {
        return name;
    }
}

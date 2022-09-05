package com.newsprovider.portal.model.enums;

import lombok.Getter;

public enum Language {

    Arabic("ar"),
    Bengali("bn"),
    Bosnian("bs"),
    Bulgarian("bg"),
    Chinese("zh"),
    Croatian("hr"),
    Czech("cs"),
    Dutch("nl"),
    English("en"),
    French("fr"),
    German("de"),
    Greek("el"),
    Hebrew("he"),
    Hindi("hi"),
    Hungarian("hu"),
    Indonesian("in"),
    Italian("it"),
    Japanese("jp"),
    Korean("ko"),
    Latvian("lv"),
    Lithuanian("lt"),
    Malay("ms"),
    Norwegian("no"),
    Polish("pl"),
    Portuguese("pt"),
    Romanian("ro"),
    Russian("ru"),
    Serbian("sr"),
    Slovak("sk"),
    Slovenian("sl"),
    Spanish("es"),
    Swedish("sv"),
    Thai("th"),
    Turkish("tr"),
    Ukrainian("uk");

    @Getter
    private final String initials;

    Language(String initials) {
        this.initials = initials;
    }
}

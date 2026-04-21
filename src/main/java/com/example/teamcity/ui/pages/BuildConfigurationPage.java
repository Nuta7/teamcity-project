package com.example.teamcity.ui.pages;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$;

public class BuildConfigurationPage extends BasePage{

    private static final String BUILD_CONFIGURATION_URL = "/buildConfiguration/%s#all-projects";

    public SelenideElement title = $("h1").$("span:not([data-test='ring-icon'])");

    public static BuildConfigurationPage open(String buildTypeId) {
        return Selenide.open(BUILD_CONFIGURATION_URL.formatted(buildTypeId), BuildConfigurationPage.class);
    }
}

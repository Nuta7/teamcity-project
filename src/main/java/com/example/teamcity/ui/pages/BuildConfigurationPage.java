package com.example.teamcity.ui.pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import com.example.teamcity.ui.pages.admin.EditBuildConfigurationPage;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;

public class BuildConfigurationPage extends BasePage{

    private static final String BUILD_CONFIGURATION_URL = "/buildConfiguration/%s#all-projects";

    public SelenideElement title = $("h1").$("span:not([data-test='ring-icon'])");

    public SelenideElement settingsButton = $("[class*='ToggleLink-module__label']");

    public static BuildConfigurationPage open(String buildTypeId) {
        return Selenide.open(BUILD_CONFIGURATION_URL.formatted(buildTypeId), BuildConfigurationPage.class);
    }

    public EditBuildConfigurationPage clickSettingsButton(){
        settingsButton.shouldBe(Condition.visible, BASE_WAITING).shouldHave(text("Settings")).click();
        return Selenide.page(EditBuildConfigurationPage.class);
    }
}

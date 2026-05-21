package com.example.teamcity.ui.pages.admin;

import com.codeborne.selenide.Selectors;
import com.codeborne.selenide.SelenideElement;
import com.example.teamcity.ui.pages.BasePage;

import static com.codeborne.selenide.Selenide.$;

public class EditBasePage extends BasePage {
    protected static final String EDIT_BUILD_URL = "/admin/editBuild.html?id=buildType/%s";
    protected static final String EDIT_RUN_TYPE_URL = "/admin/editRunType.html?id=buildType:%s&runnerId=__NEW_RUNNER__";
    protected static final String EDIT_BUILD_RUNNERS_URL = "/admin/editBuildRunners.html?id=buildType:%s";

    protected SelenideElement saveButton = $(Selectors.byAttribute("value", "Save"));

}

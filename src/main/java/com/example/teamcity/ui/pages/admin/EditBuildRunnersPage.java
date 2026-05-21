package com.example.teamcity.ui.pages.admin;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.WebDriverConditions;
import com.example.teamcity.ui.pages.BuildPage;

import static com.codeborne.selenide.Selenide.$;

public class EditBuildRunnersPage extends EditBasePage{

    private SelenideElement runButton = $("span[class='ring-button-group-common ring-button-group-split']");
    private SelenideElement addBuildStepButton = $("span[class='icon_before icon16 addNew']");

    public static EditBuildRunnersPage open(String buildId) {
        return Selenide.open(EDIT_BUILD_RUNNERS_URL.formatted(buildId), EditBuildRunnersPage.class);
    }

    public BuildPage runBuild() {
        runButton.shouldBe(Condition.visible, BASE_WAITING).click();
        WebDriverConditions.urlContaining("/buildConfiguration/");
        return Selenide.page(BuildPage.class);
    }

    public void addBuildStep(){
        addBuildStepButton.shouldBe(Condition.visible, BASE_WAITING).click();
    }

}

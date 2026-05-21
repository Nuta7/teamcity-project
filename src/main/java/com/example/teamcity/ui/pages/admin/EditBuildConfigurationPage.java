package com.example.teamcity.ui.pages.admin;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$;

public class EditBuildConfigurationPage extends EditBasePage {

    private SelenideElement addBuildStepButton = $("span[class='icon_before icon16 addNew']");
    private SelenideElement buildStepsButton = $("[data-hint-container-id='runType']");


    public static EditBuildConfigurationPage open(String buildId) {
        return Selenide.open(EDIT_BUILD_URL.formatted(buildId), EditBuildConfigurationPage.class);
    }

    public EditBuildRunnersPage clickBuildStepsButton() {
        buildStepsButton.shouldBe(Condition.visible, BASE_WAITING).click();
        return new EditBuildRunnersPage();
    }

}

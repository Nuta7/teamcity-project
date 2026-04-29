package com.example.teamcity.ui.pages.admin;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selectors;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$;

public class EditRunTypePage extends EditBasePage{

    private SelenideElement addBuildStepName = $("#buildStepName");
    SelenideElement codeMirror = $(".CodeMirror");

    public static EditRunTypePage open(String buildId) {
        return Selenide.open(EDIT_RUN_TYPE_URL.formatted(buildId), EditRunTypePage.class);
    }

    public void clickOnCommandLine(){
        $(Selectors.withText("Command Line")).shouldBe(Condition.visible).click();
    }

    public void createBuildStep(String name, String script){
    addBuildStepName.val(name);
    codeMirror.shouldBe(Condition.visible).click();
    Selenide.actions().sendKeys(script).perform();
    saveButton.click();
    }
}

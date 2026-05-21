package com.example.teamcity.ui.pages;

import com.codeborne.selenide.*;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;


public class BuildPage extends BasePage {

    private SelenideElement buildLogsButton = $(Selectors.byText("Build Log"));
    private ElementsCollection logs = $$("[class*='LogMessage-module__text']");
    private SelenideElement expandAllButton = $("[data-test-full-build-log='expand']");

    public BuildPage() {
    }

    public BuildPage clickBuildLogTab() {
        buildLogsButton.shouldBe(visible, BASE_WAITING).click();
        return this;
    }

    public String checkLogs(String expectedMessage) {
        expandAllButton.shouldBe(visible, BASE_WAITING).click();

        return logs
                .filter(text(expectedMessage))
                .find(not(text("Step 1/1")))
                .shouldBe(visible, BASE_WAITING)
                .getText()
                .trim();

    }
}

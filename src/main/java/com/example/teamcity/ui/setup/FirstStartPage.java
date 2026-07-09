package com.example.teamcity.ui.setup;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import com.example.teamcity.ui.pages.BasePage;

import static com.codeborne.selenide.Selenide.$;

public class FirstStartPage extends BasePage {
    private final SelenideElement restoreButton = $("#restoreButton");
    private final SelenideElement proceedButton = $("#proceedButton");
    private final SelenideElement dbTypeSelect = $("#dbType");
    private final SelenideElement acceptLicenseCheckbox = $("#accept");
    private final SelenideElement acceptButton = $("#acceptLicenseAgreement");

    public FirstStartPage() {
        restoreButton.shouldBe(Condition.visible, LONG_WAITING);
    }

    public static FirstStartPage open() {
        return Selenide.open("/", FirstStartPage.class);
    }

    public FirstStartPage setupFirstStart() {
        $("body").shouldBe(Condition.visible, LONG_WAITING);
        Selenide.sleep(3000);
        if (proceedButton.is(Condition.visible)) {
            proceedButton.click();
            dbTypeSelect.shouldBe(Condition.visible, LONG_WAITING);
            proceedButton.click();
            Selenide.sleep(15000);
            com.codeborne.selenide.WebDriverRunner.getWebDriver().navigate().refresh();
        }
        acceptButton.shouldBe(Condition.visible, LONG_WAITING);
        com.codeborne.selenide.Selenide.executeJavaScript("arguments[0].click();", acceptButton);

        return this;
    }
}
package com.example.teamcity.ui.pages.admin;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$;

public class CreateBuildConfigurationPage extends CreateBasePage{
    private static final String BUILD_SHOW_MODE = "createBuildTypeMenu";

    private SelenideElement buildTypeNameInput = $("#buildTypeName");
    private SelenideElement branchInput = $("#branch");

    public static CreateBuildConfigurationPage open(String projectId) {
        return Selenide.open(CREATE_URL.formatted(projectId, BUILD_SHOW_MODE), CreateBuildConfigurationPage.class);
    }

    public CreateBuildConfigurationPage createForm(String url) {
        baseCreateForm(url);
        return this;
    }

    public void setupBuildConfiguration(String buildTypeName, String branch) {
        buildTypeNameInput.val(buildTypeName);
        branchInput.val(branch);
        submitButton.click();
    }

    public static String transformId(String projectId, String buildName) {
        String noUnderscore = buildName.replace("_", "");
        String capitalizedName = noUnderscore.substring(0, 1).toUpperCase() + noUnderscore.substring(1);
        return projectId + "_" + capitalizedName;
    }

}

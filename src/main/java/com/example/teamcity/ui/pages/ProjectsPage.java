package com.example.teamcity.ui.pages;


import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import com.example.teamcity.ui.elements.ProjectElement;
import org.openqa.selenium.By;

import java.util.List;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class ProjectsPage extends BasePage {
    private static final String PROJECTS_URL = "/favorite/projects";

    private ElementsCollection projectElements = $$("div[class*='Subproject-module__container']");

    private SelenideElement spanFavoriteProjects = $("span[class='ProjectPageHeader__title--ih']");

    private SelenideElement headerLogin = $("[class*='MainPanel-module__router']");
    private SelenideElement headerProjectsPage = $("[class*='ProjectPageHeader-module__title']");

    private static SelenideElement searchField = $("[data-test='sidebar-search']");

    // ElementCollection -> List<ProjectElement>
    // UI elements -> List<Object>
    // ElementCollection -> List<BasePageElement>


    public static ProjectsPage open() {
        return Selenide.open(PROJECTS_URL, ProjectsPage.class);
    }

    public ProjectsPage() {
    }


    public ProjectsPage waitUntilPageIsLoaded() {
        headerLogin.shouldBe(visible, BASE_WAITING);
        headerProjectsPage.shouldBe(Condition.visible, BASE_WAITING);
        return this;
    }

    public List<ProjectElement> getProjects() {
        projectElements.shouldHave(com.codeborne.selenide.CollectionCondition.sizeGreaterThan(0));
        return generatePageElements(projectElements, ProjectElement::new);
    }

    public static ProjectPage searchProjectByName(String projectName) {
        searchField.val(projectName).pressEnter();
        $(By.linkText(projectName)).shouldBe(visible, BASE_WAITING).click();
        return new ProjectPage();
    }
}
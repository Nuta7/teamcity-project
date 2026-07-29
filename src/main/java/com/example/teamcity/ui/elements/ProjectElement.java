package com.example.teamcity.ui.elements;

import com.codeborne.selenide.SelenideElement;
import lombok.Getter;

@Getter
public class ProjectElement extends  BasePageElement {
    private SelenideElement name;
    private SelenideElement link;
    private SelenideElement button;

    public ProjectElement(SelenideElement element) {
        super(element);
        this.link = find("a[href*='projectId=']");
        this.button = find("button");
        this.name = this.link;
    }
}
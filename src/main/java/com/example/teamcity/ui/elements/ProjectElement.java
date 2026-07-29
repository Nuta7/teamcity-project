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
        this.link = element.$("a");
        this.button = find("button");
        this.name = element.$("a[class*='name'], span[class*='name'], a");
    }
}
package com.javarush.task.task28.task2810.view;

import com.javarush.task.task28.task2810.Controller;
import com.javarush.task.task28.task2810.vo.Vacancy;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.*;
import java.nio.file.Path;
import java.util.List;

public class HtmlView implements View {
    private Controller controller;
    private final String filePath = "./4.JavaCollections/src/" + this.getClass().getPackage().getName().replace(".", "/") + "/vacancies.html";

    @Override
    public void update(List<Vacancy> vacancies) {
        System.out.println(filePath);
        try {
            updateFile(getUpdatedFileContent(vacancies));
        }
        catch (Exception e) {
            System.out.println(e);
        }
    }

    @Override
    public void setController(Controller controller) {
        this.controller = controller;
    }

    public void userCitySelectEmulationMethod() {
        controller.onCitySelect("Odessa");
    }

    private String getUpdatedFileContent(List<Vacancy> vacancies) throws Exception {
        Document document = getDocument();
        Element elements = document.getElementsByClass("template").first();
        Element elementsCopy = elements.clone();
        elementsCopy.removeAttr("style");
        elementsCopy.removeClass("template");
        document.select("tr[class=vacancy]").remove().not("tr[class=vacancy template");
        return null;
    }

    private void updateFile(String str) throws Exception {
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(filePath)));
        writer.write(str);
        writer.close();
    }

    Document getDocument() throws IOException {
        return Jsoup.parse(new File(filePath), "UTF-8");
    }
}

package com.javarush.task.task28.task2810.model;

import com.javarush.task.task28.task2810.vo.Vacancy;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

//Этот класс будет реализовывать конкретную стратегию работы с сайтом ХэдХантер (http://hh.ua/ и http://hh.ru/).
public class HHStrategy implements Strategy {
    private static final String URL_FORMAT = "http://hh.ru/search/vacancy?text=java+%s&page=%d";
   // private static final String URL_FORMAT = "https://hh.ua/vacancy/30668347";
   // private static final String URL_FORMAT = "http://javarush.ru/testdata/big28data.html";
  //  private static final String URL_FORMAT = "http://hh.ua/search/vacancy?text=java+Киев&page=1";

    @Override
    public List<Vacancy> getVacancies(String searchString) {
        List<Vacancy> vacancies = new ArrayList<>();
        int page = 0;
        Document document = null;
        try {
            Elements elements = null;
            while (true){
                document = getDocument(searchString, page);
                elements = document.getElementsByAttributeValue("data-qa", "vacancy-serp__vacancy");
                if (elements.isEmpty()) {
                    page++;
                    break;
                }
                for (Element element : elements) {
                    Vacancy vacancy = new Vacancy();
                    vacancy.setTitle(element.getElementsByAttributeValue("data-qa", "vacancy-serp__vacancy-title").text());
                    String salary = element.getElementsByAttributeValue("data-qa", "vacancy-serp__vacancy-compensation").text();
                    vacancy.setSalary(salary.length() == 0 ? "" : salary);
                    vacancy.setCity(element.getElementsByAttributeValue("data-qa", "vacancy-serp__vacancy-address").text());
                    vacancy.setCompanyName(element.getElementsByAttributeValue("data-qa", "vacancy-serp__vacancy-employer").text());
                    vacancy.setSiteName(URL_FORMAT);
                    vacancy.setUrl(element.getElementsByAttributeValue("data-qa", "vacancy-serp__vacancy-title").attr("href"));
                    vacancies.add(vacancy);
                }
                page++;
            }

        }
        catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return vacancies;
    }

    protected Document getDocument(String searchString, int page) throws IOException {
            String url = String.format(URL_FORMAT, searchString, page);
            Document doc = Jsoup.connect(url).
                    userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/72.0.3626.121 Safari/537.36").
                    referrer("").get();
            //String html = doc.html();
            //  System.out.println(html);
        return doc;
    }
}

package com.javarush.test.level28.lesson15.big01.model;

import com.javarush.test.level28.lesson15.big01.vo.Vacancy;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class HHStrategy implements Strategy
{
    private static final String URL_FORMAT = "http://hh.ua/search/vacancy?text=java+%s&page=%d";

    @Override
    public List<Vacancy> getVacancies(String searchString)
    {
        List<Vacancy> vacancyList = new ArrayList<>();
        int page = 0;
        try
        {
            while (true)
            {
                Document doc = getDocument(searchString, page++);

                if (doc == null) break;

                Elements elements = doc.getElementsByAttributeValue("data-qa","vacancy-serp__vacancy");
                if (elements.isEmpty()) break;

                for (Element e : elements)
                {
                    Vacancy vacancy = new Vacancy();
                    vacancy.setSiteName(doc.title());
                    vacancy.setTitle(e.getElementsByAttributeValue("data-qa","vacancy-serp__vacancy-title").text());
                    vacancy.setCity(e.getElementsByAttributeValue("data-qa","vacancy-serp__vacancy-address").text());
                    vacancy.setCompanyName(e.getElementsByAttributeValue("data-qa","vacancy-serp__vacancy-employer").text());
                    vacancy.setUrl(e.getElementsByAttributeValue("data-qa","vacancy-serp__vacancy-title").attr("href"));
                    vacancy.setSalary(e.getElementsByAttributeValue("data-qa","vacancy-serp__vacancy-compensation").text());

                    vacancyList.add(vacancy);
                }
            }
        }
        catch (IOException e)
        {
        }
        return vacancyList;
    }

    protected Document getDocument(String searchString, int page) throws IOException
    {
        String url = String.format(URL_FORMAT, searchString, page);

        Document doc = Jsoup.connect(url)
                .userAgent("Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/55.0.2883.87 Safari/537.36")
                .referrer("http://google.ru")
                .get();

        return doc;
    }
}

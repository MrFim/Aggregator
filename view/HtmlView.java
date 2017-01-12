package com.javarush.test.level28.lesson15.big01.view;


import com.javarush.test.level28.lesson15.big01.Controller;
import com.javarush.test.level28.lesson15.big01.vo.Vacancy;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.*;
import java.util.List;

public class HtmlView implements View
{
    Controller controller;
    private final String filePath = "./src/" + this.getClass().getPackage().getName().replaceAll("\\.", "/") + "/vacancies.html";
    @Override
    public void update(List<Vacancy> vacancies)
    {
        try{
            updateFile(getUpdatedFileContent(vacancies));
        }catch(Exception e)
        {
            e.printStackTrace();
            System.out.println("Some exception occurred");
        }
    }
    private String getUpdatedFileContent(List<Vacancy> list) throws IOException
    {
        Document doc = getDocument();
        Element element = doc.getElementsByClass("template").first();
        Element cloneElement = element.clone();
        cloneElement.removeClass("template").removeAttr("style");
        doc.getElementsByAttributeValue("class", "vacancy").remove();

        for (Vacancy vacancy : list)
        {
            Element copyClone = cloneElement.clone();
            copyClone.getElementsByAttributeValue("class", "city").get(0).text(vacancy.getCity());
            copyClone.getElementsByAttributeValue("class", "companyName").get(0).text(vacancy.getCompanyName());
            copyClone.getElementsByAttributeValue("class", "salary").get(0).text(vacancy.getSalary());
            Element ref = copyClone.getElementsByTag("a").get(0).attr("href", vacancy.getUrl()).text(vacancy.getTitle());
            element.before(copyClone.outerHtml());
        }
        return doc.html();
    }
    private void updateFile(String s) throws IOException
    {
        PrintWriter printWriter = new PrintWriter(new OutputStreamWriter(new FileOutputStream(filePath), "UTF-8"));
        printWriter.write(s);
        printWriter.close();
    }

    @Override
    public void setController(Controller controller)
    {
        this.controller = controller;
    }
    public void userCitySelectEmulationMethod()
    {
        controller.onCitySelect("Odessa");
    }
    protected Document getDocument() throws IOException
    {
        Document doc = Jsoup.parse(new File(filePath), "UTF-8");
        return doc;
    }
}

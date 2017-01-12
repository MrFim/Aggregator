package com.javarush.test.level28.lesson15.big01;


import com.javarush.test.level28.lesson15.big01.model.Model;


public class Controller
{
    private Model model;

    public Controller(Model model)
    {
        if (model == null)
            throw new IllegalArgumentException();
        this.model = model;
    }
    public void onCitySelect(String cityName)
    {
        model.selectCity(cityName);
    }
}
//    List<Vacancy> list = new ArrayList<>();
//try
//        {
//        for (Provider p : providers)
//        {
//        for (Vacancy v : p.getJavaVacancies("Java"))
//        {
//        list.add(v);
//        }
//        }
//        }
//        catch (Exception e)
//        {
//        }
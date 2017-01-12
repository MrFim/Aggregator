package com.javarush.test.level28.lesson15.big01.model;

import com.javarush.test.level28.lesson15.big01.view.View;
import com.javarush.test.level28.lesson15.big01.vo.Vacancy;

import java.util.ArrayList;
import java.util.List;

public class Model
{
    View view;
    Provider[] providers;

    public Model(View view, Provider[] providers)
    {
        if (view == null || providers == null || providers.length == 0)
            throw new IllegalArgumentException();
        else
        {
            this.view = view;
            this.providers = providers;
        }
    }
    public void selectCity(String city)
    {
        List<Vacancy> list = new ArrayList<>();
        for (Provider p : providers)
        {
            list.addAll(p.getJavaVacancies(city));
        }
        view.update(list);
    }
}

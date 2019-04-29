package com.javarush.task.task28.task2810.model;

import com.javarush.task.task28.task2810.view.View;
import com.javarush.task.task28.task2810.vo.Vacancy;
import com.javarush.task.task28.task2810.model.Provider;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Model {
    private View view;
    private Provider[] providers;

    public Model(View view, Provider... providers) {
            if (providers == null || providers.length == 0 || view == null) throw new IllegalArgumentException();
                this.providers = providers;
                this.view = view;
    }

    public void selectCity(String city) {
        List<Vacancy> vacancies = new ArrayList<>();
        Arrays.stream(providers).map(p -> p.getJavaVacancies(city))
                .forEach(vacancies::addAll);
        view.update(vacancies);
    }
}

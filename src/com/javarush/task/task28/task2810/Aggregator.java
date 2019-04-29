package com.javarush.task.task28.task2810;

import com.javarush.task.task28.task2810.model.HHStrategy;
import com.javarush.task.task28.task2810.model.Model;
import com.javarush.task.task28.task2810.model.Provider;
import com.javarush.task.task28.task2810.view.HtmlView;
import com.javarush.task.task28.task2810.view.View;


import java.lang.reflect.Array;


public class Aggregator {

    public static void main(String[] args) {
        View view = new HtmlView();
        Provider provider = new Provider(new HHStrategy());
        Provider[] providers = new Provider[1];
        providers[0] = provider;
        Model model = new Model(view, providers);
        Controller controller = new Controller(model);
        view.setController(controller);
        ((HtmlView) view).userCitySelectEmulationMethod();
    }
}

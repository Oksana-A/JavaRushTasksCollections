package com.javarush.task.task33.task3305;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

/*
Конвертация из одного класса в другой используя JSON
{
  "className" : ".Parking",
  "name" : "Super Parking",
  "city" : "Kyiv",
  "autos" : [ {
    "className" : "com.javarush.task.task33.task3305.RaceBike",
    "name" : "Simba",
    "owner" : "Peter",
    "age" : 2
  }, {
    "className" : "com.javarush.task.task33.task3305.Motorbike",
    "name" : "Manny",
    "owner" : null
  }, {
    "className" : "com.javarush.task.task33.task3305.Car"
  } ]
}

*/

public class Solution {
    public static void main(String[] args) throws IOException {
        Parking parking = new Parking("Super Parking", "Kyiv");
        RaceBike raceBike = new RaceBike("Simba", "Peter", 2);
        Motorbike motorbike = new Motorbike("Manny");
        Car car = new Car();
        List<Auto> autos = new ArrayList<>();
        autos.add(raceBike);
        autos.add(motorbike);
        autos.add(car);
        parking.setAutos(autos);
        convertToJson(parking);
    }

    public static void convertToJson(Parking parking) throws IOException {
        StringWriter writer = new StringWriter();
        ObjectMapper mapper = new ObjectMapper();
        mapper.writerWithDefaultPrettyPrinter().writeValue(writer, parking);
        System.out.println(writer.toString());
    }
}

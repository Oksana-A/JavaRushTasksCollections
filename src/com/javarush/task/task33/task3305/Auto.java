package com.javarush.task.task33.task3305;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS,  property="className")
//@JsonSubTypes(@JsonSubTypes.Type(value=Auto.class,  name="Auto"))
public abstract class Auto {
    protected String name;
    protected String owner;
    protected int age;
}

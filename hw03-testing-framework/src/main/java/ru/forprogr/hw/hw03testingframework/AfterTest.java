package ru.forprogr.hw.hw03testingframework;
//-----------------------------------------------------------------------------
// Author:    Nemti
// Created:   28.04.2019 15:56
// Copyright: (c) Nemti 2019
// Licence:   GPL 3.0
//-----------------------------------------------------------------------------
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface AfterTest {
}

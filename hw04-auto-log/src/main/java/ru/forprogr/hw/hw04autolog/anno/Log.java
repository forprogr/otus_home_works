package ru.forprogr.hw.hw04autolog.anno;
//-----------------------------------------------------------------------------
// Author:    Nemti
// Created:   05.05.2019 10:12
// Copyright: (c) Nemti 2019
// Licence:   GPL 3.0
//-----------------------------------------------------------------------------
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Log {

}

package com.example.whatstocleanbackend;

import lombok.NoArgsConstructor;
import org.junit.jupiter.api.DisplayNameGenerator;

import java.lang.reflect.Method;

@NoArgsConstructor
public class DisplayNameGeneratorCustom extends DisplayNameGenerator.Standard {

    public String generateDisplayNameForClass(Class<?> testClass) {
        return this.replace(super.generateDisplayNameForClass(testClass));
    }

    public String generateDisplayNameForNestedClass(Class<?> nestedClass) {
        return this.replace(super.generateDisplayNameForNestedClass(nestedClass));
    }

    public String generateDisplayNameForMethod(Class<?> testClass, Method testMethod) {
        return this.replace(testMethod.getName());
    }

    private String replace(String name) {
        String newName = name
                .replaceAll("([A-Z])", " $1")
                .replaceAll("_", " ");
        return newName;
    }
}
package com.endava.internship.collections;

import java.time.LocalDate;
import java.util.Objects;

/**
 * The class that defines the element that will be contained by your collection
 */
public class Student {
    final String name;
    final LocalDate dateOfBirth;
    private String details;

    public Student(final String name, final LocalDate dateOfBirth,final String details) {
        this.name = name;
        this.dateOfBirth = dateOfBirth;
        this.details = details;
    }

    public String getName() {
        return name;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDetails(final String details) {
        this.details = details;
    }

    public String getDetails() {
        return details;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, dateOfBirth);
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj)
            return true;
        if (Objects.isNull(obj))
            return false;
        if (getClass() != obj.getClass())
            return false;
        return name.equals(((Student) obj).name) && dateOfBirth.equals(((Student) obj).dateOfBirth);
    }

    @Override
    public String toString() {
        return "Student:[ " + name + ", Birthday: " + dateOfBirth.toString() + ", " + details + "]";
    }

}

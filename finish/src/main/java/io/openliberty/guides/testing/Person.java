// tag::copyright[]
/*******************************************************************************
 * Copyright (c) 2019, 2022 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - Initial implementation
 *******************************************************************************/
// end::copyright[]
package io.openliberty.guides.testing;

import java.util.Objects;
import java.util.Random;

import jakarta.json.bind.annotation.JsonbCreator;
import jakarta.json.bind.annotation.JsonbProperty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;

public class Person {

    private static final Random R = new Random();

    @NotNull
    public final long id;

    @NotNull
    @Size(min = 2, max = 50)
    public final String name;

    @NotNull
    @PositiveOrZero
    public final int age;

    public Person(String name, int age) {
        this(name, age, null);
    }

    @JsonbCreator
    public Person(@JsonbProperty("name") String name,
            @JsonbProperty("age") int age,
            @JsonbProperty("id") Long id) {
        this.name = name;
        this.age = age;
        this.id = id == null ? R.nextLong() : id;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null || !(obj instanceof Person)) {
            return false;
        }
        Person other = (Person) obj;
        return Objects.equals(id, other.id)
            && Objects.equals(name, other.name)
            && Objects.equals(age, other.age);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, age);
    }
}

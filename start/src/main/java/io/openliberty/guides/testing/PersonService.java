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
 * See the NOTICE file(s) distributed with this work for additional
 * information regarding copyright ownership.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * You may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *******************************************************************************/
// end::copyright[]
package io.openliberty.guides.testing;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;

@Path("/people")
@ApplicationScoped
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PersonService {

    private final Map<Long, Person> personRepo = new HashMap<>();

    @GET
    public Collection<Person> getAllPeople() {
        return personRepo.values();
    }

    @GET
    @Path("/{personId}")
    public Person getPerson(@PathParam("personId") long id) {
        Person foundPerson = personRepo.get(id);
        if (foundPerson == null) {
            personNotFound(id);
        }
        return foundPerson;
    }

    @POST
    public Long createPerson(@QueryParam("name") @NotEmpty @Size(min = 2, max = 50)
                              String name,
                             @QueryParam("age") @PositiveOrZero int age) {
        Person p = new Person(name, age);
        personRepo.put(p.id, p);
        return p.id;
    }

    @POST
    @Path("/{personId}")
    public void updatePerson(@PathParam("personId") long id, @Valid Person p) {
        Person toUpdate = getPerson(id);
        if (toUpdate == null) {
            personNotFound(id);
        }
        personRepo.put(id, p);
    }

    @DELETE
    @Path("/{personId}")
    public void removePerson(@PathParam("personId") long id) {
        Person toDelete = personRepo.get(id);
        if (toDelete == null) {
            personNotFound(id);
        }
        personRepo.remove(id);
    }

    private void personNotFound(long id) {
        throw new NotFoundException("Person with id " + id + " not found.");
    }

}

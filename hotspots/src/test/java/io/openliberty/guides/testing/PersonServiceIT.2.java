// tag::copyright[]
/*
 * Copyright (c) 2019 IBM Corporation and others
 *
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
 */
// end::copyright[]
package io.openliberty.guides.testing;

// tag::importInject[]
import javax.inject.Inject;
// end::importInject[]

// tag::importMicroShedTest[]
import org.microshed.testing.jupiter.MicroShedTest;
// end::importMicroShedTest[]
// tag::importSharedContainerConfig[]
import org.microshed.testing.SharedContainerConfig;
// end::importSharedContainerConfig[]
// tag::importMPApp[]
import org.microshed.testing.testcontainers.MicroProfileApplication;
// end::importMPApp[]
// tag::importContainer[]
import org.testcontainers.junit.jupiter.Container;
// end::importContainer[]

// tag::importAssertNotNull[]
import static org.junit.jupiter.api.Assertions.assertNotNull;
// end::importAssertNotNull[]
import org.junit.jupiter.api.Test;

// tag::microShedTest[]
@MicroShedTest
// end::microShedTest[]
// tag::sharedContainerConfig[]
@SharedContainerConfig(AppDeploymentConfig.class)
// end::sharedContainerConfig[]
public class PersonServiceIT {

    // tag::inject[]
    @Inject
    // end::inject[]
    // tag::personSvc[]
    public static PersonService personSvc;
    // end::personSvc[]

    // tag::container[]
    @Container
    // end::container[]
    // tag::mpApp[]
    public static MicroProfileApplication app = new MicroProfileApplication()
                    // tag::withAppContextRoot[]
                    .withAppContextRoot("/guide-microshed-testing")
                    // end::withAppContextRoot[]
                    // tag::withReadinessPath[]
                    .withReadinessPath("/health/ready");
                    // end::withReadinessPath[]
    // end::mpApp[]

    @Test
    public void testCreatePerson() {
        // tag::testCreatePerson[]
        Long createId = personSvc.createPerson("Hank", 42);
        assertNotNull(createId);
        // end::testCreatePerson[]
    }

    // tag::tests[]
    // tag::testMinSizeName[]
    @Test
    public void testMinSizeName() {
        Long minSizeNameId = personSvc.createPerson("Ha", 42);
        assertEquals(new Person("Ha", 42, minSizeNameId),
                     personSvc.getPerson(minSizeNameId));
    }
    // end::testMinSizeName[]

    // tag::testMinAge[]
    @Test
    public void testMinAge() {
        Long minAgeId = personSvc.createPerson("Newborn", 0);
        assertEquals(new Person("Newborn", 0, minAgeId),
                     personSvc.getPerson(minAgeId));
    }
    // end::testMinAge[]

    // tag::testGetPerson[]
    @Test
    public void testGetPerson() {
        Long bobId = personSvc.createPerson("Bob", 24);
        Person bob = personSvc.getPerson(bobId);
        assertEquals("Bob", bob.name);
        assertEquals(24, bob.age);
        assertNotNull(bob.id);
    }
    // end::testGetPerson[]

    // tag::testGetAllPeople[]
    @Test
    public void testGetAllPeople() {
        Long person1Id = personSvc.createPerson("Person1", 1);
        Long person2Id = personSvc.createPerson("Person2", 2);

        Person expected1 = new Person("Person1", 1, person1Id);
        Person expected2 = new Person("Person2", 2, person2Id);

        Collection<Person> allPeople = personSvc.getAllPeople();
        assertTrue(allPeople.size() >= 2,
            "Expected at least 2 people to be registered, but there were only: " +
            allPeople);
        assertTrue(allPeople.contains(expected1),
            "Did not find person " + expected1 + " in all people: " + allPeople);
        assertTrue(allPeople.contains(expected2),
            "Did not find person " + expected2 + " in all people: " + allPeople);
    }
    // end::testGetAllPeople[]

    // tag::testUpdateAge[]
    @Test
    public void testUpdateAge() {
        Long personId = personSvc.createPerson("newAgePerson", 1);

        Person originalPerson = personSvc.getPerson(personId);
        assertEquals("newAgePerson", originalPerson.name);
        assertEquals(1, originalPerson.age);
        assertEquals(personId, Long.valueOf(originalPerson.id));

        personSvc.updatePerson(personId,
            new Person(originalPerson.name, 2, originalPerson.id));
        Person updatedPerson = personSvc.getPerson(personId);
        assertEquals("newAgePerson", updatedPerson.name);
        assertEquals(2, updatedPerson.age);
        assertEquals(personId, Long.valueOf(updatedPerson.id));
    }
    // end::testUpdateAge[]
    // end::tests[]
}

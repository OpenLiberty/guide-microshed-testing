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

import static org.junit.jupiter.api.Assertions.assertThrows;

import javax.ws.rs.BadRequestException;
import javax.ws.rs.NotFoundException;

import org.junit.jupiter.api.Test;
// tag::importSharedContainerConfig[]
import org.microshed.testing.SharedContainerConfig;
// end::importSharedContainerConfig[]
import org.microshed.testing.jaxrs.RESTClient;
import org.microshed.testing.jupiter.MicroShedTest;

@MicroShedTest
// tag::sharedContainerConfig[]
@SharedContainerConfig(AppDeploymentConfig.class)
// end::sharedContainerConfig[]
public class ErrorPathIT {
    
    @RESTClient
    public static PersonService personSvc;
    
    @Test
    public void testGetUnknownPerson() {
        assertThrows(NotFoundException.class, () -> personSvc.getPerson(-1L));
    }

    @Test
    public void testCreateBadPersonNullName() {
        assertThrows(BadRequestException.class, () -> personSvc.createPerson(null, 5));
    }

    @Test
    public void testCreateBadPersonNegativeAge() {
        assertThrows(BadRequestException.class, () -> 
          personSvc.createPerson("NegativeAgePersoN", -1));
    }

    @Test
    public void testCreateBadPersonNameTooLong() {
        assertThrows(BadRequestException.class, () ->
           personSvc.createPerson("NameTooLongPersonNameTooLongPersonNameTooLongPerson", 
           5));
    }
}

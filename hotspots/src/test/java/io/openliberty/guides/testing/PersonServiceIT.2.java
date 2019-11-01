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

import org.junit.jupiter.api.Test;
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

}

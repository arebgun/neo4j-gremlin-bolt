/*
 *  Copyright 2016 SteelBridge Laboratories, LLC.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 *  For more information: http://steelbridgelabs.com
 */

package com.steelbridgelabs.oss.neo4j.structure.providers;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.neo4j.driver.Driver;

import java.util.Date;

/**
 * @author Rogelio J. Baucells
 */
@RunWith(MockitoJUnitRunner.class)
public class DatabaseSequenceElementIdProviderWhileProcessingIdentifierTest {

    @Mock
    private Driver driver;

    @Test
    public void givenStringIdentifierShouldReturnLong() {
        // arrange
        DatabaseSequenceElementIdProvider provider = new DatabaseSequenceElementIdProvider(driver, 2, "field1", "label");
        // act
        Long id = provider.processIdentifier("1");
        // assert
        Assert.assertNotNull("Invalid identifier value", id);
        Assert.assertTrue("Provider returned an invalid identifier value", id == 1L);
    }

    @Test
    public void givenIntegerIdentifierShouldReturnLong() {
        // arrange
        DatabaseSequenceElementIdProvider provider = new DatabaseSequenceElementIdProvider(driver, 2, "field1", "label");
        // act
        Long id = provider.processIdentifier(1);
        // assert
        Assert.assertNotNull("Invalid identifier value", id);
        Assert.assertTrue("Provider returned an invalid identifier value", id == 1L);
    }

    @Test
    public void givenLongIdentifierShouldReturnLong() {
        // arrange
        DatabaseSequenceElementIdProvider provider = new DatabaseSequenceElementIdProvider(driver, 2, "field1", "label");
        // act
        Long id = provider.processIdentifier(1L);
        // assert
        Assert.assertNotNull("Invalid identifier value", id);
        Assert.assertTrue("Provider returned an invalid identifier value", id == 1L);
    }

    @Test(expected = IllegalArgumentException.class)
    public void givenDateIdentifierShouldThrowException() {
        // arrange
        DatabaseSequenceElementIdProvider provider = new DatabaseSequenceElementIdProvider(driver, 2, "field1", "label");
        // act
        Long id = provider.processIdentifier(new Date());
        // assert
        Assert.assertNotNull("Invalid identifier value", id);
        Assert.assertTrue("Provider returned an invalid identifier value", id == 1L);
    }
}

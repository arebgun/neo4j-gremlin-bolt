/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package com.steelbridgelabs.oss.neo4j.structure.providers;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.neo4j.driver.v1.Driver;
import org.neo4j.driver.v1.Record;
import org.neo4j.driver.v1.Session;
import org.neo4j.driver.v1.Statement;
import org.neo4j.driver.v1.StatementResult;
import org.neo4j.driver.v1.Transaction;
import org.neo4j.driver.v1.Values;


/**
 * @author Rogelio J. Baucells
 */
@RunWith(MockitoJUnitRunner.class)
public class DatabaseSequenceElementIdProviderWhileGeneratingId {

    @Test
    public void givenANewProviderShouldRequestPoolOfIdentifiers() {
        // arrange
        Record record = Mockito.mock(Record.class);
        Mockito.when(record.get(Mockito.eq(0))).thenAnswer(invocation -> Values.value(2));
        StatementResult result = Mockito.mock(StatementResult.class);
        Mockito.when(result.hasNext()).thenAnswer(invocation -> true);
        Mockito.when(result.next()).thenAnswer(invocation -> record);
        Transaction transaction = Mockito.mock(Transaction.class);
        Mockito.when(transaction.run(Mockito.any(Statement.class))).thenAnswer(invocation -> result);
        Session session = Mockito.mock(Session.class);
        Mockito.when(session.beginTransaction()).thenAnswer(invocation -> transaction);
        Driver driver = Mockito.mock(Driver.class);
        Mockito.when(driver.session()).thenAnswer(invocation -> session);
        DatabaseSequenceElementIdProvider provider = new DatabaseSequenceElementIdProvider(driver, 2, "field1", "label");
        // act
        Long id = provider.generateId();
        // assert
        Assert.assertNotNull("Invalid identifier value", id);
        Assert.assertTrue("Provider returned an invalid identifier value", id == 1L);
        // act
        id = provider.generateId();
        // assert
        Assert.assertNotNull("Invalid identifier value", id);
        Assert.assertTrue("Provider returned an invalid identifier value", id == 2L);
    }

    @Test
    public void givenTwoIdentifierRequestsShouldRequestPoolOfIdentifiers() {
        // arrange
        Record record = Mockito.mock(Record.class);
        Mockito.when(record.get(Mockito.eq(0))).thenAnswer(invocation -> Values.value(1));
        StatementResult result = Mockito.mock(StatementResult.class);
        Mockito.when(result.hasNext()).thenAnswer(invocation -> true);
        Mockito.when(result.next()).thenAnswer(invocation -> record);
        Transaction transaction = Mockito.mock(Transaction.class);
        Mockito.when(transaction.run(Mockito.any(Statement.class))).thenAnswer(invocation -> result);
        Session session = Mockito.mock(Session.class);
        Mockito.when(session.beginTransaction()).thenAnswer(invocation -> transaction);
        Driver driver = Mockito.mock(Driver.class);
        Mockito.when(driver.session()).thenAnswer(invocation -> session);
        DatabaseSequenceElementIdProvider provider = new DatabaseSequenceElementIdProvider(driver, 1, "field1", "label");
        // act
        Long id = provider.generateId();
        // assert
        Assert.assertNotNull("Invalid identifier value", id);
        Assert.assertTrue("Provider returned an invalid identifier value", id == 1L);
        // arrange
        Mockito.when(record.get(Mockito.eq(0))).thenAnswer(invocation -> Values.value(2));
        // act
        id = provider.generateId();
        // assert
        Assert.assertNotNull("Invalid identifier value", id);
        Assert.assertTrue("Provider returned an invalid identifier value", id == 2L);
    }
}

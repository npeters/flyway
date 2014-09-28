/**
 * Copyright 2010-2014 Axel Fontaine
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.flywaydb.core.internal.dbsupport.oracle;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Test for OracleSqlStatementBuilder.
 */
public class OracleSqlStatementBuilderSmallTest {
    private OracleSqlStatementBuilder builder = new OracleSqlStatementBuilder();

    @Test
    public void changeDelimiterRegEx() {
        assertNull(builder.changeDelimiterIfNecessary("BEGIN_DATE", null));
        assertEquals("/", builder.changeDelimiterIfNecessary("BEGIN DATE", null).getDelimiter());
        assertEquals("/", builder.changeDelimiterIfNecessary("BEGIN", null).getDelimiter());
    }

    @Test
    public void javaSource() {
        builder.addLine("CREATE OR REPLACE AND COMPILE JAVA SOURCE NAMED \"JavaTest\" AS");
        assertFalse(builder.isTerminated());
        builder.addLine("public class JavaTest {");
        assertFalse(builder.isTerminated());
        builder.addLine("};");
        assertFalse(builder.isTerminated());
        builder.addLine("/");
        assertTrue(builder.isTerminated());
    }

    @Test
    public void nvarchar() {
        builder.addLine("INSERT INTO nvarchar2_test VALUES ( N'qwerty' );");
        assertTrue(builder.isTerminated());
    }

    @Test
    public void notNvarchar() {
        builder.addLine("INSERT INTO nvarchar2_test VALUES ( ' N' );");
        assertTrue(builder.isTerminated());
    }
}

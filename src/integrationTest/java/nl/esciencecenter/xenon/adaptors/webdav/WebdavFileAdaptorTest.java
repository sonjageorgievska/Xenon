/**
 * Copyright 2013 Netherlands eScience Center
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package nl.esciencecenter.xenon.adaptors.webdav;

import org.junit.AfterClass;
import org.junit.BeforeClass;

import nl.esciencecenter.xenon.adaptors.GenericFileAdaptorTestParent;

/**
 * @author Christiaan Meijer <C.Meijer@esciencecenter.nl>
 *
 */
public class WebdavFileAdaptorTest extends GenericFileAdaptorTestParent {
    @BeforeClass
    public static void prepareWebdavFileAdaptorTest() throws Exception {
        GenericFileAdaptorTestParent.prepareClass(new WebdavFileTestConfig(null));
    }

    @AfterClass
    public static void cleanupWebdavFileAdaptorTest() throws Exception {
        GenericFileAdaptorTestParent.cleanupClass();
    }
}

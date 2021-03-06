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
package nl.esciencecenter.xenon.files;

/**
 * Path represents a specific location on a FileSystem, as by the FileSystems root plus a RelativePath. 
 * 
 * @version 1.0
 * @since 1.0
 */
public interface Path {

    /**
     * Get the FileSystem to which this Path refers.
     * 
     * @return the FileSystem.
     */
    FileSystem getFileSystem();

    /**
     * Get the location relative to the root of the FileSystem.
     * 
     * @return a RelativePath containing the location relative to the root of the FileSystem.
     */
    RelativePath getRelativePath();
}

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
package nl.esciencecenter.xenon.adaptors.slurm;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertTrue;

import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.UUID;

import nl.esciencecenter.xenon.XenonException;
import nl.esciencecenter.xenon.jobs.JobDescription;

import org.junit.Test;

/**
 * 
 */
public class SlurmJobScriptGeneratorTest {

    @Test
    public void test00_constructorIsPrivate() throws Throwable {
        Constructor<SlurmJobScriptGenerator> constructor = SlurmJobScriptGenerator.class.getDeclaredConstructor();
        assertTrue(Modifier.isPrivate(constructor.getModifiers()));
        constructor.setAccessible(true);
        constructor.newInstance();
        constructor.setAccessible(false);
    }

    @Test
    public void testEmptyDescription() throws XenonException {
        JobDescription description = new JobDescription();

        String result = SlurmJobScriptGenerator.generate(description, null);

        String expected = "#!/bin/sh\n" + "#SBATCH --job-name xenon\n" + "#SBATCH --nodes=1\n" + "#SBATCH --ntasks-per-node=1\n"
                + "#SBATCH --time=15\n" + "#SBATCH --output=/dev/null\n" + "#SBATCH --error=/dev/null\n\n" + "srun null\n";

        assertEquals(expected, result);
    }
    
    @Test
    public void testSingleProcessDescription() throws XenonException {
        JobDescription description = new JobDescription();
        description.setStartSingleProcess(true);

        String result = SlurmJobScriptGenerator.generate(description, null);

        String expected = "#!/bin/sh\n" + "#SBATCH --job-name xenon\n" + "#SBATCH --nodes=1\n" + "#SBATCH --ntasks-per-node=1\n"
                + "#SBATCH --time=15\n" + "#SBATCH --output=/dev/null\n" + "#SBATCH --error=/dev/null\n\n" + "null\n";

        assertEquals(expected, result);
    }

    @Test
    /**
     * Check to see if the output is _exactly_ what we expect, and not a single char different.
     * @throws XenonException
     */
    public void testFilledDescription() throws XenonException {
        JobDescription description = new JobDescription();
        description.setArguments("some", "arguments");
        description.addEnvironment("some.more", "environment value with spaces");
        description.addJobOption("job", "option");
        description.setExecutable("/bin/executable");
        description.setMaxTime(100);
        description.setNodeCount(5);
        description.setProcessesPerNode(55);
        description.setQueueName("the.queue");
        description.setStderr("stderr.file");
        description.setStdin("stdin.file");
        description.setStdout("stdout.file");
        description.setWorkingDirectory("/some/working/directory");

        String result = SlurmJobScriptGenerator.generate(description, null);

        String expected = "#!/bin/sh\n" + "#SBATCH --job-name xenon\n" + "#SBATCH --workdir='/some/working/directory'\n"
                + "#SBATCH --partition=the.queue\n" + "#SBATCH --nodes=5\n" + "#SBATCH --ntasks-per-node=55\n"
                + "#SBATCH --time=100\n" + "#SBATCH --input='stdin.file'\n" + "#SBATCH --output='stdout.file'\n"
                + "#SBATCH --error='stderr.file'\n" + "export some.more=\"environment value with spaces\"\n\n"
                + "srun /bin/executable 'some' 'arguments'\n";

        System.out.println(result);

        assertEquals(expected, result);
    }

    @Test
    /**
     * Check to see if the output is _exactly_ what we expect, and not a single char different.
     * @throws XenonException
     */
    public void testFilledInteractiveDescription() throws XenonException {
        JobDescription description = new JobDescription();
        description.setArguments("some", "arguments");
        description.addEnvironment("some", "environment.value");
        description.addEnvironment("some.more", "environment value with spaces");
        description.addJobOption("job", "option");
        description.setExecutable("/bin/executable");
        description.setMaxTime(100);
        description.setNodeCount(5);
        description.setProcessesPerNode(55);
        description.setQueueName("the.queue");
        description.setStderr("stderr.file");
        description.setStdin("stdin.file");
        description.setStdout("stdout.file");
        description.setWorkingDirectory("/some/working/directory");

        String[] expected = { "--quiet", "--comment=e74ef5ee-ff73-48a0-9219-3c4d34f960d2", "--chdir=/some/working/directory",
                "--partition=the.queue", "--nodes=5", "--ntasks-per-node=55", "--time=100", "/bin/executable", "some",
                "arguments" };

        UUID id = UUID.fromString("e74ef5ee-ff73-48a0-9219-3c4d34f960d2");

        String[] result = SlurmJobScriptGenerator.generateInteractiveArguments(description, null, id);

        System.out.println(Arrays.toString(result));

        assertArrayEquals(expected, result);
    }
}

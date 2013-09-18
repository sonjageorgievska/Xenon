/*
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

package nl.esciencecenter.octopus.examples.jobs;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import nl.esciencecenter.cobalt.Cobalt;
import nl.esciencecenter.cobalt.CobaltException;
import nl.esciencecenter.cobalt.CobaltFactory;
import nl.esciencecenter.cobalt.jobs.Job;
import nl.esciencecenter.cobalt.jobs.JobDescription;
import nl.esciencecenter.cobalt.jobs.Jobs;
import nl.esciencecenter.cobalt.jobs.Scheduler;
import nl.esciencecenter.cobalt.jobs.Streams;
import nl.esciencecenter.cobalt.util.Utils;

/**
 * An example of how to create and submit an interactive job that produces output. 
 * 
 * This example assumes the user provides a URI with the scheduler location on the command line.
 * 
 * Note: this example assumes the job is submitted to a machine Linux machine, as it tries to run "/bin/hostname".
 * 
 * @author Jason Maassen <J.Maassen@esciencecenter.nl>
 * @version 1.0
 * @since 1.0
 */
public class SubmitInteractiveJobWithOutput {

    public static void main(String[] args) {
        try {
            // Convert the command line parameter to a URI
            URI location = new URI(args[0]);
            
            // We create a new octopus using the OctopusFactory (without providing any properties).
            Cobalt octopus = CobaltFactory.newCobalt(null);

            // Next, we retrieve the Jobs API
            Jobs jobs = octopus.jobs();

            // We can now create a JobDescription for the job we want to run.
            JobDescription description = new JobDescription();
            description.setExecutable("/bin/hostname");
            description.setArguments("-a");
            description.setInteractive(true);
            
            // Create a scheduler to run the job
            Scheduler scheduler = jobs.newScheduler(location.getScheme(), location.getAuthority(), null, null);

            // Submit the job
            Job job = jobs.submitJob(scheduler, description);

            // Retrieve the standard streams from the job.
            Streams streams = jobs.getStreams(job);
             
            // Close stdin and stdout (we don't need them)
            streams.getStdin().close();
            streams.getStderr().close();
            
            // Read all bytes from stdout
            String result = Utils.readToString(streams.getStdout());
            
            System.out.println("Job ran succesfully and produced: " + result);

            // Close the scheduler
            jobs.close(scheduler);

            // Finally, we end octopus to release all resources 
            CobaltFactory.endCobalt(octopus);

        } catch (URISyntaxException | CobaltException | IOException e)  {
            System.out.println("SubmitBatchJob example failed: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
package nl.esciencecenter.octopus.jobs;

import java.net.URI;

import nl.esciencecenter.octopus.ImmutableTypedProperties;
import nl.esciencecenter.octopus.security.Credentials;

public interface Scheduler {

	 public URI getUri();

	 public ImmutableTypedProperties getProperties();

	 public Credentials getCredentials();

	 public String getAdaptorName();
}

package org.exercise2;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

/**
 * @Path for accessing and modifying jobs
 */

@Path("/jobs")
public class JobResource {

  // Create list to store job objects
  public static List<Job> jobs = new ArrayList<>();

  /**
   * @getJobs returns all jobs in list
   */

  @GET
  @Produces(MediaType.APPLICATION_JSON)
  public Response getJobs() {
    return Response.ok(jobs).build();
  }

  /**
   * @param id job id of a job to find
   * @return return a job with that specific id
   */

  @GET
  @Path("/find/id/{id}")
  @Produces(MediaType.APPLICATION_JSON)
  @Consumes(MediaType.APPLICATION_JSON)
  public Response getJobById(
      @PathParam("id") final Long id) {
    final Job findJob = jobs.stream()
        .filter(job -> job.getId().equals(id))
        .findAny()
        .orElse(null);

    if (findJob != null) {
      return Response.ok(findJob).build();
    } else {
      return Response.status(Status.NOT_FOUND).build();
    }
  }

  /**
   * @param type job type of jobs to find
   * @return return a list of jobs with that specific job type
   */

  @GET
  @Path("/find/type/{type}")
  @Produces(MediaType.APPLICATION_JSON)
  @Consumes(MediaType.APPLICATION_JSON)
  public Response getJobByType(
      @PathParam("type") final String type) {
    final List<Job> findJobs = jobs.stream()
        .filter(job -> job.getType().equals(type))
        .collect(Collectors.toList());

    if (findJobs != null) {
      return Response.ok(findJobs).build();
    } else {
      return Response.status(Status.NOT_FOUND).build();
    }
  }

  /**
   * @param experience experience level of jobs to find
   * @return return a list of jobs with that specific experience level
   */

  @GET
  @Path("/find/experience/{experience}")
  @Produces(MediaType.APPLICATION_JSON)
  @Consumes(MediaType.APPLICATION_JSON)
  public Response getJobByExp(
      @PathParam("experience") final String experience) {
    final List<Job> findJobs = jobs.stream()
        .filter(job -> job.getExperience().equals(experience))
        .collect(Collectors.toList());

    if (findJobs != null) {
      return Response.ok(findJobs).build();
    } else {
      return Response.status(Status.NOT_FOUND).build();
    }
  }

  /**
   * @countJobs returns amount of jobs in list
   */

  @GET
  @Produces(MediaType.TEXT_PLAIN)
  @Path("/amount")
  public Integer countJobs() {
    return jobs.size();
  }

  /**
   * @return returns updated jobs list
   * @createJob add new job to list
   */

  @POST
  @Produces(MediaType.APPLICATION_JSON)
  @Consumes(MediaType.APPLICATION_JSON)
  public Response createJob(final Job newJob) {
    jobs.add(newJob);
    return Response.ok(jobs).build();
  }

  /**
   * @param id   job id which type has to be updated
   * @param type new type for job
   * @return returns updated jobs list
   */

  @PUT
  @Path("/change/{id}/type/{type}")
  @Produces(MediaType.APPLICATION_JSON)
  @Consumes(MediaType.APPLICATION_JSON)
  public Response updateJobType(
      @PathParam("id") final Long id,
      @PathParam("type") final String type) {
    jobs = jobs.stream().map(job -> {
      if (job.getId().equals(id)) {
        job.setType(type);
      }
      return job;
    }).collect(Collectors.toList());
    return Response.ok(jobs).build();
  }

  /**
   * @param id         job id which experience level has to be updated
   * @param experience new experience level for job
   * @return returns updated jobs list
   */

  @PUT
  @Path("/change/{id}/experience/{experience}")
  @Produces(MediaType.APPLICATION_JSON)
  @Consumes(MediaType.APPLICATION_JSON)
  public Response updateJobExperience(
      @PathParam("id") final Long id,
      @PathParam("experience") final String experience) {
    jobs = jobs.stream().map(job -> {
      if (job.getId().equals(id)) {
        job.setExperience(experience);
      }
      return job;
    }).collect(Collectors.toList());
    return Response.ok(jobs).build();
  }

  /**
   * @param id     job id which salary has to be updated
   * @param salary new salary for job
   * @return returns updated jobs list
   */

  @PUT
  @Path("/change/{id}/salary/{salary}")
  @Produces(MediaType.APPLICATION_JSON)
  @Consumes(MediaType.APPLICATION_JSON)
  public Response updateJobSalary(
      @PathParam("id") final Long id,
      @PathParam("salary") final int salary) {
    jobs = jobs.stream().map(job -> {
      if (job.getId().equals(id)) {
        job.setSalary(salary);
      }
      return job;
    }).collect(Collectors.toList());
    return Response.ok(jobs).build();
  }

  /**
   * @param id job id that has to be deleted
   * @return returns either successful no conent anymore Response when deleted or 400 when not being
   * able to remove
   */

  @DELETE
  @Path("/del/{id}")
  @Consumes(MediaType.APPLICATION_JSON)
  public Response deleteJob(
      @PathParam("id") final Long id) {
    final Optional<Job> jobToDelete = jobs.stream().filter(job -> job.getId().equals(id))
        .findFirst();
    boolean removed = false;
    if (jobToDelete.isPresent()) {
      removed = jobs.remove(jobToDelete.get());
    }
    if (removed) {
      return Response.noContent().build();
    }
    return Response.status(Status.BAD_REQUEST).build();
  }
}
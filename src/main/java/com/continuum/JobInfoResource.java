package com.continuum;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

@Path("/jobinfo")
@Produces(MediaType.APPLICATION_JSON)
public class JobInfoResource {

    Map<Long, JobInfo> jobs = Maps.newLinkedHashMap();
    AtomicInteger idCounter = new AtomicInteger(0);

    @GET
    @Path("/{id}")
    public Response getJob(@PathParam("id") long id) {
        System.out.println("get " + id);
        JobInfo result = jobs.get(id);
        if (result == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(result).build();
    }

    @GET
    public Response getJobs() {
        System.out.println("get all");
        return Response.ok(Lists.newArrayList(jobs.values())).build();
    }

    @POST
    public Response addJob(@Valid JobInfo job) {
        System.out.print("received post: " + job);
        JobInfo result = job;
        if (job.getId() == null) {
            System.out.print("... creating");
            result = new JobInfo(idCounter.incrementAndGet(), job.getTarget(), job.getMean(), job.getPercentile90(), analyzeForRegression(job));
        } else {
            System.out.print("... updating");
            result = new JobInfo(job.getId(), job.getTarget(), job.getMean(), job.getPercentile90(), analyzeForRegression(job));
        }
        jobs.put(result.getId(), result);
        System.out.println("result: " + result);
        return Response.status(Response.Status.CREATED).entity(result).build();
    }

//    @PUT
//    @Path("/{id}")
//    public Response updateJob(@PathParam("id") long id, JobInfo job) {
//        if (!jobs.containsKey(id)) {
//            return Response.status(Response.Status.NOT_FOUND).build();
//        }
//        jobs.put(id, job);
//        return Response.status(Response.Status.ACCEPTED).entity(job).build();
//    }

    private boolean analyzeForRegression(JobInfo jobWithId) {
        return Math.random() > .5;
    }
}

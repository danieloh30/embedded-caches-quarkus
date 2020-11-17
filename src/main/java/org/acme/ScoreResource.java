package org.acme;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.OPTIONS;
import javax.ws.rs.PATCH;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.transaction.Transactional;
import javax.validation.Valid;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.jboss.resteasy.annotations.jaxrs.PathParam;


@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Path("/api")
public class ScoreResource {

    @Inject
    ScoreService scoreService;

    @POST
    public Response create(@Valid Score item) {
        scoreService.save(item);
        return Response.status(Status.CREATED).entity(item).build();
    }

    @GET
    @Path("/{id}")
    public Object getOne(@PathParam("id") String id) {
        Object entity = scoreService.findById(id);
        if (entity == null) {
            throw new WebApplicationException("ScoreCard with id of " + id + " does not exist.", Status.NOT_FOUND);
        }
        return entity;
    }

    @GET
    public List<Score> getAll() {
        return scoreService.getAll();
    }

    @PATCH
    @Path("/{id}")
    public Response update(@Valid Score card, @PathParam("id") Long id) {
        scoreService.save(card);
        return Response.status(Status.CREATED).entity(card).build();

    }

    @OPTIONS
    public Response opt() {
        return Response.ok().build();
    }

    @GET
    @Path("/hello")
    public String hello() {
        return "hello";
    }

    @DELETE
    @Transactional
    public Response delete() {
        return Response.noContent().build();
    }

    @DELETE
    @Transactional
    @Path("/{id}")
    public Response deleteOne(@PathParam("id") Long id) {
        return Response.noContent().build();
    }

}
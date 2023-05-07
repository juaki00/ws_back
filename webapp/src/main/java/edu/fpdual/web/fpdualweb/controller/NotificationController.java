package edu.fpdual.web.fpdualweb.controller;

import edu.fpdual.web.fpdualweb.api.dto.Usuario;
import edu.fpdual.web.fpdualweb.connector.MySQLConnector;
import edu.fpdual.web.fpdualweb.manager.UserManager;
import edu.fpdual.web.fpdualweb.manager.impl.UserManagerImpl;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import edu.fpdual.web.fpdualweb.api.dto.Notification;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Set;

@Path("/notifications")
public class NotificationController {
    private UserManager userManager;
    @GET
    @Path("/ping")
    public Response ping() {
        return Response.ok().entity("Service online").build();
    }

    @GET
    @Path("/get/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getNotification(@PathParam("id") int id) {
        return Response.ok().entity(new Notification(id, "john", "test notification")).build();
    }

    @PUT
    @Path("/get/{id}/{name}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getNotification(@PathParam("id") int id, @PathParam("name") String name) {
        return Response.ok().entity(new Notification(id, name, "test notification")).build();
    }

    @GET
    @Path("/get/{id}/name")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getNotificationWithParameters(@PathParam("id") int id, @QueryParam("name")
    String name) {
        if (name == null || name.trim().isEmpty()) {
            return Response.status(400).entity("Name no present in the request").build();
        } else {
            return Response.ok().entity(new Notification(id, name, "test notification")).build();
        }
    }

    @GET
    @Path("/getXML/{id}")
    @Produces(MediaType.APPLICATION_XML)
    public Response getNotificationXML(@PathParam("id") int id) {
        return Response.ok().entity(new Notification(id, "john", "test notification")).build();
    }

    @POST
    @Path("/post/")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response postNotification(Notification notification) {
        return Response.status(201).entity(notification).build();
    }

    @GET
    @Path("/getAll/")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllUsers() throws SQLException, ClassNotFoundException {
        Connection connection = new MySQLConnector().getMySQLConnection();
        userManager = new UserManagerImpl();
        Set<Usuario> usuarios = userManager.findAllUsuario(connection);

        for(Usuario user : usuarios){
            user.setNombre(user.getNombre().toUpperCase());
        }

        System.out.println(usuarios);
        return Response.ok().entity(usuarios).build();
    }
}


package edu.fpdual.web.fpdualweb.controller;

import edu.fpdual.web.fpdualweb.api.dto.Notification;
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

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Set;

/**
 * Controlador para la base de datos:
 * Crea la conexion y llama al manejador de la base de datos
 */
@Path("/users")
public class UserController {

    private static UserManager userManager = new UserManagerImpl();;
    private static Connection con;

    /**
     *
     * @return Devuelve todos los usuarios de la tabla
     */
    @POST
    @Path("/getAll/")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllUsers() throws SQLException, ClassNotFoundException {
        con = new MySQLConnector().getMySQLConnection();
        Set<Usuario> usuarios = userManager.findAllUsuario(con);

        return Response.ok().entity(usuarios).build();
    }

    @POST
    @Path("/insert/")
    @Consumes(MediaType.APPLICATION_JSON)
    public void insertUser(Usuario usuario) throws SQLException, ClassNotFoundException {
        con = new MySQLConnector().getMySQLConnection();
        userManager.insertUser(con,usuario.getNick(), usuario.getNombre(), usuario.getPassword(), usuario.getApellido(), usuario.getTelefono(), usuario.getEmail());
    }


    @POST
    @Path("/getPass/")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response userFromNick(Usuario user) throws SQLException, ClassNotFoundException {
        con = new MySQLConnector().getMySQLConnection();
        Usuario userCompleted = userManager.userFromNick(con,user.getNick());
        return Response.ok().entity(userCompleted).build();
    }



    @GET
    @Path("/get/{id}/name")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response existeNick(Usuario user) throws SQLException, ClassNotFoundException {
        con = new MySQLConnector().getMySQLConnection();
        if (!userManager.existeNick(con, user.getNick())){
            return Response.status(400).entity(false).build();
        } else {
            return Response.ok().entity(true).build();
        }
    }

//
//    /**
//     *
//     * @param nick
//     * @return Devuelve true si existe un usuario con ese nick y false si no existe
//     */
//    public boolean existeNick(String nick) throws SQLException, ClassNotFoundException {
//        Connection con = new MySQLConnector().getMySQLConnection();
//        return userManager.existeNick(con, nick);
//    }


}

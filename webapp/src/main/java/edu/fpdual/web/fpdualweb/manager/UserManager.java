package edu.fpdual.web.fpdualweb.manager;

import edu.fpdual.web.fpdualweb.api.dto.Usuario;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Set;

public interface UserManager {


    public Set<Usuario> findAllUsuario(Connection connection) throws SQLException, ClassNotFoundException;


    public Set<Usuario> findUsuarioById(Connection con, String nick) throws SQLException, ClassNotFoundException;

    boolean insertUser(Connection con, String nick, String nombre, String passwd, String apellido, String telefono, String email)  throws SQLException, ClassNotFoundException;

    String passwordFromNick(Connection con, String nick) throws SQLException;

    Usuario userFromNick(Connection con, String nick) throws SQLException;

    boolean existeNick(Connection con, String nick) throws SQLException;
}

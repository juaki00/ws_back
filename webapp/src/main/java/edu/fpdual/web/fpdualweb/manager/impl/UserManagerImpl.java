package edu.fpdual.web.fpdualweb.manager.impl;

import edu.fpdual.web.fpdualweb.api.dto.Usuario;
import edu.fpdual.web.fpdualweb.manager.UserManager;

import java.sql.*;
import java.util.HashSet;
import java.util.Set;

public class UserManagerImpl implements UserManager {

    @Override
    public Set<Usuario> findAllUsuario(Connection connection){

        try(Statement stm = connection.createStatement()) {

            ResultSet result = stm.executeQuery("SELECT * FROM usuarios");

            Set<Usuario> UsuarioSet = new HashSet<>();
//            result.beforeFirst();
            while (result.next()) {
                Usuario usuario = new Usuario(result);
                UsuarioSet.add(usuario);
            }

            return UsuarioSet;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }


    @Override
    public Set<Usuario> findUsuarioById(Connection con, String nick) throws SQLException, ClassNotFoundException {
        try(PreparedStatement prepstm = con.prepareStatement("SELECT * FROM usuarios WHERE nick = ?",ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE)) {

            prepstm.setString(1, nick);

            ResultSet result = prepstm.executeQuery();


            Set<Usuario> userSet = new HashSet<>();
            result.beforeFirst();
            while (result.next()) {
                Usuario usuario = new Usuario(result);
                userSet.add(usuario);
            }

            return userSet;
        }
    }

    @Override
    public boolean insertUser(Connection con, String nick, String nombre, String passwd, String apellido, String telefono, String email) throws SQLException, ClassNotFoundException {
        try(PreparedStatement prepstm = con.prepareStatement("INSERT INTO usuarios VALUES (?,?,?,?,?,?)")) {

            prepstm.setString(1, nick);
            prepstm.setString(2, nombre);
            prepstm.setString(3, passwd);
            prepstm.setString(4, apellido);
            prepstm.setString(5, telefono);
            prepstm.setString(6, email);

            prepstm.executeUpdate();


        }
        catch (SQLException e){
            return false;
        }
        return true;
    }

    @Override
    public String passwordFromNick(Connection con, String nick) throws SQLException {
        try (PreparedStatement prepstm = con.prepareStatement("SELECT passwd FROM usuarios WHERE nick = ?", ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE)) {

            prepstm.setString(1, nick);

            ResultSet result = prepstm.executeQuery();


            Set<Usuario> userSet = new HashSet<>();
            result.beforeFirst();
            if (result.next()) {
                return result.getString(1);
            } else {
                return null;
            }

        }
    }

    @Override
    public Usuario userFromNick(Connection con, String nick) throws SQLException {
        try(PreparedStatement prepstm = con.prepareStatement("SELECT * FROM usuarios WHERE nick = ?",ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE)) {

            prepstm.setString(1, nick);

            ResultSet result = prepstm.executeQuery();

            Usuario usuario = null;
            result.beforeFirst();
            if (result.next()) {
                usuario = new Usuario(result);
            }

            return usuario;
        }
    }

    @Override
    public boolean existeNick(Connection con, String nick) throws SQLException {
        try(PreparedStatement prepstm = con.prepareStatement("SELECT * FROM usuarios WHERE nick = ?",ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE)) {

            prepstm.setString(1, nick);

            ResultSet result = prepstm.executeQuery();

            Usuario usuario = null;
            result.beforeFirst();
            if (result.next()) {
                usuario = new Usuario(result);
            }

            return usuario!=null;
        }
    }


}

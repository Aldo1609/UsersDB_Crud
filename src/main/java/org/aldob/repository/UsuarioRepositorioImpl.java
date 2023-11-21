package org.aldob.repository;

import org.aldob.models.Usuario;
import org.aldob.util.ConexionDB;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UsuarioRepositorioImpl implements Repository<Usuario>{

    private Connection getConnection() throws SQLException{
        return ConexionDB.getConexion();
    }

    @Override
    public List<Usuario> findAll() {
        List<Usuario> usuarios = new ArrayList<>();
        try (Connection connection = getConnection();
             Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM usuarios")) {
            while (rs.next()) {
                usuarios.add(new Usuario(
                        rs.getLong("id"),
                        rs.getString("username"),
                        rs.getString("password"),
                        rs.getString("email")
                ));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return usuarios;
    }

    @Override
    public Usuario findById(Long id) {
        Usuario usuario = null;
        try(PreparedStatement stmt = getConnection().prepareStatement("SELECT * FROM usuarios WHERE id = ?")){
            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();
            if(rs.next()) {
                usuario = new Usuario(
                        rs.getLong("id"),
                        rs.getString("username"),
                        rs.getString("password"),
                        rs.getString("email")
                );
            }
            rs.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return usuario;
    }

    @Override
    public void save(Usuario usuario) {
        if (usuario.getId() == null) {
            // Nuevo usuario, realizar inserción
            String sql = "INSERT INTO usuarios (username, password, email) VALUES (?, ?, ?)";
            try (PreparedStatement stmt = getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                stmt.setString(1, usuario.getUsername());
                stmt.setString(2, usuario.getPassword());
                stmt.setString(3, usuario.getEmail());
                stmt.executeUpdate();

                // Obtener el ID generado para el nuevo usuario
                ResultSet generatedKeys = stmt.getGeneratedKeys();
                if (generatedKeys.next()) {
                    usuario.setId(generatedKeys.getLong(1));
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        } else {
            // Usuario existente, realizar actualización
            String sql = "UPDATE usuarios SET username=?, password=?, email=? WHERE id=?";
            try (PreparedStatement stmt = getConnection().prepareStatement(sql)) {
                stmt.setString(1, usuario.getUsername());
                stmt.setString(2, usuario.getPassword());
                stmt.setString(3, usuario.getEmail());
                stmt.setLong(4, usuario.getId());
                stmt.executeUpdate();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

    }


    @Override
    public void delete(Long id) {
        String sql = "DELETE FROM usuarios WHERE id = ?";
        try(PreparedStatement stmt = getConnection().prepareStatement(sql)){
            stmt.setLong(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}

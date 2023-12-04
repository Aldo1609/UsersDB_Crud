package org.aldob;

import org.aldob.models.Usuario;
import org.aldob.repository.Repository;
import org.aldob.repository.UsuarioRepositorioImpl;
import org.aldob.util.ConexionDB;
import javax.swing.*;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;


public class MainDB {
    public static void main(String[] args) {
        int opcionIndice;

        Map<String, Integer> operaciones = new HashMap<>();
        operaciones.put("Actualizar", 1);
        operaciones.put("Eliminar", 2);
        operaciones.put("Agregar", 3);
        operaciones.put("Listar", 4);
        operaciones.put("Salir", 5);

        try (Connection conn = ConexionDB.getConexion()) {
            Repository<Usuario> repository = new UsuarioRepositorioImpl();

            while (true) {
                Object[] opArreglo = operaciones.keySet().toArray();
                Object opcion = JOptionPane.showInputDialog(null, "Seleccione una opción", "Menú principal", JOptionPane.INFORMATION_MESSAGE, null, opArreglo, opArreglo[0]);

                if (opcion == null) {
                    JOptionPane.showMessageDialog(null, "Debe seleccionar una operacion");
                } else {
                    opcionIndice = operaciones.get(opcion.toString());

                    if (opcionIndice == 1) {
                        Long id = Long.parseLong(JOptionPane.showInputDialog(null, "Ingrese el id del usuario a actualizar"));
                        Usuario usuario = repository.findById(id);

                        if (usuario != null) {
                            usuario.setUsername(JOptionPane.showInputDialog(null, "Ingrese el nuevo nombre de usuario"));
                            usuario.setPassword(JOptionPane.showInputDialog(null, "Ingrese la nueva contraseña"));
                            usuario.setEmail(JOptionPane.showInputDialog(null, "Ingrese el nuevo correo electrónico"));
                            repository.save(usuario);
                            JOptionPane.showMessageDialog(null, "Usuario actualizado");
                            repository.findAll().forEach(System.out::println);
                        } else {
                            JOptionPane.showMessageDialog(null, "No se encontró un usuario con el ID proporcionado");
                        }
                    } else if (opcionIndice == 2) {
                        Long id = Long.parseLong(JOptionPane.showInputDialog(null, "Ingrese el id del usuario a eliminar"));
                        repository.delete(id);
                        JOptionPane.showMessageDialog(null, "Usuario eliminado");
                        repository.findAll().forEach(System.out::println);
                    } else if (opcionIndice == 3) {
                        Usuario usuario = new Usuario();
                        usuario.setUsername(JOptionPane.showInputDialog(null, "Ingrese el nombre de usuario"));
                        usuario.setPassword(JOptionPane.showInputDialog(null, "Ingrese la contraseña"));
                        usuario.setEmail(JOptionPane.showInputDialog(null, "Ingrese el correo electrónico"));
                        repository.save(usuario);
                        JOptionPane.showMessageDialog(null, "Usuario agregado");
                        repository.findAll().forEach(System.out::println);
                    } else if (opcionIndice == 4) {
                        System.out.println("Conexión exitosa");
                        repository.findAll().forEach(System.out::println);
                    } else if (opcionIndice == 5) {
                        break;
                    }
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al conectar a la base de datos" + e.getMessage());
        }
    }
}

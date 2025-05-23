/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import com.mysql.jdbc.PreparedStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import vista.sistema;

/**
 *
 * @author USUARIO
 */
public class ClienteDAO {

    Conexion cc = new Conexion();
    Connection conec;
    ResultSet rs;

    public boolean existeCliente(String cedula) {
        try {
            conec = cc.conectar();
            // Preparar la consulta para verificar si la cédula ya existe en la base de datos
            PreparedStatement statement = (PreparedStatement) conec.prepareStatement("SELECT COUNT(*) FROM clientes WHERE cedula = ?");

            //java.sql.PreparedStatement psd = conec.prepareStatement(sql);
            //String sql = "SELECT COUNT(*) FROM clientes WHERE cedula = ?";
            statement.setString(1, cedula);
            // Ejecutar la consulta
            ResultSet resultSet = statement.executeQuery();

            // Obtener el resultado
            resultSet.next();
            int count = resultSet.getInt(1);

            // Cerrar los recursos
            resultSet.close();
            statement.close();

            // Verificar si el cliente existe
            return count > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                conec.close();
            } catch (SQLException ex) {
                System.out.println(ex.toString());
            }
        }
    }

    public boolean RegistrarCliente(Clientes cl) {

        try {
            conec = cc.conectar();//con existe cliente la coneccion se cierra
            if (existeCliente(cl.getCedula())) {
                JOptionPane.showMessageDialog(null, "El clientes ya existe ");
                sistema ss = new sistema();
                ss.nuevo();

            } else {
                conec = cc.conectar();//volver abrir la coneccion
                String sql = "INSERT INTO clientes (cedula, nombre, apellido, telefono, direccion) VALUES (?,?,?,?,?)";
                System.out.println(sql);
                PreparedStatement psd = (PreparedStatement) conec.prepareStatement(sql);
                psd.setString(1, cl.getCedula());
                psd.setString(2, cl.getNombre());
                psd.setString(3, cl.getApellido());
                psd.setString(4, cl.getTelefono());
                psd.setString(5, cl.getDireccion());
                psd.execute();
                JOptionPane.showMessageDialog(null, "Cliente Registrado");
            }
            return true;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.toString());
            return false;
        } finally {
            try {
                conec.close();
            } catch (SQLException ex) {
                System.out.println(ex.toString());
            }
        }
    }

    public List ListarCliente() {
        List<Clientes> ListaCl = new ArrayList();
        try {
            conec = cc.conectar();
            String sql = "SELECT * FROM clientes";
            Statement psd = conec.createStatement();
            ResultSet rs = psd.executeQuery(sql);
            while (rs.next()) {
                Clientes cl = new Clientes();
                cl.setId(rs.getInt("id"));
                cl.setCedula(rs.getString("cedula"));
                cl.setNombre(rs.getString("nombre"));
                cl.setApellido(rs.getString("apellido"));
                cl.setTelefono(rs.getString("telefono"));
                cl.setDireccion(rs.getString("direccion"));
                ListaCl.add(cl);
                
            }
            conec.close();
            return ListaCl;
        } catch (SQLException e) {
            System.out.println(e.toString());
        } 
        return ListaCl;

    }

    public boolean EliminarCliente(int id) {
        String sql = "DELETE FROM clientes WHERE id = ?";

        try {
            conec = cc.conectar();
            PreparedStatement psd = (PreparedStatement) conec.prepareStatement(sql);
            psd.setInt(1, id);
            psd.execute();

            return true;
        } catch (SQLException e) {
            System.out.println(e.toString());
            return false;
        } finally {

            try {
                conec.close();
            } catch (SQLException ex) {
                System.out.println(ex.toString());
            }

        }
    }

    public boolean ModificarCliente(Clientes cl) {
        String sql = "UPDATE clientes SET cedula=?, nombre=?,apellido=?, telefono=?, direccion=? WHERE id=?";
        try {
            conec = cc.conectar();
            PreparedStatement psd = (PreparedStatement) conec.prepareStatement(sql);
            psd.setString(1, cl.getCedula());
            psd.setString(2, cl.getNombre());
            psd.setString(3, cl.getApellido());
            psd.setString(4, cl.getTelefono());
            psd.setString(5, cl.getDireccion());
            psd.setInt(6, cl.getId());
            psd.execute();
            return true;
        } catch (SQLException e) {
            System.out.println(e.toString());
            return false;
        } finally {
            try {
                conec.close();
            } catch (SQLException e) {
                System.out.println(e.toString());
            }

        }

    }

    public Clientes Buscarcliente(String cedula) throws SQLException {
        Clientes cl = new Clientes();
        try {
            conec = cc.conectar();// corre antes de llamado a psd
            PreparedStatement psd = (PreparedStatement) conec.prepareStatement("SELECT * FROM clientes WHERE cedula = ?");
        
            psd.setString(1, cedula);
            ResultSet rs = psd.executeQuery();
            if (rs.next()) {
                cl.setId(rs.getInt("id"));
                cl.setNombre(rs.getString("nombre"));
                cl.setApellido(rs.getString("apellido"));
                cl.setTelefono(rs.getString("telefono"));
                cl.setDireccion(rs.getString("direccion"));
            }
            conec.close();
            return cl;
        } catch (SQLException e) {
            System.out.println(e.toString());
           
        }
        
        return cl;
    }

}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

/**
 *
 * @author carri
 */
public class VentasDAO {
    Connection con;
    Conexion cn = new Conexion();
    PreparedStatement ps;
    ResultSet rs;
    int r;
    
    public int IdVenta(){
        int id = 0;
        String sql = "SELECT MAX(id) FROM ventas";
        try {
            con = cn.conectar();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            if (rs.next()) {
                id = rs.getInt(1);
            }
        } catch (Exception e) {
            System.out.println(e.toString());
        }finally {

            try {
                con.close();
            } catch (SQLException ex) {
                System.out.println(ex.toString());
            }
        }
        return id;
    }
    
    public int RegistrarVenta(Venta v){
        String sql = "INSERT INTO ventas (cliente, vendedor, total, fecha) VALUES (?,?,?,?)";
        try {
            con = cn.conectar();
            ps = con.prepareStatement(sql);
            ps.setInt(1, v.getCliente());
            ps.setString(2, v.getVendedor());
            ps.setDouble(3, v.getTotal());
            ps.setString(4, v.getFecha());
            ps.execute();
        } catch (Exception e) {
            System.out.println(e.toString());
        }finally{
            try {
                con.close();
            } catch (Exception e) {
                System.out.println(e.toString());
            }finally {

            try {
                con.close();
            } catch (SQLException ex) {
                System.out.println(ex.toString());
            }
        }
        }
        return r;
    }
    
    public int RegistrarDetalle(Detalle Dv){
       String sql = "INSERT INTO detalle (id_pro, cantidad, precio, id_venta) VALUES (?,?,?,?)";
        try {
            con = cn.conectar();
            ps = con.prepareStatement(sql);
            ps.setInt(1, Dv.getId_pro());
            ps.setInt(2, Dv.getCantidad());
            ps.setDouble(3, Dv.getPrecio());
            ps.setInt(4, Dv.getId());
            ps.executeUpdate();
        } catch (Exception e) {
            System.out.println(e.toString());
            JOptionPane.showMessageDialog(null, e);
        }finally{
            try {
                con.close();
            } catch (Exception e) {
                System.out.println(e.toString());
            }
        }
        return r;
    }
    
    public boolean ActualizarStock(int cant, int id){
        String sql = "UPDATE productos SET stock = ? WHERE id = ?";
        try {
            con = cn.conectar();
            ps = con.prepareStatement(sql);
            ps.setInt(1,cant);
            ps.setInt(2, id);
            ps.execute();
            return true;
        } catch (Exception e) {
            System.out.println(e.toString());
            return false;
        }finally {

            try {
                con.close();
            } catch (SQLException ex) {
                System.out.println(ex.toString());
            }
        }
    }
    
    public List Listarventas(){
       List<Venta> ListaVenta = new ArrayList();
        String sql = "SELECT c.id AS cedula, c.nombre, v.* FROM clientes c INNER JOIN ventas v ON c.id = v.cliente";
       try {
           con = cn.conectar();
           ps = con.prepareStatement(sql);
           rs = ps.executeQuery();
           while (rs.next()) {               
               Venta vent = new Venta();
               vent.setId(rs.getInt("id"));
               vent.setCliente(rs.getInt("cedula"));
               vent.setVendedor(rs.getString("vendedor"));
               vent.setTotal(rs.getDouble("total"));
               ListaVenta.add(vent);
           }
       } catch (Exception e) {
           System.out.println(e.toString());
       }finally {

            try {
                con.close();
            } catch (SQLException ex) {
                System.out.println(ex.toString());
            }
        }
       return ListaVenta;
   }
    public Venta BuscarVenta(int id){
        Venta cl = new Venta();
        String sql = "SELECT * FROM ventas WHERE id = ?";
        try {
            con = cn.conectar();
            ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            rs = ps.executeQuery();
            if (rs.next()) {
                cl.setId(rs.getInt("id"));
                cl.setCliente(rs.getInt("cliente"));
                cl.setTotal(rs.getDouble("total"));
                cl.setVendedor(rs.getString("vendedor"));
                cl.setFecha(rs.getString("fecha"));
            }
        } catch (Exception e) {
            System.out.println(e.toString());
        }finally {

            try {
                con.close();
            } catch (SQLException ex) {
                System.out.println(ex.toString());
            }
        }
        return cl;
    }
    
}

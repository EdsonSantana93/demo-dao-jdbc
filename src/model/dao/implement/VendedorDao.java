package model.dao.implement;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import db.DB;
import db.DbException;
import model.dao.IVendedorDao;
import model.entities.Departamento;
import model.entities.Vendedor;

public class VendedorDao implements IVendedorDao{

	private Connection conn;
	
	public VendedorDao(Connection conn) {
		this.conn = conn;
	}
	
	@Override
	public void inserirVendedor(Vendedor vendedor) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void atualizarVendedor(Vendedor vendedor) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deletarVendedorId(Vendedor vendedor) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Vendedor pesquisarVendedorId(Integer id) {
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sql = "SELECT seller.*, department.Name as DepName FROM seller INNER JOIN"
				+ " department ON seller.DepartmentId = departmentId WHERE seller.Id = ?";
		
		try {
			ps = conn.prepareStatement(sql);
			ps.setInt(1, id);
			rs = ps.executeQuery();
			
			if (rs.next()) {
				Departamento departamento = instanciarDepartamento(rs);
				Vendedor vendedor = instanciarVendedor(rs, departamento);
				return vendedor;
			}
			return null;
		} catch (SQLException e) {
			throw new DbException("Error: " + e.getMessage()); 
		} finally {
			DB.closeResultSet(rs);
			DB.closeStatement(ps);
		}
	}

	private Vendedor instanciarVendedor(ResultSet rs, Departamento departamento) throws SQLException {
		Vendedor vendedor = new Vendedor();
		vendedor.setId(rs.getInt("Id"));
		vendedor.setNome(rs.getString("Name"));
		vendedor.setEmail(rs.getString("Email"));
		vendedor.setDataNascimento(rs.getDate("BirthDate"));
		vendedor.setSalarioBase(rs.getDouble("BaseSalary"));
		vendedor.setDepartamento(departamento);
		return vendedor;
	}

	private Departamento instanciarDepartamento(ResultSet rs) throws SQLException {
		Departamento departamento = new Departamento();
		departamento.setId(rs.getInt("DepartmentId"));
		departamento.setNome(rs.getString("DepName"));
		return departamento;
	}

	@Override
	public List<Vendedor> pesquisarVendedores() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Vendedor> pesquisarPorDepartemento(Departamento departamento) {
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sql = "SELECT seller.*, department.Name as DepName FROM seller"
				+ " INNER JOIN department ON seller.DepartmentId = department.Id "
				+ "WHERE DepartmentId = ? ORDER BY Name";
		
		try {
			ps = conn.prepareStatement(sql);
			ps.setInt(1, departamento.getId());
			rs = ps.executeQuery();
			
			List<Vendedor> vendedores = new ArrayList<>();
			Map<Integer, Departamento> map = new HashMap<>();
			
			while(rs.next()) {
				Departamento dep = map.get(rs.getInt("DepartmentId"));
				
				if(dep == null) {
					dep = instanciarDepartamento(rs);
					map.put(rs.getInt("DepartmentId"), dep);
				}
				Vendedor vendedor = instanciarVendedor(rs, dep);
				vendedores.add(vendedor);
			}
			return vendedores;
		} catch (SQLException e) {
			throw new DbException("Error: " + e.getMessage());
		} finally {
			DB.closeResultSet(rs);
			DB.closeStatement(ps);
		}
	}

}

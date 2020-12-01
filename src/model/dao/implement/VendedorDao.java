package model.dao.implement;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
		PreparedStatement ps = null;
		
		String sql = "INSERT INTO seller (Name, Email, BirthDate, BaseSalary, DepartmentId) VALUES (?, ?, ?, ?, ?)";
		
		
				
		try {
			ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			ps.setString(1, vendedor.getNome());
			ps.setString(2, vendedor.getEmail());
			ps.setDate(3, new Date(vendedor.getDataNascimento().getTime()));
			ps.setDouble(4, vendedor.getSalarioBase());
			ps.setInt(5, vendedor.getDepartamento().getId());
			
			int linhasAfetadas = ps.executeUpdate();
			
			if (linhasAfetadas > 0) {
				ResultSet rs = ps.getGeneratedKeys();
				if(rs.next()) {
					int id = rs.getInt(1);
					vendedor.setId(id);
				} else {
					throw new DbException("Erro Inesperado! Nenhuma linha afetada!");
				}
				DB.closeResultSet(rs);
			}
			
		} catch (SQLException e) {
			throw new DbException("Error: " + e.getMessage()); 
		} finally {
			DB.closeStatement(ps);
		}
	}

	@Override
	public void atualizarVendedor(Vendedor vendedor) {
		PreparedStatement ps = null;
		
		String sql = "UPDATE seller SET Name = ?, Email = ?, BirthDate = ?, BaseSalary = ?, DepartmentId = ? WHERE Id = ?";
		
		try {
			ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			ps.setString(1, vendedor.getNome());
			ps.setString(2, vendedor.getEmail());
			ps.setDate(3, new Date(vendedor.getDataNascimento().getTime()));
			ps.setDouble(4, vendedor.getSalarioBase());
			ps.setInt(5, vendedor.getDepartamento().getId());
			ps.setInt(6, vendedor.getId());
			
			ps.executeUpdate();
		} catch (SQLException e) {
			throw new DbException("Error: " + e.getMessage());
		} finally {
			DB.closeStatement(ps);
		}
		
	} 

	@Override
	public void deletarVendedorId(Integer id) {
		// TODO Auto-generated method stub
		return;
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
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sql = "SELECT seller.*, department.Name AS DepName FROM seller "
				+ "INNER JOIN department ON seller.DepartmentId = department.Id ORDER BY Name";
		
		try {
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			
			List<Vendedor> vendedores = new ArrayList<>();
			Map<Integer, Departamento> map = new HashMap<>();
			
			while (rs.next()) {
				Departamento departamentoId = map.get(rs.getInt("DepartmentId"));
				
				if (departamentoId == null) {
					departamentoId = instanciarDepartamento(rs);
					map.put(rs.getInt("DepartmentId"), departamentoId);
				}
				
				Vendedor vendedor = instanciarVendedor(rs, departamentoId);
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

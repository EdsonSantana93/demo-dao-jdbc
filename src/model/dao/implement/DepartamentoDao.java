package model.dao.implement;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import db.DB;
import db.DbException;
import db.DbIntegritException;
import model.dao.IDepartamentoDao;
import model.entities.Departamento;

public class DepartamentoDao implements IDepartamentoDao {

	Connection conn;

	public DepartamentoDao(Connection connection) {
		this.conn = connection;
	}

	@Override
	public void inserirDepartamento(Departamento departamento) {
		PreparedStatement ps = null;
		String sql = "INSERT INTO department (Name) VALUES (?)";

		try {
			ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			ps.setString(1, departamento.getNome());

			int linhasAfetadas = ps.executeUpdate();
			
			if (linhasAfetadas > 0) {
				ResultSet rs = ps.getGeneratedKeys();
				if(rs.next()) {
					int id = rs.getInt(1);
					departamento.setId(id);
				}
			} else {
				throw new DbException("Não Foi Possível inserir o Departamento");
			}
		} catch (SQLException e) {
			throw new DbException("Error: " + e.getMessage());
		} finally {
			DB.closeStatement(ps);
		}

	}

	@Override
	public void atualizarDepartamento(Departamento departamento) {
		PreparedStatement ps = null;
		String sql = "UPDATE department SET Name = ? WHERE Id = ?";
		
		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, departamento.getNome());
			ps.setInt(2, departamento.getId());
			ps.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			DB.closeStatement(ps);
		}

	}

	@Override
	public void deletarDepartamentoId(Integer id) {
		PreparedStatement ps = null;
		String sql = "DELETE FROM department WHERE Id = ?";
		
	
		try {			
			ps = conn.prepareStatement(sql);
			ps.setInt(1, id);
			ps.executeUpdate();
		} catch (SQLException e) {
			throw new DbIntegritException("Error: " + e.getMessage());
		} finally {
			DB.closeStatement(ps);
		}
	}

	@Override
	public Departamento buscarDepartamentoId(Integer id) {
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sql = "SELECT * FROM department WHERE Id = ? ORDER BY Name";

		try {
			ps = conn.prepareStatement(sql);
			ps.setInt(1, id);
			rs = ps.executeQuery();

			if (rs.next()) {
				Departamento departamento = instanciarDepartamento(rs);
				return departamento;
			} else {
				return null;
			}
		} catch (SQLException e) {
			throw new DbException("Error: " + e.getMessage());
		} finally {
			DB.closeResultSet(rs);
			DB.closeStatement(ps);
		}
	}

	@Override
	public List<Departamento> buscarDepartamentos() {
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sql = "SELECT * FROM department ORDER BY Name";

		try {
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();

			List<Departamento> departamentos = new ArrayList<>();

			while (rs.next()) {
				Departamento departamento = new Departamento();
				departamento = instanciarDepartamento(rs);
				departamentos.add(departamento);
			}
			return departamentos;
		} catch (SQLException e) {
			throw new DbException("Error: " + e.getMessage());
		} finally {
			DB.closeResultSet(rs);
			DB.closeStatement(ps);
		}
	}

	private Departamento instanciarDepartamento(ResultSet rs) throws SQLException {
		Departamento departamento = new Departamento();
		departamento.setId(rs.getInt("Id"));
		departamento.setNome(rs.getString("Name"));
		return departamento;
	}

}

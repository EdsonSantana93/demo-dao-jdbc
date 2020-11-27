package model.dao;

import java.util.List;

import model.entities.Departamento;

public interface IDepartamentoDao {
	void inserirDepartamento(Departamento departamento);
	void atualizarDepartamento(Departamento departamento);
	void deletarDepartamentoId(Integer id);
	Departamento buscarDepartamentoId(Integer id);
	List<Departamento> buscarDepartamentos();
}

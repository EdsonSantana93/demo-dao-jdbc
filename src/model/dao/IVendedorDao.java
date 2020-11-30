package model.dao;

import java.util.List;

import model.entities.Departamento;
import model.entities.Vendedor;

public interface IVendedorDao {
	void inserirVendedor(Vendedor vendedor);
	void atualizarVendedor(Vendedor vendedor);
	void deletarVendedorId(Vendedor vendedor);
	Vendedor pesquisarVendedorId(Integer id);
	List<Vendedor> pesquisarVendedores();
	List<Vendedor> pesquisarPorDepartemento(Departamento departemento);
}

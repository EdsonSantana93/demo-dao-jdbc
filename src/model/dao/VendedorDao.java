package model.dao;

import java.util.List;

import model.entities.Vendedor;

public interface VendedorDao {
	void inserirVendedor(Vendedor vendedor);
	void atualizarVendedor(Vendedor vendedor);
	void deletarVendedorId(Vendedor vendedor);
	Vendedor pesquisarVendedorId(Integer id);
	List<Vendedor> pesquisarVendedores();
}

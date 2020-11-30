package application;

import java.util.List;

import db.DB;
import model.dao.DaoFactory;
import model.dao.IVendedorDao;
import model.entities.Departamento;
import model.entities.Vendedor;

public class Program {

	public static void main(String[] args) {
		IVendedorDao vendedorDao = DaoFactory.criarVendedorDao();
		System.out.println("=== Teste 1 : Pesquisar por ID ===");
		Vendedor vendedor = vendedorDao.pesquisarVendedorId(10);
		System.out.println(vendedor == null? "Nenhum vendedor encontrado" : vendedor);
		
		System.out.println();
		System.out.println("=== Teste 2 : Pesquisar por departamento");
		Departamento departamento = new Departamento(2, null);
		List<Vendedor> vendedores = vendedorDao.pesquisarPorDepartemento(departamento); 
		for(Vendedor vend : vendedores) {
			System.out.println(vend);	
		}
		
		if (vendedores.size() == 0) {
			System.out.println("Nenhum vendedor encontrado");
		}
		
		DB.closeConnection();
	}

}

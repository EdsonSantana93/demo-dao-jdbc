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
		
		System.out.println();
		System.out.println("=== Teste 3 : Pesquisar todos");
		List<Vendedor> vendedores2 = vendedorDao.pesquisarVendedores();
		for(Vendedor vend2 : vendedores2) {
			System.out.println(vend2);
		}
		
		/*System.out.println("\n=== Teste 4 : Inserir vendedor");
		Vendedor vendedorNovo = new Vendedor(null, "Maria do Bairro", "maria@gmail.com", new Date(), 2800.00, departamento);
		vendedorDao.inserirVendedor(vendedorNovo);
		System.out.println("Vendedor cadastrado com sucesso: " + vendedorNovo.getId() + " - " + vendedorNovo.getNome());*/
		
		System.out.println("\n=== Teste 5 : Atualizar vendedor");
		vendedor = vendedorDao.pesquisarVendedorId(8);
		vendedor.setNome("Estroncio Gonçalves");
		vendedor.setEmail("estroncio@gmail.com");
		vendedorDao.atualizarVendedor(vendedor);
		System.out.println("Atualização realizada com sucesso!");
		
		System.out.println("\n=== Teste 6 : Deletar vendedor");
		vendedorDao.deletarVendedorId(7);
		System.out.println("Vendedor Deletado com Sucesso!");
		
		DB.closeConnection();	
	}

}

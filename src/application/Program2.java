package application;

import java.util.List;

import db.DB;
import model.dao.DaoFactory;
import model.dao.IDepartamentoDao;
import model.entities.Departamento;

public class Program2 {

	public static void main(String[] args) {
		IDepartamentoDao departamentoDao = DaoFactory.criarDepartemantoDao();
		
		System.out.println("=== Teste 1 : Pesquisar departamentos ===");
		List<Departamento> departamentos = departamentoDao.buscarDepartamentos();
		for (Departamento departamento : departamentos) {
			System.out.println(departamento);
		}
		
		if(departamentos == null){
			System.out.println("Nenhum departamento foi encontrado!");
		}
		
		System.out.println("\n=== Teste 2 : Pesquisar departamento por ID ===");
		Departamento departamento2 =  departamentoDao.buscarDepartamentoId(1);
		System.out.println(departamento2 == null ? "Departamento não encontrado!" : departamento2);

		/*System.out.println("\n=== Teste 3 : Inserir departamento ===");
		Departamento departamento3 = new Departamento();
		departamento3.setNome("Toys");
		departamentoDao.inserirDepartamento(departamento3);
		System.out.println("Departamento Inserido com sucesso! : " + departamento3.getId() + " - " + departamento3.getNome());*/
		
		System.out.println("\n=== Teste 4 : Atualizar departamento ===");
		Departamento departamento4 = new Departamento();
		departamento4.setNome("Toys");
		departamento4.setId(6);
		departamentoDao.atualizarDepartamento(departamento4);
		System.out.println("Atualização realizada com sucesso!");
		
		System.out.println("\n=== Teste 5 : Deletar departamento ===");
		departamentoDao.deletarDepartamentoId(1);
		System.out.println("Deletado com sucesso!");
		
		DB.closeConnection();
	}

}

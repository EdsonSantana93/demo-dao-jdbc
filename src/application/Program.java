package application;

import java.util.Date;

import model.dao.DaoFactory;
import model.dao.IVendedorDao;
import model.entities.Departamento;
import model.entities.Vendedor;

public class Program {

	public static void main(String[] args) {
		Departamento departamento = new Departamento(1, "TI");
		Vendedor vendedor = new Vendedor(1, "Edson Ferreira Santana", "edson@gmail.com", new Date(), 1000.00, departamento);
		System.out.println(vendedor);
		
		IVendedorDao vendedorDao = DaoFactory.criarVendedorDao();

	}

}

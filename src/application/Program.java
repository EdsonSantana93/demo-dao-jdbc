package application;

import db.DB;
import model.dao.DaoFactory;
import model.dao.IVendedorDao;
import model.entities.Vendedor;

public class Program {

	public static void main(String[] args) {
		IVendedorDao vendedorDao = DaoFactory.criarVendedorDao();
		Vendedor vendedor = vendedorDao.pesquisarVendedorId(8);
		System.out.println(vendedor == null? "Nenhum registro encontrado" : vendedor);
		DB.closeConnection();
	}

}

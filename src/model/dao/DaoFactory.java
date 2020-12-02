package model.dao;

import db.DB;
import model.dao.implement.DepartamentoDao;
import model.dao.implement.VendedorDao;

public class DaoFactory {
	public static IVendedorDao criarVendedorDao() {
		return new VendedorDao(DB.getConnection());		
	}
	
	public static IDepartamentoDao criarDepartemantoDao() {
		return new DepartamentoDao(DB.getConnection());
	}
}

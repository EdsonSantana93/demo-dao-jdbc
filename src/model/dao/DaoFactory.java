package model.dao;

import model.dao.implement.VendedorDao;

public class DaoFactory {
	public static IVendedorDao criarVendedorDao() {
		return new VendedorDao();		
	}
}

package application;

import db.DB;

public class Program {

	public static void main(String[] args) {
		DB.getConnection();
		System.out.println("Conexão OK!");
		DB.closeConnection();
		System.out.println("Conexão fechada!");

	}

}

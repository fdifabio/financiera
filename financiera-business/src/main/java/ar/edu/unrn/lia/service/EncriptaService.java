package ar.edu.unrn.lia.service;


public interface EncriptaService {

	public  String encrypt(String cadena);

	public  String decrypt(String cadena) ;

	public String encryptURL(String cadena);
}
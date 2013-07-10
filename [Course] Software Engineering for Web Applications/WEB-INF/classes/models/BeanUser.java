package models;

import controllers.formcontroller;

public class BeanUser {

	private String user = "";
	private String mail = "";
	private String name = "";
	private String surname = "";
	private String pass = "";
	private String sex = "";
	private String birthday = "";
	private int[] error = {0,0}; 
	private formcontroller controlador;
	
	/* Getters */
	public String getUser(){
		return user;
	}
	
	public String getMail() {
		return mail;
	}
	
	public int[] getError() {
		return error;
	}
	
	public String getName() {
		return name;
	}

	public String getSurname() {
		return surname;
	}


	public String getPass() {
		return pass;
	}

	public String getSex() {
		return sex;
	}

	public String getBirthday() {
		return birthday;
	}

	
	/*Setters*/
	public void setUser(String user){
		controlador = new formcontroller();
		//Consultar si existeix un usuari igual a la BD
		if(controlador.existeUser(user))
		{
		error[0] = 1;
		}
		else
		{
			this.user = user;
		}
		
	}
	
	public void setMail(String mail){
		this.mail = mail;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public void setSurname(String surname) {
		this.surname = surname;
	}
	
	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}
	
	public void setSex(String sex) {
		this.sex = sex;
	}
	
	public void setPass(String pass) {
		this.pass = pass;
	}
	
	/* Logic Functions */
	
	
	
	public boolean isComplete() {
	    return(hasValue(getUser()) &&
	           hasValue(getMail()) && hasValue(getPass()));
	}
	
	private boolean hasValue(String val) {
		return((val != null) && (!val.equals("")));
	}


}

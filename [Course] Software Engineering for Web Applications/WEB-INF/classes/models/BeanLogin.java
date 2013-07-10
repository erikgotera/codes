package models;

import controllers.formcontroller;
import controllers.logincontroller;

public class BeanLogin {

	private String user = "";
	private String pass = "";
	private int[] error = {0};
	private formcontroller controlador;
	private logincontroller logincont;
	
	public String getUser(){
		return user;
	}
	
	public void setUser(String user){
		controlador = new formcontroller();
		//Consultar si existeix un usuari igual a la BD
		if(controlador.existeUser(user))
		{
			this.user = user;
		}
		else
		{
			error[0] = 1;
		}
	}
	
	public String getPass() {
		return pass;
	}

	public void setPass(String pass) {
		this.pass = pass;
	}

	public int[] getError() {
		return error;
	}
	
	public boolean isComplete() {
		logincont = new logincontroller();
		
		if(logincont.equalPass(getUser(), getPass()))
		{
			return(hasValue(getUser()) && hasValue(getPass()));
		}
		else{
			error[0] = 2;
			return false;
		}
	}
	
	private boolean hasValue(String val) {
		return((val != null) && (!val.equals("")));
	}
	
}


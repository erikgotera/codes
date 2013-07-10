package es.in2.jira;

import dbaccess.DAO;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Map;

import com.atlassian.crowd.embedded.api.User;
import com.atlassian.jira.plugin.projectpanel.impl.AbstractProjectTabPanel;
import com.atlassian.jira.project.Project;
import com.atlassian.jira.project.browse.BrowseContext;
import com.atlassian.jira.security.groups.GroupManager;
import com.atlassian.jira.security.roles.ProjectRole;
import com.atlassian.jira.security.roles.ProjectRoleActors;
import com.atlassian.jira.security.roles.ProjectRoleManager;
import java.sql.*;


public class IndicatorsProjectTabPanel extends AbstractProjectTabPanel {
	
	private final ProjectRoleManager projectRoleManager;
	private final GroupManager groupManager;
	public static final String ROL = "Administrators";
	 
	  public IndicatorsProjectTabPanel(ProjectRoleManager projectRoleManager, GroupManager groupManager) {
	    this.projectRoleManager = projectRoleManager;
	    this.groupManager = groupManager;
	  }
	
	@Override
	public boolean showPanel(BrowseContext ctx) {
		
		User user = ctx.getUser();
		Project project = ctx.getProject();
		boolean estaEnGrupo = false;
		boolean estaEnRol = false;
		boolean esLider = false;
    	
		if(project.getLead() == user)
		{
			esLider = true;
		}
		
		if (groupManager.isUserInGroup(user.getName(), "Comercial"))
		{
			estaEnGrupo = true;
		}
		
    	ProjectRole role = projectRoleManager.getProjectRole(ROL);
    			
    	ProjectRoleActors projectRoleActors = projectRoleManager.getProjectRoleActors(role, project);
    	   	
    	for (User usuario : projectRoleActors.getUsers()) {
	      if ((usuario.getName().equals(user.getName()))) {
	    	  estaEnRol = true;
	    	  break;
	      }
	    }
    	
    	if (estaEnRol || estaEnGrupo || esLider)
    	{
	        return true;
    	}
    	
		return false;
	}
	
	@Override
	protected Map<String, Object> createVelocityParams(BrowseContext ctx) {
	    Map<String, Object> params = super.createVelocityParams(ctx);
	    
	    //String project = ctx.getProject().getName();
	    
	    Long project = ctx.getProject().getId();
	    
	    ResultSet resultado = null;
	    DAO conexion;
	    try {
	    	conexion = new DAO();

	    	try {
	    		resultado = conexion.executarSQL("SELECT * FROM in2_vinfo_cierre_proyecto where id="+project);
	    		
	    		boolean existe = resultado.next();
	    		if(existe)
	    		{
	    			params.put("enBD", true);
	    			
	    			//Estat del projecte
	    	    	params.put("estado", resultado.getString("estado"));
	    	    	
	    	    	//Indicadors econòmics
	    	    	DecimalFormat formateador = new DecimalFormat("###,###.##");
	    	    	params.put("importe", formateador.format(resultado.getFloat("importe")));
	    	    	params.put("ingresos", formateador.format(resultado.getFloat("total_ingreso")));
	    	    	params.put("coste", formateador.format(resultado.getFloat("total_coste")));
	    	    	params.put("margen", formateador.format(resultado.getFloat("MARGEN")));
	    	    	
	    	    	float margenPor;
	    	    	
	    	    	if(resultado.getFloat("total_coste") == 0 || resultado.getFloat("total_ingreso") == 0)
	    	    	{
	    	    		margenPor = 0;
	    	    	}
	    	    	else
	    	    	{
	    	    		margenPor = (1-(resultado.getFloat("total_coste")/resultado.getFloat("total_ingreso")))*100;
	    	    	}
	    	    	params.put("margen_por", formateador.format(margenPor));
	    	    	
	    	    	
	    	    	
	    	    	//Indicadors d'esforç
	    	    	params.put("estimadas", formateador.format(resultado.getFloat("HORASVALORACION")));
	    	    	params.put("reales", formateador.format(resultado.getFloat("HORAS_JIRA")));
	    	    	params.put("diferencia", formateador.format(resultado.getFloat("DIF_HORAS")));
	    	    	
	    	    	float desviament;
	    	    	
	    	    	if(resultado.getFloat("HORAS_JIRA") == 0 || resultado.getFloat("HORASVALORACION") == 0)
	    	    	{
	    	    		desviament = 0;
	    	    	}
	    	    	else
	    	    	{
	    	    		desviament = (1-(resultado.getFloat("HORAS_JIRA")/resultado.getFloat("HORASVALORACION")))*100;
	    	    	}
	    	    	params.put("desviament", formateador.format(desviament));
	    	    	
	    	    	//Indicadors entrega
	    	    	SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
	    	    	
	    	    	if(resultado.getDate("fecha_entrega_prevista") == null)
	    	    	{
	    	    		params.put("fecha_entrega_prevista", "NO DEFINIT");
	    	    	}
	    	    	else
	    	    	{
	    	    		params.put("fecha_entrega_prevista", formato.format(resultado.getDate("fecha_entrega_prevista")));
	    	    	}
	    	    	
	    	    	if(resultado.getDate("fecha_entrega_real") == null )
	    	    	{
	    	    		params.put("fecha_entrega_real", "NO DEFINIT");
	    	    	}
	    	    	else
	    	    	{
	    	    		params.put("fecha_entrega_real", formato.format(resultado.getDate("fecha_entrega_real")));
	    	    	}
	    	    	
	    	    	params.put("dif_plazo", resultado.getInt("DIF_PLAZO_LAB"));
	    	    	
	    		}
	    		else
	    		{
	    			params.put("enBD", false);
	    		}
	    		
	    		
	    	} catch (SQLException e) {
	    		System.err.println("Se obtiene el siguiente error al realizar el insert en la BD: " + e.getMessage());
	    		e.printStackTrace();
	    	}

	    	conexion.desconnectarBD();
	    	
	    } catch (Exception e1) {
	    	System.err.println("Se obtiene el siguiente error al conectarte a la BD: " + e1.getMessage());
	    	e1.printStackTrace();
	    }

	    return params;
	}	
	

}

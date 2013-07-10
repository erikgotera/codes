package es.in2.barcelo;

import javax.ws.rs.*;
import javax.ws.rs.core.*;

import com.atlassian.crowd.embedded.api.User;
import com.atlassian.jira.component.ComponentAccessor;
import com.atlassian.jira.project.Project;
import com.atlassian.jira.security.roles.ProjectRole;
import com.atlassian.jira.security.roles.ProjectRoleActors;
import com.atlassian.jira.security.roles.ProjectRoleManager;
 
@Path("/isUserInRol")
@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})

public class RestIsUserInRolService {
	
	private final ProjectRoleManager projectRoleManager;
	 
	  public RestIsUserInRolService(ProjectRoleManager projectRoleManager) {
	    this.projectRoleManager = projectRoleManager;
	  }
	
    @GET
    @Path("/{projectkey}/{rol}/{username}")
    public Response getMessageFromPath(@PathParam("projectkey") String projectkey, @PathParam("rol") String rol, @PathParam("username") String username )
    {
        return Response.ok(new RestIsUserInRolServiceModel(getRolesFromProject(projectkey, username, rol))).build();
    }
    
    private String getRolesFromProject(String projectkey, String username, String rol) {
    	
    	User user = ComponentAccessor.getUserUtil().getUser(username);
    	
    	Project project = ComponentAccessor.getProjectManager().getProjectObjByKey(projectkey);
    	
    	ProjectRole role = projectRoleManager.getProjectRole(rol);
    			
    	ProjectRoleActors projectRoleActors = projectRoleManager.getProjectRoleActors(role, project);
    	
    	
    	for (User usuario : projectRoleActors.getUsers()) {
    	      if ((usuario.getName().equals(user.getName()))) {
    	        return "true";
    	      }
    	    }
    	
        return "false";
    }
}

package es.in2.salut.jira.mailhandlerchangestatus;

import java.sql.Timestamp;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;

import javax.mail.Message;
import javax.mail.MessagingException;

import com.atlassian.crowd.embedded.api.User;
import com.atlassian.jira.bc.issue.IssueService;
import com.atlassian.jira.bc.issue.IssueService.IssueResult;
import com.atlassian.jira.bc.issue.IssueService.TransitionValidationResult;
import com.atlassian.jira.component.ComponentAccessor;
import com.atlassian.jira.issue.CustomFieldManager;
import com.atlassian.jira.issue.Issue;
import com.atlassian.jira.issue.IssueInputParameters;
import com.atlassian.jira.issue.ModifiedValue;
import com.atlassian.jira.issue.fields.CustomField;
import com.atlassian.jira.issue.util.DefaultIssueChangeHolder;
import com.atlassian.jira.issue.util.IssueChangeHolder;
import com.atlassian.jira.security.JiraAuthenticationContext;
import com.atlassian.jira.service.util.handler.MessageHandler;
import com.atlassian.jira.service.util.handler.MessageHandlerContext;
import com.atlassian.jira.service.util.handler.MessageHandlerErrorCollector;
import com.atlassian.jira.service.util.handler.MessageUserProcessor;
import com.atlassian.jira.workflow.JiraWorkflow;
import com.atlassian.jira.workflow.WorkflowManager;
import com.opensymphony.workflow.loader.ActionDescriptor;
import com.opensymphony.workflow.loader.StepDescriptor;

public class SalutHandler implements MessageHandler {
	private String issueKey;
	private String customFieldValue;
	private String statusIssueSau;
	private String projectKey;
	private final IssueKeyValidator issueKeyValidator;
    private final CustomFieldManager customFieldManager;
    private final WorkflowManager workflowManager;
    
    public static final String PROJECT_KEY = "projectKey";
    public static final String OBERTURA = "Obertura del tiquet";
    public static final String MANCAN_DADES = "Manca de dades del tiquet";
    public static final String RESOLUCIO = "Resolució del tiquet";

    
    public SalutHandler(MessageUserProcessor messageUserProcessor, IssueKeyValidator issueKeyValidator, CustomFieldManager customFieldManager, WorkflowManager workflowManager) {
        this.issueKeyValidator = issueKeyValidator;
        this.customFieldManager = customFieldManager;
        this.workflowManager = workflowManager;
    }
    
	@Override
	public void init(Map<String, String> params, MessageHandlerErrorCollector monitor) {
		// getting here project key configured by the user in the configuration of mail handler
		projectKey = params.get(PROJECT_KEY);
		
	}
    
	@Override
	public boolean handleMessage(Message message, MessageHandlerContext context)
			throws MessagingException {
		
		String subject = message.getSubject();
		
		
		issueKey = subject.substring(subject.lastIndexOf('(')+1, subject.lastIndexOf(')'));
		
		
		final Issue issue = issueKeyValidator.validateIssue(issueKey, context.getMonitor());

		if (issue == null) {
            return false; // returning false means that we were unable to handle this message.
        }
		
		
		//WORKFLOW PART
		JiraWorkflow workflow = workflowManager.getWorkflow(issue);
		
		StepDescriptor actualStatus = workflow.getLinkedStep(issue.getStatusObject());
		
		IssueService issueService = ComponentAccessor.getIssueService();
		IssueInputParameters issueInputParameters = issueService.newIssueInputParameters();
		int savedAction = 0;
		User user = ComponentAccessor.getUserUtil().getUser("sau");
		
		JiraAuthenticationContext jac = ComponentAccessor.getJiraAuthenticationContext();
		
		jac.setLoggedInUser(user);
		
		
		IssueChangeHolder changeHolder = new DefaultIssueChangeHolder();
		
		statusIssueSau = subject.substring(subject.indexOf(" - ")+3, subject.indexOf(" - ", (subject.indexOf(" - ")+3)));
		
		if(statusIssueSau.equals(OBERTURA))
		{
			CustomField customField = customFieldManager.getCustomFieldObjectByName( "Ticket Sau" );
			CustomField customFieldDate = customFieldManager.getCustomFieldObjectByName( "Data i hora de rebuda" );
			
			// retrieves the custom field value object from the issue
			if(issue.getCustomFieldValue( customField ) == null)
			{
				//the custom field is the first word of the subject
				customFieldValue = subject.substring(0, subject.indexOf(' '));
				customField.updateValue(null, issue, new ModifiedValue(issue.getCustomFieldValue(customField), customFieldValue), changeHolder);
			}

			if(issue.getCustomFieldValue( customFieldDate ) == null)
			{
				Date today = new Date();
				Timestamp timestamp = new Timestamp(today.getTime());
				customFieldDate.updateValue(null, issue, new ModifiedValue(issue.getCustomFieldValue(customFieldDate), timestamp), changeHolder);
			}
			
			// Find a "Obrir SAU" transition
	         for (Iterator<ActionDescriptor> iter = actualStatus.getActions().iterator(); iter.hasNext();) {
	               ActionDescriptor actionDescriptor = (ActionDescriptor) iter.next();
	               if (actionDescriptor.getName().contains("Obrir SAU") ) {
	                   savedAction = actionDescriptor.getId();
	               }
	           }
	         
	         
	         
	         TransitionValidationResult transitionValidationResult = issueService.validateTransition(user, issue.getId(), savedAction, issueInputParameters);
	         
    		 if (transitionValidationResult.isValid()){ 
    		        IssueResult transitionResult = issueService.transition(user, transitionValidationResult);
    		        if (!transitionResult.isValid()){ 
    		        	context.getMonitor().error("La validación de la Transición ha dado ERROR para el mail con Asunto: "+ subject);
    		        } 
    		 }
    		 else
	   			{
	   				context.getMonitor().error("No se puede realizar este cambio de estado: NO EXISTE LA TRANSICION AL ESTADO SOLICITADO para el mail con Asunto: "+ subject);
		   			return false;
	   			}
	  
			
		}
		else if(statusIssueSau.equals(MANCAN_DADES))
		{
			// Find a "Manquen dades SAU" transition
	         for (Iterator<ActionDescriptor> iter = actualStatus.getActions().iterator(); iter.hasNext();) {
	               ActionDescriptor actionDescriptor = (ActionDescriptor) iter.next();
	               if (actionDescriptor.getName().contains("Manquen dades SAU") ) {
	                   savedAction = actionDescriptor.getId();
	               }
	           }
	         
	         TransitionValidationResult transitionValidationResult = issueService.validateTransition(user, issue.getId(), savedAction, issueInputParameters);
	         
	   		 if (transitionValidationResult.isValid()){ 
	   		        IssueResult transitionResult = issueService.transition(user, transitionValidationResult);
	   		        if (!transitionResult.isValid()){ 
	   		        	context.getMonitor().error("La validación de la Transición ha dado ERROR para el mail con Asunto: "+ subject);
	   		        } 
	   		 }
	   		else
   			{
	   			context.getMonitor().error("No se puede realizar este cambio de estado: NO EXISTE LA TRANSICION AL ESTADO SOLICITADO para el mail con Asunto: "+ subject);
	   			return false;
   			}
		}
		else if(statusIssueSau.equals(RESOLUCIO))
		{
			CustomField customFieldDateClose = customFieldManager.getCustomFieldObjectByName( "Data i hora de tancament" );
			
			Date today = new Date();
			Timestamp timestamp = new Timestamp(today.getTime());
			customFieldDateClose.updateValue(null, issue, new ModifiedValue(issue.getCustomFieldValue(customFieldDateClose), timestamp), changeHolder);
				
			// Find a "Tancar Tiquet SAU" transition
	         for (Iterator<ActionDescriptor> iter = actualStatus.getActions().iterator(); iter.hasNext();) {
	               ActionDescriptor actionDescriptor = (ActionDescriptor) iter.next();
	               if (actionDescriptor.getName().contains("Tancar Tiquet SAU") ) {
	                   savedAction = actionDescriptor.getId();
	               }
	           }
	         
	         TransitionValidationResult transitionValidationResult = issueService.validateTransition(user, issue.getId(), savedAction, issueInputParameters);
	         
	   		 if (transitionValidationResult.isValid()){ 
	   		        IssueResult transitionResult = issueService.transition(user, transitionValidationResult);
	   		        if (!transitionResult.isValid()){ 
	   		        	context.getMonitor().error("La validación de la Transición ha dado ERROR para el mail con Asunto: "+ subject);
	   		        } 
	   		 }
	   		else
	   			{
	   				context.getMonitor().error("No se puede realizar este cambio de estado: NO EXISTE LA TRANSICIÓN AL ESTADO SOLICITADO para el mail con Asunto: "+ subject);
		   			return false;
	   			}
		}
		
		
		
		
		
		return true;
	}


	

}

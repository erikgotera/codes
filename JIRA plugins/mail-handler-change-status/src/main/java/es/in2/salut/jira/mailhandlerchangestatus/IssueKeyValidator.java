package es.in2.salut.jira.mailhandlerchangestatus;

import org.apache.commons.lang.StringUtils;
import com.atlassian.jira.issue.Issue;
import com.atlassian.jira.issue.IssueManager;
import com.atlassian.jira.service.util.handler.MessageHandlerErrorCollector;

public class IssueKeyValidator 
{
	private final IssueManager issueManager;
	
	public IssueKeyValidator(IssueManager issueManager) {
        this.issueManager = issueManager;
    }

	public Issue validateIssue(String issueKey, MessageHandlerErrorCollector collector)  {
		
        if (StringUtils.isBlank(issueKey)) {
            collector.error("Issue key cannot be undefined.");
            return null;
        }
        
        final Issue issue = issueManager.getIssueObject(issueKey);
        
        if (issue == null) {
            collector.error("Cannot change status from mail to issue '" + issueKey + "'. The issue does not exist.");
            return null;
        }
        
        return issue;
    }

}

package org.CommunityService.Services;

import javax.servlet.ServletContext;

import org.ocpsoft.rewrite.annotation.RewriteConfiguration;
import org.ocpsoft.rewrite.config.Configuration;
import org.ocpsoft.rewrite.config.ConfigurationBuilder;
import org.ocpsoft.rewrite.servlet.config.HttpConfigurationProvider;
import org.ocpsoft.rewrite.servlet.config.rule.Join;

@RewriteConfiguration
public class FacesConfigurationProvider extends HttpConfigurationProvider{
	
	@Override
	public Configuration getConfiguration(final ServletContext context) {
		return ConfigurationBuilder.begin()
				.addRule(Join.path("/group/{groupId}").to("/Web/ViewGroup.xhtml"))
				
				.addRule(Join.path("/createGroup").to("/Web/NewGroup.xhtml"))
				
				.addRule(Join.path("/manageEvent/{eventId}").to("/Web/EditEvent.xhtml"))
				.addRule(Join.path("/manageOrganization/{orgId}").to("/Web/EditOrganization.xhtml"))
				.addRule(Join.path("/manageGroup/{groupId}").to("/Web/EditGroup.xhtml"))
				;
	}
	
	@Override
	public int priority() {
		// Using 10 so that if we want more configuration providers, we can add above or below this one
		return 10;
	}

}

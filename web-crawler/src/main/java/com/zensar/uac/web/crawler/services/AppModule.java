package com.zensar.uac.web.crawler.services;

import com.zensar.uac.web.crawler.services.impl.ReportServiceImpl;
import com.zensar.uac.web.crawler.services.impl.UACServiceImpl;
import com.zensar.uac.web.crawler.services.impl.UserDetailServiceImpl;
import org.apache.tapestry5.ComponentResources;
import org.apache.tapestry5.Link;
import org.apache.tapestry5.SymbolConstants;
import org.apache.tapestry5.ioc.MappedConfiguration;
import org.apache.tapestry5.ioc.ServiceBinder;
import org.apache.tapestry5.services.ComponentSource;
import org.apache.tapestry5.services.RequestExceptionHandler;
import org.apache.tapestry5.services.Response;

import java.io.IOException;

/**
 * Created by srikant.singh on 10/16/2016.
 * Purpose of the class: This is Tapestry specific class and use to do tapestry related configuration.
 * Sponsor: www.zensar.com
 * License: This code is released under GPL. You are free to make changes to the code as long as you provide
 * attribution to the Sponsor.
 */
public class AppModule {
    public static void contributeApplicationDefaults(MappedConfiguration<String, String> configuration) {
        configuration.add(SymbolConstants.JAVASCRIPT_INFRASTRUCTURE_PROVIDER, "jquery");
        configuration.add("tapestry.hmac-passphrase", "true");
    }

    public static void bind(ServiceBinder binder) {
        binder.bind(ReportService.class, ReportServiceImpl.class);
        binder.bind(UACService.class, UACServiceImpl.class);
        binder.bind(UserDetailService.class, UserDetailServiceImpl.class);
    }

    public RequestExceptionHandler decorateRequestExceptionHandler(
            final ComponentSource componentSource,
            final Response response,
            final RequestExceptionHandler oldHandler) {
        return new RequestExceptionHandler() {
            @Override
            public void handleRequestException(Throwable exception) throws IOException {
                if (!exception.getMessage().contains("Forms require that the request method be POST and that the t:formdata query parameter have values")) {
                    oldHandler.handleRequestException(exception);
                    return;
                }
                ComponentResources cr = componentSource.getActivePage().getComponentResources();
                Link link = cr.createEventLink("");
                String uri = link.toRedirectURI().replaceAll(":", "");
                response.sendRedirect(uri);
            }
        };
    }
}

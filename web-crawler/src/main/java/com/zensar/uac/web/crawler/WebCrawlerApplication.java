package com.zensar.uac.web.crawler;

import com.zensar.uac.web.crawler.config.HomepageConfig;
import com.zensar.uac.web.crawler.constants.CrawlerConstants;
import org.apache.tapestry5.spring.TapestrySpringFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletContextInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.env.AbstractEnvironment;

import javax.servlet.FilterRegistration;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by srikant.singh on 10/16/2016.
 * Purpose of the class: This is the entry point. This is a spring boot application.
 * Sponsor: www.zensar.com
 * License: This code is released under GPL. You are free to make changes to the code as long as you provide
 * attribution to the Sponsor.
 */
@EnableAutoConfiguration
@SpringBootApplication
@ComponentScan({"com.zensar.uac.web.crawler"})
public class WebCrawlerApplication implements ServletContextInitializer {

    private static final Logger LOGGER = Logger.getLogger(WebCrawlerApplication.class.getName());
    private static HomepageConfig homepageConfig;

    @Autowired
    public void setHomepageConfig(HomepageConfig homepageConfig) {
        WebCrawlerApplication.homepageConfig = homepageConfig;
    }

    public static void main(String[] args) {
        if (System.getProperty(AbstractEnvironment.ACTIVE_PROFILES_PROPERTY_NAME) == null) {
            System.setProperty(AbstractEnvironment.ACTIVE_PROFILES_PROPERTY_NAME, "production");
        }
        SpringApplication.run(WebCrawlerApplication.class, args);
        loadHomepage();
    }

    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {

        servletContext.setInitParameter("contextClass",
                "org.springframework.web.context.support.AnnotationConfigWebApplicationContext");

        servletContext.setInitParameter("tapestry.app-package", "com.zensar.uac.web.crawler");
        servletContext.setInitParameter("tapestry.use-external-spring-context", "true");
        FilterRegistration.Dynamic filter = servletContext.addFilter("app", TapestrySpringFilter.class);
        filter.addMappingForUrlPatterns(null, true, "/*");
    }

    private static void loadHomepage() {
        /*
        The http protocol will be used as default
        and the https protocol will be used when the ssl support is configured for the application
        */
        String protocol = CrawlerConstants.HTTP;
        if (homepageConfig.isSslEnabled()) {
            protocol = CrawlerConstants.HTTPS;
        }

        //We can specify the name of page class that we want to display on application startup
        String homepage = "home";

        String url = protocol + "://" + homepageConfig.getAddress() + ":" + homepageConfig.getPort() + homepageConfig.getContextPath() + "/" + homepage;
        String urlMessage = "Browser will open the application on URL " + url;
        LOGGER.info(urlMessage);

        String operatingSystem = System.getProperty("os.name").toLowerCase();
        Runtime runtime = Runtime.getRuntime();

        try {
            if (operatingSystem.contains("win")) {
                runtime.exec("rundll32 url.dll,FileProtocolHandler " + url);
            }
            else if(operatingSystem.contains("mac")) {
                runtime.exec("open " + url);
            }
            else if(operatingSystem.contains("nix") || operatingSystem.contains("nux")) {
                String[] browsers = { "firefox", "mozilla", "konqueror", "epiphany" };
                StringBuilder cmd = new StringBuilder();
                for (int i=0; i < browsers.length - 1; i++) {
                    cmd.append((i == 0 ? "" : " || ") + browsers[i] + " \"" + url + "\" ");
                }
                runtime.exec(new String[]{"sh", "-c", cmd.toString()});
            } else {
                LOGGER.warning("Application could not identify any supported browser.");
                LOGGER.warning("Please use the following URL to access the application -");
                LOGGER.warning(url);
                return;
            }
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, CrawlerConstants.EXCEPTION, e);
        }
    }
}

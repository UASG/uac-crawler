package com.zensar.uac.web.crawler.components;

import org.apache.tapestry5.BindingConstants;
import org.apache.tapestry5.annotations.BeginRender;
import org.apache.tapestry5.annotations.Import;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.Response;

/**
 * Created by srikant.singh on 08/31/2016.
 * Purpose of the class: This class is Tapestry framework specific class ans use for Layout of comman behaviour
 * Sponsor: www.zensar.com
 * License: This code is released under GPL. You are free to make changes to the code as long as you provide
 * attribution to the Sponsor.
 */
@Import(stylesheet = {"css/style.css", "css/ie.css", "css/font-awesome.min.css", "css/latofonts.css", "css/font-awesome.css", "css/bootstrap.min.css", "css/jquery.bxslider.css", "css/slider.css"}, library = {"js/demo.js",
        "js/jquery.min.js", "js/bootstrap.min.js", "js/demo1.js", "js/jquery.bxslider.js", "js/jquery.flexslider-min.js", "js/polyfiller.js"})

public class Layout {

    @Parameter(required = true, defaultPrefix = BindingConstants.LITERAL)
    @Property
    private String title;
    @Inject
    private Response response;

    @BeginRender
    public Object beginRender() {
        response.setHeader("X-Frame-Options", "SAMEORIGIN");
        response.setHeader("X-XSS-Protection", "1; mode=block");
        response.setHeader("Strict-Transport-Security", "max-age=31536000; includeSubDomains; preload");
        response.setHeader("X-Content-Type-Options", "nosniff"); // Manual ethical hacking issue fix
        return null;
    }

}

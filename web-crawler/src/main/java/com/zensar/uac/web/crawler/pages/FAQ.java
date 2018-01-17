package com.zensar.uac.web.crawler.pages;

import org.apache.tapestry5.EventConstants;
import org.apache.tapestry5.StreamResponse;
import org.apache.tapestry5.annotations.OnEvent;
import org.apache.tapestry5.services.Response;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Path;

/**
 * Created by srikant.singh on 10/06/2016.
 * Purpose of the class: Page class for FaQ
 * Sponsor: www.zensar.com
 * License: This code is released under GPL. You are free to make changes to the code as long as you provide
 * attribution to the Sponsor.
 */
public class FAQ {

    private static final String FILENAME = "UAC-Crawler-HelpGuide-v1.0";

    /**
     * Returns the file to be downloaded when export link is clicked.
     *
     * @return  the file to be downloaded when export link is clicked
     */
    @OnEvent(component = "exportLink", value = EventConstants.ACTION)
    public StreamResponse onExportClick() {
        return getExport();
    }

    /*
    This method gives pdf report for website so that it can be downloaded by user
    */
    private StreamResponse getExport() {
        return new StreamResponse() {
            @Override
            public void prepareResponse(Response response) {
                setHeaders(response, FILENAME);
            }

            @Override
            public InputStream getStream() throws IOException {
                FileSystem fileSystems = FileSystems.getDefault();
                Path filePath = fileSystems.getPath("PDF/" + FILENAME + ".pdf").normalize();
                File file = filePath.toFile();
                return new FileInputStream(file);
            }

            @Override
            public String getContentType() {
                return "text/pdf";
            }
        };
    }

    /*
    This method is used for setting headers required for downloading the report
    */
    public static void setHeaders(Response response, String fileName) {
        response.setHeader(
                "Content-Disposition",
                "attachment; filename=" + '"' + fileName + ".pdf" + '"');
    }
}

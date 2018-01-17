package com.zensar.uac.web.crawler.util;

import java.io.IOException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfWriter;
import com.zensar.uac.web.crawler.constants.CrawlerConstants;

/**
 * Created by Srikant Singh on 10/20/2016.
 * Purpose of the class: Setting the background of paf report
 * Sponsor: www.zensar.com
 * License: This code is released under GPL. You are free to make changes to the code as long as you provide
 * attribution to the Sponsor.
 */
public class DraftBackground extends PdfPageEventHelper {

    private static final Logger LOGGER = Logger.getLogger(DraftBackground.class.getName());

    @Override
    public void onEndPage(PdfWriter writer, Document document) {

        try {
            ClassLoader cl = DraftBackground.class.getClassLoader();
            URL imgURL = null;

            imgURL = cl.getResource("PDF/images/draft.png");


            Image background = Image.getInstance(imgURL);

            float width = document.getPageSize().getWidth();
            float height = document.getPageSize().getHeight();
            writer.getDirectContentUnder().addImage(background, width, 0, 0,
                    height, 0, 0);
        } catch (IOException | DocumentException e) {
            LOGGER.log(Level.SEVERE, CrawlerConstants.EXCEPTION, e);
        }
    }

}

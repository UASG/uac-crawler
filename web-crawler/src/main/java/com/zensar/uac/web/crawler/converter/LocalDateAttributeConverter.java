package com.zensar.uac.web.crawler.converter;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.sql.Date;
import java.time.LocalDate;

/**
 * Created by kiran.govind on 07/20/2016.
 * Purpose of the class: This is converter to convert LocalDate in java 8 to SQL date
 * Sponsor: www.zensar.com
 * License: This code is released under GPL. You are free to make changes to the code as long as you provide
 * attribution to the Sponsor.
 */
@Converter(autoApply = true)
public class LocalDateAttributeConverter implements AttributeConverter<LocalDate, Date> {

    @Override
    public Date convertToDatabaseColumn(LocalDate entityValue) {
        return (entityValue == null ? null : Date.valueOf(entityValue));
    }

    @Override
    public LocalDate convertToEntityAttribute(Date databaseValue) {
        return (databaseValue == null ? null : databaseValue.toLocalDate());
    }
}

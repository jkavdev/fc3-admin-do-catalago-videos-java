package br.com.jkavdev.fullcycle.admin.catalogo.infrastructure.video.persistence;

import br.com.jkavdev.fullcycle.admin.catalogo.domain.video.Rating;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class VideoConverter implements AttributeConverter<Rating, String> {
    @Override
    public String convertToDatabaseColumn(Rating attribute) {
        System.out.println("convertToDatabaseColumn " + attribute);
        if (attribute == null) {
            return null;
        }
        return attribute.getName();
    }

    @Override
    public Rating convertToEntityAttribute(String dbData) {
        System.out.println("convertToEntityAttribute " + dbData);
        if (dbData == null) {
            return null;
        }
        return Rating.of(dbData).orElse(null);
    }
}

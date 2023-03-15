package br.com.jkavdev.fullcycle.admin.catalogo.infrastructure.configuration.properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;

public class StorageProperties implements InitializingBean {

    private static final Logger log = LoggerFactory.getLogger(StorageProperties.class);

    private String fileNamePattern;
    private String locationPattern;

    public StorageProperties() {
    }

    public String fileNamePattern() {
        return fileNamePattern;
    }

    public void setFileNamePattern(String fileNamePattern) {
        this.fileNamePattern = fileNamePattern;
    }

    public String locationPattern() {
        return locationPattern;
    }

    public void setLocationPattern(String locationPattern) {
        this.locationPattern = locationPattern;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        log.debug(this.toString());
    }

    @Override
    public String toString() {
        return "StorageProperties{" +
                "fileNamePattern='" + fileNamePattern + '\'' +
                ", locationPattern='" + locationPattern + '\'' +
                '}';
    }
}

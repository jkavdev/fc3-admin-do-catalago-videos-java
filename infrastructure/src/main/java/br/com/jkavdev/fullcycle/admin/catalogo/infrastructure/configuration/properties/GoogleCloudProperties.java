package br.com.jkavdev.fullcycle.admin.catalogo.infrastructure.configuration.properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;

public class GoogleCloudProperties implements InitializingBean {

    private static final Logger log = LoggerFactory.getLogger(GoogleCloudProperties.class);

    private String credentials;

    private String projectId;

    public String getCredentials() {
        return credentials;
    }

    public void setCredentials(String credentials) {
        this.credentials = credentials;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        log.debug(this.toString());
    }

    @Override
    public String toString() {
        return "GoogleCloudProperties{" +
                "projectId='" + projectId + '\'' +
                '}';
    }
}

package com.npn.updater.model;

import java.util.List;
import java.util.Properties;

/**Базовый клас работы с базы данных. Содержит всю информацию по свойствам соединения и список запросов который необходимо выполинить.
 *
 */
public class DbOperationItem {
    private List<String> sqlQuery;
    private String database;
    private String host;
    private String port;
    private Properties connectionProperties;
    private DbType dbType;

    public DbOperationItem() {
    }

    public List<String> getSqlQuery() {
        return sqlQuery;
    }

    public void setSqlQuery(List<String> sqlQuery) {
        this.sqlQuery = sqlQuery;
    }

    public String getDatabase() {
        return database;
    }

    public void setDatabase(String database) {
        this.database = database;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public Properties getConnectionProperties() {
        return connectionProperties;
    }

    public void setConnectionProperties(Properties connectionProperties) {
        this.connectionProperties = connectionProperties;
    }

    public DbType getDbType() {
        return dbType;
    }

    public void setDbType(DbType dbType) {
        this.dbType = dbType;
    }
}

package com.npn.dbdeploy.model;

import java.util.List;
import java.util.Objects;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DbOperationItem that = (DbOperationItem) o;
        return Objects.equals(sqlQuery, that.sqlQuery) &&
                Objects.equals(database, that.database) &&
                Objects.equals(host, that.host) &&
                Objects.equals(port, that.port) &&
                Objects.equals(connectionProperties, that.connectionProperties) &&
                dbType == that.dbType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(sqlQuery, database, host, port, connectionProperties, dbType);
    }
}

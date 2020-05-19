package com.npn.dbdeploy.drivers;


import com.npn.dbdeploy.exception.RollbackException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Properties;

@SuppressWarnings("JavadocReference")
public class MysqlDriver extends SQLDriver {
    private static final Logger logger = LoggerFactory.getLogger(MysqlDriver.class);

    /**
     * Конструктор класса SQLDriver
     *
     * @param database             имя базы данных, not null
     * @param host                 имя хоста, может быть null
     * @param port                 имя порта, может быть null
     * @param connectionProperties {@link Map} с дополнительными параметрами подключения, может быть null
     */
    public MysqlDriver(String database, String host, String port, Properties connectionProperties) {
        super(database, host, port, connectionProperties);
        JDBC_URI_PREFIX = "jdbc:mysql:";
        JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    }

    /**
     * Выполняет запросы. При ошибке выполняет rollback. В случае невозможности rollback будет выброшен
     * {@link RollbackException}.
     *
     * @param sqlQuery - лист с запросами
     * @throws RollbackException при ошибка
     */
    @Override
    public void executeStatements(List<String> sqlQuery) throws RollbackException, SQLException, ClassNotFoundException {
        logger.debug("executeStatements");
        logger.info("Start executing queries");
        super.executeStatements(sqlQuery);
        logger.info("End executing queries");
    }


    /**
     * Выполняет запрос
     *
     * @param query     запрос
     * @param statement {@link Statement}
     * @throws SQLException
     */
    @Override
    void executeQuery(String query, Statement statement) throws SQLException {
        logger.info("Execute sql: {}",query);
        super.executeQuery(query, statement);
    }

    /**
     * Rollback
     *
     * @param connection
     * @throws SQLException
     */
    @Override
    void rollback(Connection connection) throws SQLException {
        logger.info("Rollback operations");
        super.rollback(connection);
    }

    @Override
    String getDatabaseUrl() {
        logger.debug("getDatabaseUrl");
        return super.getDatabaseUrl();
    }

    /**
     * Создает {@link Connection}
     *
     * @param driver
     * @return {@link Connection}
     * @throws SQLException           при ошибке
     * @throws ClassNotFoundException при ошибке при регистрации JDBC драйвера
     */
    @Override
    Connection getConnection(String driver) throws SQLException, ClassNotFoundException {
        logger.debug("getConnection");
        logger.info("Registering JDBC driver and creating database connection");
        return super.getConnection(driver);
    }
}

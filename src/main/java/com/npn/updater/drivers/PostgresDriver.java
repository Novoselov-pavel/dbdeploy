package com.npn.updater.drivers;

import com.npn.updater.exception.RollbackException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**Реализует подключение и работу с базой данных PostrgreSQL  <a href="https://postgrespro.ru">https://postgrespro.ru</a>
 *
 */
public class PostgresDriver extends SQLDriver {
    private static final Logger logger = LoggerFactory.getLogger(PostgresDriver.class);

    /**
     * Конструктор класса PostrgreSQL
     *
     * @param database             имя базы данных, not null
     * @param host                 имя хоста, может быть null
     * @param port                 имя порта, может быть null
     * @param connectionProperties {@link Map} с дополнительными параметрами подключения, может быть null
     */
    public PostgresDriver(String database, String host, String port, Properties connectionProperties) {
        super(database, host, port, connectionProperties);
        JDBC_URI_PREFIX = "jdbc:postgresql:";
        JDBC_DRIVER = "org.postgresql.Driver";
    }


    /**
     * Выполняет запросы. При ошибке выполняет rollback. В случае невозможности rollback будет выброшен
     * {@link RollbackException}.
     *
     * @param sqlQuery - лист с запросами
     * @throws RollbackException при ошибка
     */
    @Override
    public void executeStatements(List<String> sqlQuery) throws RollbackException {
        logger.debug("executeStatements");
        logger.info("Start executing queries");
        super.executeStatements(sqlQuery);
        logger.info("End executing queries");
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

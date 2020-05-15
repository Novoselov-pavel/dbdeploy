package com.npn.updater.drivers;

import com.npn.updater.exception.RollbackException;
import com.npn.updater.interfaces.DbInterface;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

/**Реализует подключение и роаботу с базой данных PostrgreSQL  <a href="https://postgrespro.ru">https://postgrespro.ru</a>
 *
 */
public class PostgresDriver implements DbInterface {
    private static final Logger logger = LoggerFactory.getLogger(PostgresDriver.class);
    private static final String  JDBC_DRIVER = "org.postgresql.Driver";
    /**jdbc:postgresql:database
     *jdbc:postgresql:
     *jdbc:postgresql://host/database
     *jdbc:postgresql://host/
     *jdbc:postgresql://host:port/database
     *jdbc:postgresql://host:port/
     *
     */
    private final String DATABASE_URL;
    private final Properties connectionProperties;

    /**Конструктор класса PostgresDriver
     *
     * @param database имя базы данных, not null
     * @param host имя хоста, может быть null
     * @param port имя порта, может быть null
     * @param connectionProperties {@link Map} с дополнительными параметрами подключения, может быть null
     */
    public PostgresDriver(final String database, final String host, final String port, final Properties connectionProperties) {
        StringBuilder builder = new StringBuilder();
        builder.append("jdbc:postgresql:");

        if (host!=null) {
            builder.append("//").append(host);
            if (port!=null) {
                builder.append(":").append(port);
            }
            builder.append("/");
        }

        if (database!=null) {
            builder.append(database);
        } else {
            throw new IllegalArgumentException("Database name is null");
        }

        this.connectionProperties = connectionProperties;

        DATABASE_URL = builder.toString();

    }


    /**Выполняет запросы. При ошибке выполняет rollback. В случае невозможности rollback будет выброшен
     * {@link RollbackException}.
     *
     * @param sqlQuery - лист с запросами
     * @throws RollbackException при ошибка
     */
    @Override
    public void executeStatements(List<String> sqlQuery) throws RollbackException {
        Connection connection = null;
        try{
            connection = getConnection();
            connection.setAutoCommit(false);
            Statement statement = connection.createStatement();
            for (String s : sqlQuery) {
                statement.execute(s);
            }
            connection.commit();
            statement.close();
            connection.close();
        } catch (SQLException e) {
            if (connection!=null) {
                try{
                    connection.rollback();
                } catch (SQLException error) {
                    throw new RollbackException("Rollback failed", error);
                }
            }
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    /**Создает {@link Connection}
     *
     * @return {@link Connection}
     * @throws SQLException при ошибке
     * @throws ClassNotFoundException при ошибке при регистрации JDBC драйвера
     */
    private Connection getConnection() throws SQLException, ClassNotFoundException {
        logger.info("Registering JDBC driver");
        Class.forName(JDBC_DRIVER);
        logger.info("Creating database connection");
        return DriverManager.getConnection(DATABASE_URL,connectionProperties);
    }









}

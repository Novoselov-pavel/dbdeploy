package com.npn.updater.drivers;

import com.npn.updater.exception.RollbackException;
import com.npn.updater.interfaces.DbInterface;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**Реализует подключение и работу с базами данных
 *
 */
public abstract class SQLDriver implements DbInterface {
    /**
     * Для примера jdbc:postgresql:
     */
    String  JDBC_URI_PREFIX;

    /**
     * Для примера org.postgresql.Driver:
     */
    String  JDBC_DRIVER;


    String database;
    String host;
    String port;
    Properties connectionProperties;

    /**Конструктор класса SQLDriver
     *
     * @param database имя базы данных, not null
     * @param host имя хоста, может быть null
     * @param port имя порта, может быть null
     * @param connectionProperties {@link Map} с дополнительными параметрами подключения, может быть null
     */
    public SQLDriver(final String database, final String host, final String port, final Properties connectionProperties) {
        this.database = database;
        this.host = host;
        this.port = port;
        this.connectionProperties = connectionProperties;
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
            connection = getConnection(JDBC_DRIVER);
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


    String getDatabaseUrl() {
        StringBuilder builder = new StringBuilder();
        builder.append(JDBC_URI_PREFIX);

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

        return builder.toString();
    }


    /**Создает {@link Connection}
     *
     * @return {@link Connection}
     * @throws SQLException при ошибке
     * @throws ClassNotFoundException при ошибке при регистрации JDBC драйвера
     */
     Connection getConnection(String driver) throws SQLException, ClassNotFoundException {
        Class.forName(driver);
        return DriverManager.getConnection(getDatabaseUrl(),connectionProperties);
    }
}

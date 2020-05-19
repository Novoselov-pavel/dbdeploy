package com.npn.dbdeploy.model;

import com.npn.dbdeploy.drivers.MysqlDriver;
import com.npn.dbdeploy.drivers.PostgresDriver;
import com.npn.dbdeploy.interfaces.DbInterface;

/**Фабрика интерфейса доступа работы с базой данных
 *
 */
public class DbInterfaceFabric {


    /**Фабрика интерфейса доступа работы с базой данных
     *
     * @param operation {@link DbOperationItem} описывает подключение и операции с базой данных
     * @return реализацию DbInterface
     * @throws IllegalArgumentException при ошибке
     */
    public static DbInterface getInterface(DbOperationItem operation) {
        if (operation.getDbType()==DbType.POSTGRES) {
            return new PostgresDriver(operation.getDatabase(),operation.getHost(),operation.getPort(),operation.getConnectionProperties());
        } else if (operation.getDbType()==DbType.MYSQL) {
            return new MysqlDriver(operation.getDatabase(),operation.getHost(),operation.getPort(),operation.getConnectionProperties());
        } else {
            throw new IllegalArgumentException("Illegal dbType");
        }
    }

}

package com.npn.dbdeploy.interfaces;

import com.npn.dbdeploy.exception.RollbackException;

import java.sql.SQLException;
import java.util.List;

/**Интрефейс по работе с базой данных
 *
 */
public interface DbInterface {

    /**Выполняет запросы. При ошибке выполняет rollback. В случае невозможности rollback будет выброшен
     * {@link RollbackException}.
     *
     * @param sqlQuery - лист с запросами
     * @throws RollbackException - ошибка отката изменений
     * @throws SQLException - ошибка запроса
     * @throws ClassNotFoundException - ошибка регистрации драйвера
     */
    void executeStatements(List<String> sqlQuery) throws RollbackException, SQLException, ClassNotFoundException;
}

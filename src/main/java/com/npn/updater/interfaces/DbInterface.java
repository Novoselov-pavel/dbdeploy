package com.npn.updater.interfaces;

import com.npn.updater.exception.RollbackException;

import java.util.List;

/**Интрефейс по работе с базой данных
 *
 */
public interface DbInterface {

    /**Выполняет запросы. При ошибке выполняет rollback. В случае невозможности rollback будет выброшен
     * {@link RollbackException}.
     *
     * @param sqlQuery - лист с запросами
     * @throws RollbackException при ошибка
     */
    void executeStatements(List<String> sqlQuery) throws RollbackException;
}

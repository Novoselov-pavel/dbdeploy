package com.npn.updater.drivers;

import com.npn.updater.exception.RollbackException;
import com.npn.updater.interfaces.DbInterface;

import java.util.List;

public class MysqlDriver implements DbInterface {
    @Override
    public void executeStatements(List<String> sqlQuery) throws RollbackException {
        //TODO
    }
}

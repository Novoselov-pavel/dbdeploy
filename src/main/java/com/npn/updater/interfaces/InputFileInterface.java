package com.npn.updater.interfaces;

import com.npn.updater.model.DbOperationItem;

public interface InputFileInterface {

    DbOperationItem getOperation(String filePath);

}

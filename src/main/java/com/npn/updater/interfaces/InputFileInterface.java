package com.npn.updater.interfaces;

import com.npn.updater.model.DbOperationItem;

import java.util.Set;

/**Интерфейс для получения класса {@link DbOperationItem} из конфигируционного файла
 *
 */
public interface InputFileInterface {

    /**Возвращает {@link DbOperationItem} из конфигируционного файла
     *
     * @param filePath путь к файлу
     * @return {@link DbOperationItem}
     */
    DbOperationItem getOperation(String filePath) throws Exception;

    /**Выдает список расширений файлов которые обрабатываются текущей реализацией интерфейса
     *
     * @return
     */
    Set<String> getExtension();

}

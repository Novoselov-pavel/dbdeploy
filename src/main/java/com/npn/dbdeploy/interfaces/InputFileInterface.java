package com.npn.dbdeploy.interfaces;

import com.npn.dbdeploy.model.DbOperationItem;

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

    /**Проверяет расширение переданного файла на соответствие списку расширений
     *
     * @param path путь к файлу
     * @return true если совпадает, иначе false. Если путь null - false.
     */
     boolean isFileHasCorrectExtension(String path);
}

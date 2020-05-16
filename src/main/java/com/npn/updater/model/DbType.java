package com.npn.updater.model;

public enum DbType {
    MYSQL, POSTGRES;

    /**Возвращает элемент {@link DbType} по текстовому значению
     *
     * @param element  строка соответсвующая значению enum, регистр игноритуется.
     * @return элемент {@link DbType} или NULL при отсутствии соответствуюшего значения.
     */
    public static DbType getDbType(final String element) {
        if (element == null) return null;

        try {
            return DbType.valueOf(element.toUpperCase());
        } catch (IllegalArgumentException e) {
            return null;
        }
    }
}

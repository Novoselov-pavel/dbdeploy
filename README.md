# dbdeploy
<h3>v.1.0.0</h3>
Выполняет запросы из xml файла (любое количество).
В состав включены драйверы для PostrgreSQL (протестированно) и MysqlDriver(не тестированно).

Путь к файлу с запросами передается в командной строке 1 аргументом, если он отсутствует будет открыт диалог для выбора файла. 

Пример разметки XML файла:

<pre>
&lt;?xml version="1.0" encoding="utf-8" ?&gt;
&lt;class&gt;
    &lt;dbtype&gt;POSTGRES&lt;/dbtype&gt;
    &lt;database&gt;test&lt;/database&gt;
    &lt;host&gt;localhost&lt;/host&gt;
    &lt;port&gt;5432&lt;/port&gt;
    &lt;connectionProperties&gt;
        &lt;item&gt;user=user&lt;/item&gt;
        &lt;item&gt;password=password&lt;/item&gt;
    &lt;/connectionProperties&gt;
    &lt;query&gt;
        &lt;item&gt;DROP TABLE IF EXISTS a1;&lt;/item&gt;
        &lt;item&gt;CREATE TABLE a1 (id bigint PRIMARY KEY, name varchar(255));&lt;/item&gt;
    &lt;/query&gt;
&lt;/class&gt;
</pre>
Поддерживаемые значения элемента <dbtype></dbtype> - MYSQL,POSTGRES, регист игнорируется.


Использованы: JDBC, Slf4j, Swing, DOM XML.

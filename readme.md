Florian Jenett's library that wraps around JDBC drivers to let you access MySQL, SQLite and PostgreSQL databases. [Link to original project](https://github.com/fjenett/sql-library-processing)

## Latest Release

~~Latest release (same that is in Processing's library manager):
https://raw.github.com/fjenett/sql-library-processing/latest/release/BezierSQLib.zip~~

This fork has been modified slightly for Processing 3. It's now available in the Contribution Manager in the PDE, or you can install manually by unzipping this folder into your libraries folder:
https://github.com/benfry/sql-library-processing/raw/master/release/BezierSQLib.zip

## Features

It's not an ORM (and will not become one) but it has some handy features
since v0.2.0 that makes it easier to load and save objects to a database.

    setFromRow(object)
    saveToDatabase(object)
    insertUpdateIntoDatabase(table, columns, values)

See:
https://raw.github.com/fjenett/sql-library-processing/master/src/de/bezier/data/sql/SQL.java

## Database Drivers

Parts of this library are available under their own licenses. These are redistributed as downloaded from the original sources:

MySQL Connector/J (JDBC driver)
mysql-connector-java-3.1.8-bin.jar
http://dev.mysql.com/downloads/connector/j/
http://dev.mysql.com/downloads/connector/j/3.1.html
GPL, http://www.gnu.org/licenses/old-licenses/gpl-2.0.html

PostgreSQL JDBC driver
postgresql-8.3-604.jdbc3.jar
http://jdbc.postgresql.org/
http://jdbc.postgresql.org/download.html
BSD, http://jdbc.postgresql.org/license.html

SQLite JDBC driver
sqlite-jdbc-3.7.2.jar
http://code.google.com/p/sqlite-jdbc/
Apache License 2.0, http://www.apache.org/licenses/LICENSE-2.0


**UTF-8 notes here:**

http://dev.mysql.com/doc/refman/5.1/en/connector-j-reference-charsets.html

---

**Formatting queries:**

db.query( String )
db.query( String, Object ... args ) {
	db.query( String.format( String, args ) )
}

or just use a [http://docs.oracle.com/javase/6/docs/api/java/sql/PreparedStatement.html PreparedStatement] which seems to bring in

select("table_name").all()
select("table_name").where("name",Object).groupBy("foo")

---

**ORM**

http://www.hibernate.org/

http://sourceforge.net/projects/crossdb/

---

**Convenience**

Create a class inspector that will generate SQL that is needed to store class in a DB

---

SQLite

- create db file (file missing ...)
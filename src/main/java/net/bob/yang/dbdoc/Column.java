package net.bob.yang.dbdoc;

public class Column {
	private String name;
	private String comment;
	private String columnType;
	
	public Column(String name, String comment, String columnType) {
		super();
		this.name = name;
		this.comment = comment;
		this.columnType = columnType;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public String getColumnType() {
		return columnType;
	}
	public void setColumnType(String columnType) {
		this.columnType = columnType;
	}
}

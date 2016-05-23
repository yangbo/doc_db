package net.bob.yang.dbdoc;

import java.util.List;

public class Table {

	private String name;
	private String comment;
	private List<Column> columns;
	
	public Table(String name, String comment) {
		super();
		this.name = name;
		this.comment = comment;
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
	public List<Column> getColumns() {
		return columns;
	}
	public void setColumns(List<Column> columns) {
		this.columns = columns;
	}
}

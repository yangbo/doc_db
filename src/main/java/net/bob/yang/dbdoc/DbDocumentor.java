package net.bob.yang.dbdoc;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class DbDocumentor {
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	public void document(String databaseName, OutputStream output) throws SQLException, IOException {
		List<Table> tables = getTablesInDB(databaseName);
		// renderToPlainText(tables, output);
		renderToWord(tables, output);
	}

	private void renderToWord(List<Table> tables, OutputStream output) throws IOException {
		XWPFDocument doc = new XWPFDocument();
		XWPFParagraph paragraph = doc.createParagraph();
		paragraph.setAlignment(ParagraphAlignment.CENTER);
		XWPFRun paragraphRun = paragraph.createRun();
		paragraphRun.setBold(true);
		paragraphRun.setFontSize(20);
		paragraphRun.setText("你好 POI XWPF!");
		doc.write(output);
		output.close();
	}
	
	private void renderToPlainText(List<Table> tables, OutputStream output) throws UnsupportedEncodingException {
		PrintStream printer = new PrintStream(output, true, "UTF-8");
		tables.stream().forEach(t -> {
			printer.printf("\n\n表 %s (%s)\n\n", t.getName(), t.getComment());
			t.getColumns().forEach(column -> 
				//printer.printf("%25s: %s\n", column.getName(), column.getComment()));
				printer.printf("%s\t%s\t%s\n", column.getName(), column.getColumnType(), column.getComment()));
		});
	}

	private List<Table> getTablesInDB(String databaseName) throws SQLException {
		String catalog = null;
		String schemaPattern = null;
		String tableNamePattern = null;
		String[] types = null;
		
		DatabaseMetaData metaData = jdbcTemplate.getDataSource().getConnection().getMetaData();
		ResultSet resultSet = metaData.getTables(catalog, schemaPattern, tableNamePattern, types);
		LinkedList<Table> tableList = new LinkedList<>();
		/* 打印 DatabaseMetaData 的 meta data.
		ResultSetMetaData resultMetaData = resultSet.getMetaData();
		int count = resultMetaData.getColumnCount();
		for (int i=1; i<=count; i++) {
			String name = resultMetaData.getColumnName(i);
			System.out.println("column: " + name);
		}
		*/
		while(resultSet.next()) {
			String tableName = resultSet.getString("TABLE_NAME");
			String comment = resultSet.getString("REMARKS");
			Table table = new Table(tableName, comment);
			String columnNamePattern = null;
			ResultSet columnResultSet = metaData.getColumns(catalog, schemaPattern, tableName, columnNamePattern);
			List<Column> columns = getColumns(columnResultSet);
			table.setColumns(columns);
			tableList.add(table);
		}
		
		return tableList;
	}

	// 取字段信息
	private List<Column> getColumns(ResultSet columnResultSet) throws SQLException {
		LinkedList<Column> columnList = new LinkedList<>();
		while(columnResultSet.next()) {
			String columnName = columnResultSet.getString("COLUMN_NAME");
			String comment = columnResultSet.getString("REMARKS");
			String columnType = columnResultSet.getString("TYPE_NAME");
			columnList.add(new Column(columnName, comment, columnType));
		}
		return columnList;
	}
}

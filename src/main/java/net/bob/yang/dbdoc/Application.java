package net.bob.yang.dbdoc;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.SQLException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class Application {
	
	public static void main(String[] args) throws SQLException, IOException {
		ConfigurableApplicationContext context = SpringApplication.run(Application.class, args);
		DbDocumentor documentor = context.getBean(DbDocumentor.class);
		//OutputStream output = System.out;
		OutputStream output = new FileOutputStream(new File("c:/data/db.docx"));
		String databaseName = null;
		documentor.document(databaseName, output);
	}
}

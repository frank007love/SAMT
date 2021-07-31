package ntut.csie.samt;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Writer;
import java.util.Iterator;

public class FileCopy {

	/**
	 * Copy File from source to target
	 * @param source
	 * @param target
	 */
	public static void copy(File source,File target){

		try{
			BufferedReader reader = null;
			Writer writer = null;
			try{
				if( !target.exists() ){
					target.createNewFile();
				}
				
				reader = new BufferedReader(new FileReader(source));
				writer = new FileWriter(target);
				
				String line ="";
				
	            while ((line = reader.readLine()) != null) {
	            	line = line.trim() + "\n";
	            	writer.write(line);
	            }
			
	            writer.close();
	            reader.close();
	            
			} catch (IOException e){
				e.printStackTrace();
				writer.close();
		        reader.close();
			}
		} catch (IOException e){
			e.printStackTrace();
		}
			
	}

}

package es.fer.encierros.funciones;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import android.os.Environment;

public class InputStreamToFile {
	
	public static void createFile(InputStream inputStream) {
		OutputStream outputStream = null;

		try {
			// write the inputStream to a FileOutputStream
			File dir = new File (Environment.getExternalStorageDirectory().getAbsolutePath() + "/.data/encierros/sec"); 
			dir.mkdirs(); 
			File file = new File(dir, "Encierros.p12");
			outputStream = new FileOutputStream(file);
			
			int read = 0;
			byte[] bytes = new byte[1024];

			while ((read = inputStream.read(bytes)) != -1) {
				outputStream.write(bytes, 0, read);
			}

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (inputStream != null) {
				try {
					inputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (outputStream != null) {
				try {
					// outputStream.flush();
					outputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}

			}
		}
	}
}
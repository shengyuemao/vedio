package maomo.vedio.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import android.os.Environment;

public class FileUtil
{
	/**
	 * 检查SD卡是否存在
	 * 
	 * @return
	 */
	public static boolean hasSdcard()
	{
		if (Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED))
		{
			return true;
		} else
		{
			return false;
		}
	}

	/**
	 * 读取SD卡中的数据
	 * 
	 * @param file
	 *            File文件
	 * @return
	 * @throws IOException
	 */
	public static byte[] readFileFromSdcard(File file) throws IOException
	{
		FileInputStream fileInputStream = new FileInputStream(file);
		int length = fileInputStream.available();
		byte[] buffer = new byte[length];
		fileInputStream.read(buffer);
		fileInputStream.close();
		return buffer;
	}

	/**
	 * 写数据到SD卡中
	 * 
	 * @param filePath
	 *            文件路径
	 * @param buffer
	 * @throws IOException
	 */
	public static void writeFiletoSdcard(String filePath, byte[] buffer)
			throws IOException
	{
		File file = new File(filePath);
		FileOutputStream fileOutputStream = new FileOutputStream(file);
		fileOutputStream.write(buffer);
		fileOutputStream.close();
	}

}

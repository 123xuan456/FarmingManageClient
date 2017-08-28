package com.ruicheng.farmingmanageclient.utils;

import android.content.ContentResolver;
import android.content.Context;
import android.net.Uri;
import android.os.ParcelFileDescriptor;
import android.text.TextUtils;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.text.DecimalFormat;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

public class FileUtils {
	
	public static final String TAG = "FileUtil";
	public static final int FILE_BUFFER_SIZE = 51200;
	
	/**
	 * 取得文件夹大long 单位为M
	 * 
	 * @param f
	 * @return
	 * @throws Exception
	 */
	public static long getFileSize(File f) throws Exception {
		long size = 0;
		File flist[] = f.listFiles();
		for (int i = 0; i < flist.length; i++) {
			if (flist[i].isDirectory()) {
				size = size + getFileSize(flist[i]);
			} else {
				size = size + flist[i].length();
			}
		}
		return size;
	}

	/**
	 * 转换文件大小
	 * 
	 * @param fileS
	 * @return
	 */
	public static String FormetFileSize(long fileS) {
		DecimalFormat df = new DecimalFormat("#.00");
		String fileSizeString = "";
		if (fileS == 0) {
			fileSizeString = "0KB";
		} else if (fileS < 1024) {
			fileSizeString = df.format((double) fileS) + "B";
		} else if (fileS < 1048576) {
			fileSizeString = df.format((double) fileS / 1024) + "KB";
		} else if (fileS < 1073741824) {
			fileSizeString = df.format((double) fileS / 1048576) + "MB";
		} else {
			fileSizeString = df.format((double) fileS / 1073741824) + "G";
		}
		return fileSizeString;
	}

	/**
	 * 删除指定目录下文件及目录
	 * 
	 * @param filePath
	 * @param deleteThisPath
	 * @throws IOException
	 */
	public static void deleteFolderFile(String filePath, boolean deleteThisPath)
			throws IOException {
		if (!TextUtils.isEmpty(filePath)) {
			File file = new File(filePath);
			if (file.isDirectory()) {// 处理目录
				File files[] = file.listFiles();
				for (int i = 0; i < files.length; i++) {
					deleteFolderFile(files[i].getAbsolutePath(), true);
				}
			}
			if (deleteThisPath) {
				// 如果是文件，删除
				if (!file.isDirectory()) {
					file.delete();
				} else {
					// 目录下没有文件或者目录，删除
					if (file.listFiles().length == 0) {
						file.delete();
					}
				}
			}
		}
	}

	/**
	 * 获取文
	 * @param path
	 * @return
	 */
	public static String getFileName(String path) {
		int start = path.lastIndexOf("/") + 1;
		return path.substring(start);
	}

	/**
	 * 获取文件
	 * @param file
	 * @return
	 */
	public static String getFileName(File file) {
		String filePath = file.getAbsolutePath();
		return getFileName(filePath);
	}
	
	/**
	 * 获取工程包名(com.ab.cde)
	 * @return
	 */
	private static String getProjectPackage() {
		String[] split = FileUtils.class.getPackage().toString().split("\\.");
		return split[0]+"."+split[1]+"."+split[2];
	}
	/**
	 * 获取工程包名第三个字段（为项目名称）(com.ab.cde:cde为项目名
	 * @return
	 */
	private static String getProjectName() {
		String packageName = FileUtils.class.getPackage().toString();
		String[] split = packageName.split("[.]");
		return split[2];
	}
	
	/**
	 * 根据路径创建目录文件
	 * @param filePath
	 * @return
	 */
	public static boolean createDirectory(String filePath) {
		if (null == filePath) {
			return false;
		}
		File file = new File(filePath);
		if (file.exists()) {
			return true;
		}
		return file.mkdirs();
	}
	
	/**
	 * 根据路径删除目录文件夹及内容
	 * @param filePath
	 * @return
	 */
	public static boolean deleteDirectory(String filePath) {
		if (null == filePath) {
			return false;
		}
		File file = new File(filePath);

		if (file == null || !file.exists()) {
			return false;
		}

		if (file.isDirectory()) {
			File[] list = file.listFiles();

			for (int i = 0; i < list.length; i++) {
				if (list[i].isDirectory()) {
					deleteDirectory(list[i].getAbsolutePath());
				} else {
					list[i].delete();
				}
			}
		}
		file.delete();
		return true;
	}
	
	/**
	 * 保存的根目录
	 * 
	 * @return
	 */
	/*public static String getRootFilePath() {
		if (SDCardUtils.isSdCardExists()) {
			return Environment.getExternalStorageDirectory().getAbsolutePath()
					+ "/";// filePath:/sdcard/
		} else {
			return Environment.getDataDirectory().getAbsolutePath() + "/data/"; // filePath:
		}
	}*/
	
	/**
	 * 根据路径判断文件是否存在
	 * @param filePath
	 * @return
	 */
	public static boolean fileIsExist(String filePath) {
		/*if (filePath == null || filePath.length() < 1) {
			Log.e(TAG,"filePath:" + filePath);
			return false;
		}*/

		File f = new File(filePath);
		if (!f.exists()) {
			return false;
		}
		return true;
	}
	
	/**
	 * 根据路径获取文件
	 * @param filePath
	 * @return
	 */
	public static InputStream readFile(String filePath) {
		if (null == filePath) {
			return null;
		}
		InputStream is = null;
		try {
			if (fileIsExist(filePath)) {
				File f = new File(filePath);
				is = new FileInputStream(f);
			} else {
				return null;
			}
		} catch (Exception ex) {
			Log.e(TAG,"readFile Exception:" + ex.toString());
			return null;
		}
		return is;
	}
	
	/**
	 * 根据流文件写入指定路径生成的文件
	 * @param filePath
	 * @param inputStream
	 * @return
	 */
	public static boolean writeFile(String filePath, InputStream inputStream) {
		if (null == filePath || filePath.length() < 1) {
			return false;
		}
		try {
			File file = new File(filePath);
			if (file.exists()) {
				deleteDirectory(filePath);
			}
			String pth = filePath.substring(0, filePath.lastIndexOf("/"));
			boolean ret = createDirectory(pth);
			if (!ret) {
				return false;
			}
			boolean ret1 = file.createNewFile();
			if (!ret) {
				return false;
			}
			FileOutputStream fileOutputStream = new FileOutputStream(file);
			byte[] buf = new byte[1024];
			int c = inputStream.read(buf);
			while (-1 != c) {
				fileOutputStream.write(buf, 0, c);
				c = inputStream.read(buf);
			}
			fileOutputStream.flush();
			fileOutputStream.close();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	/**
	 * 把内容写到指定路径生成的文件
	 * @param filePath
	 * @param fileContent
	 * @param append
	 * @return
	 */
	public static boolean writeFile(String filePath, String fileContent,
			boolean append) {
		if (null == filePath || fileContent == null || filePath.length() < 1
				|| fileContent.length() < 1) {
			return false;
		}
		try {
			File file = new File(filePath);
			if (!file.exists()) {
				if (!file.createNewFile()) {
					return false;
				}
			}
			BufferedWriter output = new BufferedWriter(new FileWriter(file,
					append));
			output.write(fileContent);
			output.flush();
			output.close();
		} catch (IOException ioe) {
			Log.e(TAG,"writeFile ioe:" + ioe.toString());
			return false;
		}
		return true;
	}
	
	/**
	 * 获取文件大小
	 * @param filePath
	 * @return
	 */
	public static long getFileSize(String filePath) {
		if (null == filePath) {
			return 0;
		}

		File file = new File(filePath);
		if (file == null || !file.exists()) {
			return 0;
		}

		return file.length();
	}

	public static long getFileModifyTime(String filePath) {
		if (null == filePath) {
			return 0;
		}

		File file = new File(filePath);
		if (file == null || !file.exists()) {
			return 0;
		}

		return file.lastModified();
	}

	public static boolean setFileModifyTime(String filePath, long modifyTime) {
		if (null == filePath) {
			return false;
		}

		File file = new File(filePath);
		if (file == null || !file.exists()) {
			return false;
		}

		return file.setLastModified(modifyTime);
	}

	public static boolean copyFile(ContentResolver cr, String fromPath,
			String destUri) {
		if (null == cr || null == fromPath || fromPath.length() < 1
				|| null == destUri || destUri.length() < 1) {
			return false;
		}
		InputStream is = null;
		OutputStream os = null;
		try {
			is = new FileInputStream(fromPath);
			if (null == is) {
				return false;
			}
			// check output uri
			String path = null;
			Uri uri = null;
			String lwUri = destUri.toLowerCase();
			if (lwUri.startsWith("content://")) {
				uri = Uri.parse(destUri);
			} else if (lwUri.startsWith("file://")) {
				uri = Uri.parse(destUri);
				path = uri.getPath();
			} else {
				path = destUri;
			}

			// open output
			if (null != path) {
				File fl = new File(path);
				String pth = path.substring(0, path.lastIndexOf("/"));
				File pf = new File(pth);

				if (pf.exists() && !pf.isDirectory()) {
					pf.delete();
				}

				pf = new File(pth + File.separator);

				if (!pf.exists()) {
					if (!pf.mkdirs()) {
						Log.e(TAG,"Can't make dirs, path=" + pth);
					}
				}

				pf = new File(path);
				if (pf.exists()) {
					if (pf.isDirectory())
						deleteDirectory(path);
					else
						pf.delete();
				}

				os = new FileOutputStream(path);
				fl.setLastModified(System.currentTimeMillis());
			} else {
				os = new ParcelFileDescriptor.AutoCloseOutputStream(
						cr.openFileDescriptor(uri, "w"));
			}

			// copy file
			byte[] dat = new byte[1024];
			int i = is.read(dat);
			while (-1 != i) {
				os.write(dat, 0, i);
				i = is.read(dat);
			}

			is.close();
			is = null;

			os.flush();
			os.close();
			os = null;

			return true;

		} catch (Exception ex) {
			Log.e(TAG,"copyFile Exception: " + ex.toString());
		} finally {
			if (null != is) {
				try {
					is.close();
				} catch (Exception ex) {
				}
				;
			}
			if (null != os) {
				try {
					os.close();
				} catch (Exception ex) {
				}
			}
		}
		return false;
	}

	public static byte[] readAll(InputStream is) throws Exception {
		ByteArrayOutputStream baos = new ByteArrayOutputStream(1024);
		byte[] buf = new byte[1024];
		int c = is.read(buf);
		while (-1 != c) {
			baos.write(buf, 0, c);
			c = is.read(buf);
		}
		baos.flush();
		baos.close();
		return baos.toByteArray();
	}

	public static byte[] readFile(Context ctx, Uri uri) {
		if (null == ctx || null == uri) {
			return null;
		}

		InputStream is = null;
		String scheme = uri.getScheme().toLowerCase();
		if (scheme.equals("file")) {
			is = readFile(uri.getPath());
		}

		try {
			is = ctx.getContentResolver().openInputStream(uri);
			if (null == is) {
				return null;
			}

			byte[] bret = readAll(is);
			is.close();
			is = null;

			return bret;
		} catch (FileNotFoundException fne) {
			Log.e(TAG,"readFile FilNotFoundException: " + fne.toString());
		} catch (Exception ex) {
			Log.e(TAG,"readFile Exception:" + ex.toString());
		} finally {
			if (null != is) {
				try {
					is.close();
				} catch (Exception ex) {
				}
			}
		}
		return null;
	}

	public static boolean writeFile(String filePath, byte[] content) {
		if (null == filePath || null == content) {
			return false;
		}

		FileOutputStream fos = null;
		try {
			String pth = filePath.substring(0, filePath.lastIndexOf("/"));
			File pf = null;
			pf = new File(pth);
			if (pf.exists() && !pf.isDirectory()) {
				pf.delete();
			}
			pf = new File(filePath);
			if (pf.exists()) {
				if (pf.isDirectory())
					deleteDirectory(filePath);
				else
					pf.delete();
			}

			pf = new File(pth + File.separator);
			if (!pf.exists()) {
				if (!pf.mkdirs()) {
					Log.e(TAG,"Can't make dirs, path=" + pth);
				}
			}

			fos = new FileOutputStream(filePath);
			fos.write(content);
			fos.flush();
			fos.close();
			fos = null;
			pf.setLastModified(System.currentTimeMillis());

			return true;

		} catch (Exception ex) {
			Log.e(TAG,"writeFile Exception:" + ex.toString());
		} finally {
			if (null != fos) {
				try {
					fos.close();
				} catch (Exception ex) {
				}
				;
			}
		}
		return false;
	}

	/************* ZIP file operation ***************/
	public static boolean readZipFile(String zipFileName, StringBuffer crc) {
		try {
			ZipInputStream zis = new ZipInputStream(new FileInputStream(
					zipFileName));
			ZipEntry entry;
			while ((entry = zis.getNextEntry()) != null) {
				long size = entry.getSize();
				crc.append(entry.getCrc() + ", size: " + size);
			}
			zis.close();
		} catch (Exception ex) {
			Log.e(TAG,"readZipFile Exception: " + ex.toString());
			return false;
		}
		return true;
	}

	public static byte[] readGZipFile(String zipFileName) {
		if (fileIsExist(zipFileName)) {
			try {
				FileInputStream fin = new FileInputStream(zipFileName);
				int size;
				byte[] buffer = new byte[1024];
				ByteArrayOutputStream baos = new ByteArrayOutputStream();
				while ((size = fin.read(buffer, 0, buffer.length)) != -1) {
					baos.write(buffer, 0, size);
				}
				return baos.toByteArray();
			} catch (Exception ex) {
				Log.i(TAG,"readGZipFile read zipRecorder file error");
			}
		}
		return null;
	}

	public static boolean zipFile(String baseDirName, String fileName,
			String targerFileName) throws IOException {
		if (baseDirName == null || "".equals(baseDirName)) {
			return false;
		}
		File baseDir = new File(baseDirName);
		if (!baseDir.exists() || !baseDir.isDirectory()) {
			return false;
		}

		String baseDirPath = baseDir.getAbsolutePath();
		File targerFile = new File(targerFileName);
		ZipOutputStream out = new ZipOutputStream(new FileOutputStream(
				targerFile));
		File file = new File(baseDir, fileName);

		boolean zipResult = false;
		if (file.isFile()) {
			zipResult = fileToZip(baseDirPath, file, out);
		} else {
			zipResult = dirToZip(baseDirPath, file, out);
		}
		out.close();
		return zipResult;
	}

	public static boolean unZipFile(String fileName, String unZipDir)
			throws Exception {
		File f = new File(unZipDir);

		if (!f.exists()) {
			f.mkdirs();
		}

		BufferedInputStream is = null;
		ZipEntry entry;
		ZipFile zipfile = new ZipFile(fileName);
		Enumeration<?> enumeration = zipfile.entries();
		byte data[] = new byte[FILE_BUFFER_SIZE];

		while (enumeration.hasMoreElements()) {
			entry = (ZipEntry) enumeration.nextElement();

			if (entry.isDirectory()) {
				File f1 = new File(unZipDir + "/" + entry.getName());
				if (!f1.exists()) {
					f1.mkdirs();
				}
			} else {
				is = new BufferedInputStream(zipfile.getInputStream(entry));
				int count;
				String name = unZipDir + "/" + entry.getName();
				RandomAccessFile m_randFile = null;
				File file = new File(name);
				if (file.exists()) {
					file.delete();
				}

				file.createNewFile();
				m_randFile = new RandomAccessFile(file, "rw");
				int begin = 0;

				while ((count = is.read(data, 0, FILE_BUFFER_SIZE)) != -1) {
					try {
						m_randFile.seek(begin);
					} catch (Exception ex) {
						Log.e(TAG,"unZipFile exception: " + ex.toString());
					}

					m_randFile.write(data, 0, count);
					begin = begin + count;
				}

				file.delete();
				m_randFile.close();
				is.close();
			}
		}

		return true;
	}

	private static boolean fileToZip(String baseDirPath, File file,
			ZipOutputStream out) throws IOException {
		FileInputStream in = null;
		ZipEntry entry = null;

		byte[] buffer = new byte[FILE_BUFFER_SIZE];
		int bytes_read;
		try {
			in = new FileInputStream(file);
			entry = new ZipEntry(getEntryName(baseDirPath, file));
			out.putNextEntry(entry);

			while ((bytes_read = in.read(buffer)) != -1) {
				out.write(buffer, 0, bytes_read);
			}
			out.closeEntry();
			in.close();
		} catch (IOException e) {
			Log.e(TAG, "fileToZip Exception: " + e.toString());
			return false;
		} finally {
			if (out != null) {
				out.closeEntry();
			}

			if (in != null) {
				in.close();
			}
		}
		return true;
	}

	private static boolean dirToZip(String baseDirPath, File dir,
			ZipOutputStream out) throws IOException {
		if (!dir.isDirectory()) {
			return false;
		}

		File[] files = dir.listFiles();
		if (files.length == 0) {
			ZipEntry entry = new ZipEntry(getEntryName(baseDirPath, dir));

			try {
				out.putNextEntry(entry);
				out.closeEntry();
			} catch (IOException e) {
				Log.e(TAG,"dirToZip Exception: " + e.toString());
			}
		}

		for (int i = 0; i < files.length; i++) {
			if (files[i].isFile()) {
				fileToZip(baseDirPath, files[i], out);
			} else {
				dirToZip(baseDirPath, files[i], out);
			}
		}
		return true;
	}

	private static String getEntryName(String baseDirPath, File file) {
		if (!baseDirPath.endsWith(File.separator)) {
			baseDirPath = baseDirPath + File.separator;
		}

		String filePath = file.getAbsolutePath();
		if (file.isDirectory()) {
			filePath = filePath + "/";
		}

		int index = filePath.indexOf(baseDirPath);
		return filePath.substring(index + baseDirPath.length());
	}
}

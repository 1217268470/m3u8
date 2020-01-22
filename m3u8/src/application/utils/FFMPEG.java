package application.utils;

import java.io.File;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FFMPEG {

	/**
	 * @param command
	 * @throws Exception
	 */
	private static boolean process(List<String> command) {
		try {
			if (null == command || command.size() == 0) {
				return false;
			}
			Process videoProcess = new ProcessBuilder(command).redirectErrorStream(true).start();
			Runnable target = new Runnable() {

				@Override
				public void run() {
					try {
						while (this != null) {
							int _ch;
							_ch = videoProcess.getInputStream().read();
							if (_ch == -1) {
								break;
							} else {
								System.out.print((char) _ch);
							}
						}
					} catch (IOException e) {
						e.printStackTrace();
					}

				}
			};
			Runnable target2 = new Runnable() {

				@Override
				public void run() {
					try {
						while (this != null) {
							int _ch;
							_ch = videoProcess.getErrorStream().read();
							if (_ch == -1) {
								break;
							} else {
								System.err.print((char) _ch);
							}
						}
					} catch (IOException e) {
						e.printStackTrace();
					}

				}
			};
			new Thread(target).start();
			new Thread(target2).start();
			int exitcode = videoProcess.waitFor();
			if (exitcode == 1) {
				return false;
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	// ffmpeg -i https://www2.800-cdn.com/20200117/oEPSuMue/index.m3u8
	// C:\Users\kyh\Desktop\m3u8\name.mp4

	private static List<String> getFfmpegCommand(String tsfilepath, String out) {
		List<String> command = new ArrayList<>();
		// ffmpeg -f concat -safe 0 -i filelist.txt -c copy output.mp4
		command.add("ffmpeg");
		command.add("-f");
		command.add("concat");
		command.add("-safe");
		command.add("0");
		command.add("-i");
		command.add(tsfilepath);
		command.add("-c");
		command.add("copy");
		command.add(out);
		return command;
	}

	
	
	@Deprecated
	private static String tsfile(String dir) {

		File file = new File(dir);
		File[] list = file.listFiles(new FilenameFilter() {

			@Override
			public boolean accept(File dir, String name) {
				if (name.endsWith(".ts")) {
					return true;
				} else {
//					System.out.println(name);
					return false;
				}
			}
		});
		ArrayList<String> arrayList = new ArrayList<String>();
		for (int i = 0, len = list.length; i < len; i++) {
			arrayList.add(list[i].getAbsolutePath());
		}
		Collections.sort(arrayList);
		StringBuilder sb = new StringBuilder();
		for (int j = 0, jlen = arrayList.size(); j < jlen; j++) {
			sb.append("file ");
			sb.append("'");
			sb.append(arrayList.get(j));
			sb.append("'");
			sb.append("\r\n");
		}
//		System.out.println("tsFile:\n");
//		System.out.println(sb.toString());
		String filePath = dir + File.separator + "tsFile.txt";

		File tsfile = new File(filePath);
		FileWriter fileWriter = null;
		try {
			fileWriter = new FileWriter(tsfile);
			fileWriter.write(sb.toString());
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				fileWriter.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return filePath;
	}

	public static void merge(String dir) {
//		String tsfilepath = tsfile(dir);
		List<String> command = getFfmpegCommand(dir+File.separator+"tsFile.txt", dir + File.separator + "out.mp4");
		process(command);
	}

	public static void main(String[] args) {
//		merge("C:\\Users\\kyh\\Desktop\\m3u8\\sg02");
		tsfile("C:\\Users\\kyh\\Desktop\\m3u8\\xxx-");
	}
}
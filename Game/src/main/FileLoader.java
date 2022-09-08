package main;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import java.io.*;

import special.Image;

public class FileLoader {

	public ArrayList<String> files = new ArrayList<String>();
	private ArrayList<String> cutDirs = new ArrayList<String>();
	public HashMap<String, Image> images = new HashMap<String, Image>();

	public boolean isJar;

	public FileLoader() {

		String resPath = null;

		try {

			resPath = getClass().getProtectionDomain().getCodeSource().getLocation().toURI().getPath();
			resPath = new File(resPath).getParentFile().getAbsolutePath();
			resPath += "/res";

		} catch (URISyntaxException e) {

			e.printStackTrace();

		}

		String jarPath = null;
		String mainPath = null;
		ArrayList<String> paths = new ArrayList<String>();

		try {

			jarPath = getClass().getProtectionDomain().getCodeSource().getLocation().toURI().getPath();
			mainPath = new File(jarPath).getParentFile().getAbsolutePath();

			try {

				Process process = Runtime.getRuntime().exec("jar tf " + jarPath);
				BufferedReader result = new BufferedReader(new InputStreamReader(process.getInputStream()));

				// https://stackoverflow.com/questions/25370256/java-bufferedreader-to-string-array
				String line = null;

				while ((line = result.readLine()) != null) {

					paths.add(line);

				}

				result.close();

			} catch (IOException e) {

				e.printStackTrace();

			}

		} catch (URISyntaxException e1) {

			e1.printStackTrace();

		}

		File res = new File(resPath);

		ArrayList<File> directoryFiles = new ArrayList<File>();

		directoryFiles.add(res);

		// Search through directories
		while (!directoryFiles.isEmpty()) {

			for (int i = 0; i < directoryFiles.size(); i++) {

				File[] subFiles = directoryFiles.get(i).listFiles();
				isJar = false;

				// jar file
				if (subFiles == null) {

					subFiles = new File[paths.size()];
					isJar = true;

					String superPath = directoryFiles.get(i).getAbsolutePath().replace(mainPath + "/", "");

					String[] baseFolders = {"src", "res"};

					if (Arrays.asList(baseFolders).contains(superPath.substring(0, 3))) {

						superPath = superPath.substring(3);

					}

					String[] pathArray = paths.toArray(new String[paths.size()]);

					String[] subPaths = startsWith(pathArray, superPath);

					for (int j = 0; j < subPaths.length; j++) {

						String curPath = "/" + subPaths[j].replace(mainPath, "");

						subFiles[j] = new File(getClass().getResource(curPath).toExternalForm());

					}

				}

				for (int j = 0; j < subFiles.length; j++) {

					File curSubFile = subFiles[j];

					if (curSubFile.isDirectory()) {

						directoryFiles.add(curSubFile);

					} else {

						files.add(curSubFile.getAbsolutePath());

					}

				}

				directoryFiles.remove(i);

			}

		}

		// Add shortened directories
		for (int i = 0; i < files.size(); i++) {

			String curDir = files.get(i);
			int resIndex = curDir.indexOf("res");
			String splitDir = curDir.substring(resIndex + 3);

			if (isJar) {
				
				splitDir = curDir.split(".jar!")[1];

			}
									
			cutDirs.add(splitDir);

		}
		
		// Add images
		for (int i = 0; i < files.size(); i++) {

			String curDir = files.get(i);
			String fileType = curDir.substring(curDir.lastIndexOf(".") + 1, curDir.length());

			if (fileType.equals("png")) {

				Image img = new Image(cutDirs.get(i));
				images.put(cutDirs.get(i), img);

			} else {

				images.put(cutDirs.get(i), null);

			}

		}
		
	}

	public String[] startsWith(String[] input, String start) {

		ArrayList<String> output = new ArrayList<String>();

		for (int i = 0; i < input.length; i++) {

			if (input[i].startsWith(start) || start.length() == 0) {

				output.add(input[i]);

			}

		}

		return output.toArray(new String[output.size()]);

	}

	public Image getImage(String directory) {

		if (images.keySet().contains(directory)) {

			Image img = images.get(directory);

			return img;

		} else {

			return null;

		}

	}

}

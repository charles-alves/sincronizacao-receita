package br.com.charlesalves.sincronizacaoreceita.util;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class IOUtils {

	public static boolean createParentDir(String path) {
		return createParentDir(new File(path));
	}

	public static boolean createParentDir(File file) {
		File parentFile = file.getParentFile();
		return mkdirs(parentFile);
	}

	public static boolean mkdirs(String path) {
		File directory = new File(path);
		return mkdirs(directory);
	}

	private static boolean mkdirs(File directory) {
		if (!directory.exists()) {
			return directory.mkdirs();
		}
		return true;
	}

	public static void copy(String source, String target) throws IOException {
		Path sourcePath = Paths.get(source);
		Path targetPath = Paths.get(target);
		copy(sourcePath, targetPath);
	}

	public static void copy(Path source, Path target) throws IOException {
		createParentDir(target.toFile());
		Files.copy(source, target, StandardCopyOption.REPLACE_EXISTING);
	}
}

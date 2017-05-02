package ru.igrey.dev.dao;

import java.io.File;


/**
 * Класс служит для получения данных о директории sql-скриптов/исполняющего jar-файла
 */
public class DirectoryInfo {
    public static final String BASE_DIRECTORY = DirectoryInfo.class.getProtectionDomain().getCodeSource().getLocation().getPath();
    /**
     * Метод возвращает абсолютный путь исполняющего jar-файла
     * @return String Возвращает абсолютный путь исполняющего jar-файла
     */
    public static String getDirectoryTargetScripts() {
        File jarDirectory = new File(BASE_DIRECTORY);
        return jarDirectory.getParentFile().getAbsolutePath() + "/";
    }
}

package com.kasania.core;

import com.kasania.core.game.ScoreItem;
import com.kasania.core.game.settings.GameSettings;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

public class FileManager {

    private static String wordFilePath = "./words.txt";
    private static String scoreFilePath = "./score";
    private static String settingFilePath = "./setting";

    private static FileManager instance;

    private static ArrayList<String> wordList;
    private static ArrayList<String> scoreList;
    private static ArrayList<String> settingList;

    private FileManager(){
        readFiles();
    }

    /**
     *
     * @return Unique instance of FileManager.
     */
    public static FileManager getInstance(){
        if(instance == null){
            instance = new FileManager();
        }
        return instance;
    }

    private void readWordListFromFile(){
        wordList = readFile(wordFilePath);
    }

    private void readScoreListFromFile(){
        scoreList = readFile(scoreFilePath);
    }

    private void readSettingListFromFile(){
        settingList = readFile(settingFilePath);
    }

    public void readFiles(){
        readSettingListFromFile();
        readScoreListFromFile();
        readWordListFromFile();
    }

    public ArrayList<String> getSettingList(){
        return new ArrayList<>(settingList);
    }

    public void saveSettingList(){
        ExecutorService es = Executors.newCachedThreadPool();
        settingList = GameSettings.getInstance().getAllAttribute();

        FutureTask<Boolean> saveTask = new FutureTask<>(() ->   saveFile(settingList,settingFilePath));

        es.execute(saveTask);
        if(saveTask.isDone()){
            es.shutdown();
        }
    }

    /**
     *
     * @return Clone of word list.
     */
    public ArrayList<String> getWordList(){
        return new ArrayList<>(wordList);
    }

    /**
     *
     * @param newWordList ArrayList to replace original.
     */
    public void replaceWordList(ArrayList<String> newWordList){
        wordList = newWordList;
    }

    /**
     * Save word list asynchronously.
     */
    public void saveWordList(){

        ExecutorService es = Executors.newCachedThreadPool();

        FutureTask<Boolean> saveTask = new FutureTask<>(() -> saveFile(wordList,wordFilePath));

        es.execute(saveTask);
        if(saveTask.isDone()){
            es.shutdown();
        }
    }

    /**
     *
     * @return Clone of score list.
     */
    public ArrayList<String> getScoreList(){
        return new ArrayList<>(scoreList);
    }

    /**
     * Add word to original score list.
     * @param score score to add.
     */
    public void addScore(ScoreItem score){
        scoreList.add(score.toString());
    }

    /**
     * Save score list asynchronously.
     */
    public void saveScoreList(){

        ExecutorService es = Executors.newCachedThreadPool();

        FutureTask<Boolean> saveTask = new FutureTask<>(() -> saveFile(scoreList,scoreFilePath));

        es.execute(saveTask);
        if(saveTask.isDone()){
            es.shutdown();
        }

    }

    /**
     *
     * @param path Path to read.
     * @return ArrayList filled with read Data. If File is not exist, will return new ArrayList.
     */
    private ArrayList<String> readFile(String path){

        ArrayList<String> readData = null;
        File file = new File(path);

        try {
            if(Files.exists(file.toPath())){
                readData = new ArrayList<>(Files.readAllLines(file.toPath()));
            }else{
                readData = new ArrayList<>();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return readData;
    }

    /**
     *
     * @param data Data to save.
     * @param path Path to save.
     * @return If file writing is success return true. else return false.
     */
    private boolean saveFile(ArrayList<String> data, String path){

        File file = new File(path);

        StringBuilder sb = new StringBuilder();

        for(String str : data){
            sb.append(str);
            sb.append("\r\n");
        }

        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(file));
            bw.write(sb.toString());
            bw.flush();
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return true;
    }

}

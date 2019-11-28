package com.kasania.core.game.logic;

import com.kasania.core.FileManager;
import com.kasania.core.game.ScoreItem;
import com.kasania.core.game.WordItem;
import com.kasania.core.game.settings.GameSettings;
import com.kasania.graphics.MainFrame;

import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;

public class WordTypeGame {

    private static WordTypeGame instance;

    private int difficulty;

    private long highScore;
    private long currentScore;
    private int scoreOffset;

    private float yPositionLimit = 90;

    private long wordGenDelay;
    private long positionUpdateDelay;
    private long drawDelay;
    private float positionUpdateDistance;

    private int maxLife;
    private int currentLife;

    private int caughtWordCount;
    private int requirementOfLevelUp;

    /**
     * Max, current
     */
    private BiConsumer<Long, Long> updateScore;
    /**
     * Max, current
     */
    private BiConsumer<Integer, Integer> updateLife;
    private Consumer<List> updateWord;
    private Function<Long,String> onGameOver;

    private ArrayList<String> storedWordList;
    private ArrayList<String> storedScoreList;
    private List<WordItem> displayedWords;

    private ScheduledExecutorService executorService;
    private ScheduledFuture<?> wordGenerator;
    private ScheduledFuture<?> positionUpdater;
    private ScheduledFuture<?> wordListPresenter;

    private WordTypeGame(){

    }

    public static WordTypeGame newGame(int baseDifficulty){
        if(instance == null){
            instance = new WordTypeGame();
        }

        instance.storedWordList = FileManager.getInstance().getWordList();
        instance.storedScoreList = FileManager.getInstance().getScoreList();

        instance.initializeGame(baseDifficulty);

        instance.resetScore();

        return instance;
    }

    public WordTypeGame addWordUpdater(Consumer<List> updateWord){
        instance.updateWord = updateWord;
        return instance;
    }

    public WordTypeGame addScoreUpdater(BiConsumer<Long, Long> updateScore){
        instance.updateScore = updateScore;
        return instance;
    }

    public WordTypeGame addLifeUpdater(BiConsumer<Integer,Integer> updateLife){
        instance.updateLife = updateLife;
        return instance;
    }
    public WordTypeGame addGameOver(Function<Long,String> onGameOver){
        instance.onGameOver = onGameOver;
        return instance;
    }

    private void initializeGame(int baseDifficulty){

        this.difficulty = baseDifficulty;

        maxLife = (int) (Math.sqrt(difficulty)*2 + 1);
        currentLife = maxLife;
        caughtWordCount = 0;

        scoreOffset = (difficulty+1)*10;

        //thread-safe list
        displayedWords = Collections.synchronizedList(new LinkedList<>());

        calculateDifficulty();

        executorService = Executors.newScheduledThreadPool(3);
    }

    private void calculateDifficulty(){
        long secondToMicro = 1_000_000;
        long framePerSecond = GameSettings.getInstance().getSettingValue(GameSettings.Items.FRAME_RATE);

        wordGenDelay = (long)(2.0 * secondToMicro / Math.sqrt(difficulty + 1));
        positionUpdateDistance = (float) (Math.sqrt(difficulty + 1) * 7) / framePerSecond;
        requirementOfLevelUp = (int) (Math.sqrt(difficulty + 1) * 10);

        positionUpdateDelay = (secondToMicro / framePerSecond);
        drawDelay = (secondToMicro / framePerSecond);

    }

    /**
     *
     * @param difficulty difficulty level
     * @return highest score , if there is no record, return 0.
     */
    private long getHighScore(int difficulty){

        long highestScore = -maxLife * scoreOffset;

        int levelCount = 0;

        for (String s : storedScoreList) {

            String[] strings = s.split(":");
            ScoreItem item = new ScoreItem(strings[0],Integer.parseInt(strings[1]),Long.parseLong(strings[2]));
            if(item.difficulty == difficulty){
                levelCount += 1;
                if(highestScore < item.score) {
                    highestScore = item.score;
                }
            }
        }

        if(levelCount == 0){
            highestScore = 0;
        }
        return highestScore;
    }

    private void resetScore(){
        currentScore = 0;
        highScore = getHighScore(difficulty);
        if(updateScore!=null)
            updateScore.accept(highScore,currentScore);

    }

    public void startGame(){
        wordGenerator = executorService.scheduleAtFixedRate(this::generateWord, 0,wordGenDelay,TimeUnit.MICROSECONDS);
        positionUpdater = executorService.scheduleAtFixedRate(this::updatePosition, 0,positionUpdateDelay,TimeUnit.MICROSECONDS);
        wordListPresenter = executorService.scheduleAtFixedRate(this::drawWord,0,drawDelay,TimeUnit.MICROSECONDS);
        updateLife.accept(maxLife,currentLife);

    }

    public void stopGame(){
        wordGenerator.cancel(true);
        positionUpdater.cancel(true);
        wordListPresenter.cancel(true);

        executorService.shutdown();
    }


    private void decreaseLife(){
        currentLife -= 1;
        updateLife.accept(maxLife,currentLife);
    }

    private void updatePosition(){
        displayedWords.forEach(this::increasePosition);
        removeCaughtWord();
        removeMissedWord();
        checkAlive();
        checkLevelUp();
    }

    private void removeCaughtWord() {
        displayedWords.removeIf(WordItem::isCaught);
    }

    private void removeMissedWord(){
        displayedWords.removeIf(word -> {
            if(word.getYPosition() > yPositionLimit){
                currentScore -= scoreOffset;
                decreaseLife();
                return true;
            }else{
                return false;
            }

        });

        updateScore.accept(highScore,currentScore);
    }

    private void checkAlive(){

        if(currentLife<=0){
            stopGame();
            String name = onGameOver.apply(currentScore);
            if(name != null){
                if(name.equals("")){
                    name = "Anonymous";
                }
                FileManager.getInstance().addScore(new ScoreItem(name,difficulty,currentScore));
            }
        }
    }

    private void checkLevelUp(){

        if(requirementOfLevelUp < caughtWordCount){
            stopGame();

            initializeGame(difficulty + 1);

            MainFrame.requestConfirmDialog("NextLevel : " + difficulty, "Level Up");
            startGame();
        }

    }

    private void increasePosition(WordItem item){
        item.increaseYPosition(positionUpdateDistance);
    }

    private void generateWord(){
        Random random = new Random();
        WordItem item = new WordItem(random.nextInt(50)+5,0, storedWordList.get(random.nextInt(storedWordList.size())));
        displayedWords.add(item);
    }

    private void drawWord(){
        updateWord.accept(displayedWords);
    }

    public void checkAnswer(String answer){
        int firstMatchWordIndex = findFirstMatch(answer);

        if(firstMatchWordIndex != -1){
            displayedWords.get(firstMatchWordIndex).setCaught();
            caughtWordCount += 1;
            currentScore += scoreOffset;
            updateScore.accept(highScore,currentScore);
        }
    }

    private int findFirstMatch(String input){
        int firstMatchWordIndex = -1;
        for(int i = 0; i<displayedWords.size(); ++i){
            if(displayedWords.get(i).getWord().contentEquals(input)){
                firstMatchWordIndex = i;
                break;
            }
        }
        return firstMatchWordIndex;
    }

}

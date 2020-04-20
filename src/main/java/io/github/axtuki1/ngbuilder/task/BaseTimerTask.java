package io.github.axtuki1.ngbuilder.task;

import io.github.axtuki1.ngbuilder.GameStatus;
import io.github.axtuki1.ngbuilder.NGBuilder;
import org.bukkit.scheduler.BukkitTask;

public class BaseTimerTask extends BaseTask {

    // ExpTimerを見て書いてたけどあまりうまく行かなかった....
    // しょーがないからデクリメントする方法でとりあえず。
    // 現在時刻から算出する方法もやってはみたい

    private static final long OFFSET = 1150;
    private static final long DEC_OFFSET = 0;

    private NGBuilder plugin;

    private BukkitTask task;

    private long secondsRest, secondsMax, secondsBefore;

    private long tickBase;

    private int nextCount = 0;

    private boolean isPaused = false;

    BaseTimerTask(NGBuilder pl, long sec ){
        super(pl);
        this.plugin = pl;
        secondsRest = sec + (int)DEC_OFFSET;
        secondsMax = sec;
        secondsBefore = sec;
        tickBase = System.currentTimeMillis() + secondsRest * 1000 + OFFSET;
        NGBuilder.setTask(this);
    }

    public void run() {
        beforeView();
        updateTime();
        updateView();
        if( !isPaused ){
            if ( secondsRest <= 0 ) {
                EndExec();
                stop();
                return;
            }
            exec();
        }
    }

    /**
     * 表示の更新に使うメソッド。
     * execと違ってタイマーが可動している間は呼ばれる。
     * 秒数の更新前に呼ばれる。
     */
    public void beforeView() {
//        JinroScoreboard.getScoreboard().resetScores(
//                Utility.getColor(getSeconds(), getSecondsMax()) + "残り時間: "+ getSeconds() +"秒"
//        );
//        JinroScoreboard.getScoreboard().resetScores(
//                Utility.getColor(secondsBefore, getSecondsMax()) + "残り時間: "+ secondsBefore +"秒"
//        );
    }

    /**
     * 表示の更新に使うメソッド。
     * execと違ってタイマーが可動している間は呼ばれる。
     * 秒数の更新後に呼ばれる。
     */
    public void updateView() {
//        JinroScoreboard.getInfoObj().getScore(
//                Utility.getColor(getSeconds(), getSecondsMax()) + "残り時間: " + getSeconds() + "秒"
//        ).setScore(0);
    }

    public void updateTime() {
        if( nextCount == 10 ){
            if ( !isPaused ) {
                secondsRest--;
                incrementElapse();
            }
            execSecond();
            nextCount = 0;
        } else {
            nextCount++;
        }
    }

    /**
     * メッセージ系に使用するメソッド。
     * Pause中に呼ばれることはない。
     * これはTimerEndExecが呼ばれるときは呼ばれない。
     */
    public void exec() { }

    /**
     * タイマーが終了した(0になった)ときに呼ばれるメソッド。
     */
    public void EndExec(){

    }

    public void execSecond(){}

    public boolean isPaused() {
        return isPaused;
    }

    public void setSecondsRest(long secondsRest) {
//        JinroScoreboard.getScoreboard().resetScores(
//                Utility.getColor(getSeconds(), getSecondsMax()) + "残り時間: "+ getSeconds() +"秒"
//        );
//        JinroScoreboard.getScoreboard().resetScores(
//                Utility.getColor(secondsBefore, getSecondsMax()) + "残り時間: "+ secondsBefore +"秒"
//        );
        secondsBefore = this.secondsRest;
        this.secondsRest = secondsRest;
//        JinroScoreboard.getInfoObj().getScore(
//                Utility.getColor(getSeconds(), getSecondsMax()) + "残り時間: " + getSeconds() + "秒"
//        ).setScore(0);
        if( secondsMax <= secondsRest ){
            secondsMax = secondsRest;
        }
    }

    public long getSeconds() {
        return secondsRest;
    }

    public long getSecondsMax() {
        return secondsMax;
    }

    /**
     * 現在のステータスを返す
     * @return ステータス
     */
    public Status getStatus() {

        if ( isPaused ) {
            return Status.PAUSED;
        }
        if  ( secondsRest > 0 ) {
            return Status.RUNNING;
        }
        return Status.READY;
    }

    /**
     * タイマーを一時停止する
     */
    public void pause(){
        isPaused = true;
    }

    /**
     * 一時停止にあるタイマーを再開する
     */
    public void resume() {
        if ( isPaused ) {
            isPaused = false;
            long current = System.currentTimeMillis();
            tickBase = current + secondsRest * 1000 + OFFSET;
            GameStatus.setStatus(GameStatus.Playing);
        }
    }

    /**
     * タイマーを開始する。
     */
    public void start(){
        if( task == null ){
            task = runTaskTimer(plugin, 2L, 2L);
        }
//        JinroScoreboard.getScoreboard().resetScores(
//                Utility.getColor(0, getSecondsMax()) + "残り時間: 0秒"
//        );
//        JinroScoreboard.getInfoObj().getScore(
//                Utility.getColor(getSeconds(), getSecondsMax()) + "残り時間: " + getSeconds() + "秒"
//        ).setScore(0);
    }

    /**
     * タイマーを強制終了する。
     * このメソッドで終了した場合、再開はできない。
     */
    public void stop(){
        if( task != null ){
            cancel();
            task = null;
        }
    }

    /**
     * 終了時処理を実行し、タイマーを終了する。
     * このメソッドで終了した場合、再開はできない。
     */
    public void end(){
        beforeView();
        secondsRest = 1;
        updateTime();
        updateView();
        if( !isPaused ){
            if ( secondsRest <= 0 ) {
                EndExec();
                stop();
                return;
            }
            exec();
        }
    }
}

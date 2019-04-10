package io.github.axtuki1.ngbuilder.task;

import io.github.axtuki1.ngbuilder.GameStatus;
import io.github.axtuki1.ngbuilder.NGBuilder;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

public class BaseTask extends BukkitRunnable {

    private NGBuilder plugin;

    private BukkitTask task;

    private long secondsElapsed;

    private boolean isPaused = false;

    public enum Status {
        READY,
        RUNNING,
        PAUSED,;
    }

    BaseTask(NGBuilder pl){
        this.plugin = pl;
    }

    public void run() {
        updateView();
        if( !isPaused ){
            exec();
        }
    }

    /**
     * 表示の更新に使うメソッド。
     * execと違ってタイマーが可動している間は呼ばれる。
     */
    public void updateView() {

    }

    /**
     * メッセージ系に使用するメソッド。
     * Pause中に呼ばれることはない。
     */
    public void exec() { }

    /**
     * タスク終了時に呼ばれる。
     */
    public void EndExec() {
    }

    public void incrementElapse(){
        secondsElapsed++;
    }

    public void resetElapse(){
        secondsElapsed = 0;
    }

    public long getSecondsElapsed() {
        return secondsElapsed;
    }

    public boolean isPaused() {
        return isPaused;
    }

    /**
     * 現在のステータスを返す
     * @return ステータス
     */
    public Status getStatus() {

        if ( isPaused ) {
            return Status.PAUSED;
        }
        if  ( task != null ) {
            return Status.RUNNING;
        }
        return Status.READY;
    }

    /**
     * タスクを一時停止する
     */
    public void pause(){
        isPaused = true;
    }

    /**
     * 一時停止にあるタスクを再開する
     */
    public void resume() {
        if ( isPaused ) {
            isPaused = false;
            GameStatus.setStatus(GameStatus.Playing);
        }
    }

    /**
     * タスクを開始する。
     */
    public void start(){
        if( task == null ){
            task = runTaskTimer(plugin, 20, 20);
        }
    }

    /**
     * タスクを開始する。
     */
    public void start(int periodTick){
        if( task == null ){
            task = runTaskTimer(plugin, 20, periodTick);
        }
    }

    /**
     * タスクを開始する。
     */
    public void start(int delaytick, int periodTick){
        if( task == null ){
            task = runTaskTimer(plugin, delaytick, periodTick);
        }
    }

    /**
     * タスクを強制終了する。
     * このメソッドで終了した場合、再開はできない。
     */
    public void stop(){
        if( task != null ){
            cancel();
            task = null;
        }
    }

    /**
     * タスクを終了する。
     * このメソッドで終了した場合、再開はできない。
     */
    public void end() {
        stop();
        EndExec();
    }

    /**
     * チャットイベント時に呼ばれます。
     */
    public void onChat(AsyncPlayerChatEvent e){

    }

    public NGBuilder getPlugin() {
        return plugin;
    }
}

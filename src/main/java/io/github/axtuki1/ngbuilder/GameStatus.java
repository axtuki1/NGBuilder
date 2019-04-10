package io.github.axtuki1.ngbuilder;

public enum GameStatus {
    Ready, Playing, End;

    private static GameStatus s = GameStatus.Ready;
    private static int roundCount = 0;

    public static GameStatus getStatus(){
        return s;
    }

    public static void setStatus(GameStatus status) {
        s = status;
    }

    public static int getRoundCount() {
        return roundCount;
    }

    public static void setRoundCount(int roundCount) {
        GameStatus.roundCount = roundCount;
    }
}



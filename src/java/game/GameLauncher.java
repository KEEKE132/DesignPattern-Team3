package game;

import game.gameconfig.LevelConfig;

import javax.swing.*;
import java.io.IOException;

//애플리케이션의 진입점
public class GameLauncher {
    private static UIPanel uiPanel;

    public static void main(String[] args) {
        JFrame window = new JFrame();
        window.setTitle("Pacman");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel gameWindow = new JPanel();

        LevelConfig levelConfig = LevelConfig.builder().build(); // level 기본 설정

        LevelConfig level2Config = LevelConfig.builder()
                .levelMap("level/level2.csv")
                .speeds(4, 4) //팩맨과 유령의 속도는 8의 약수(1, 2, 4, 8)여야 한다.
                .seconds(5, 25, 4, 3)
                .scores(15, 150, 750)
                .build();

        //"게임 영역" 생성
        try {
            //GameplayPanel은 Runnable이므로, 이 시점부터 백그라운드 스레드에서 게임 루프가 돌기 시작합니다.
            gameWindow.add(new GameplayPanel(448,496, level2Config)); // <-- 변경
        } catch (IOException e) {
            e.printStackTrace();
        }

        //UI 생성 (점수를 표시하기 위함)
        uiPanel = new UIPanel(256,496, level2Config); // <-- 변경
        gameWindow.add(uiPanel);

        window.setContentPane(gameWindow);
        window.setResizable(false);
        window.pack();
        window.setLocationRelativeTo(null);
        window.setVisible(true);
    }

    public static UIPanel getUIPanel() {
        return uiPanel;
    }
}
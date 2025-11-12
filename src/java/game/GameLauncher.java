package game;

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

        //"게임 영역" 생성
        try {
            //GameplayPanel은 Runnable이므로, 이 시점부터 백그라운드 스레드에서 게임 루프가 돌기 시작합니다.
            gameWindow.add(new GameplayPanel(448,496));
        } catch (IOException e) {
            e.printStackTrace();
        }

        //UI 생성 (점수를 표시하기 위함)
        uiPanel = new UIPanel(256,496);
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
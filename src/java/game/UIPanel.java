package game;

import game.entities.PacGum;
import game.entities.SuperPacGum;
import game.entities.ghosts.Ghost;
import game.entities.items.Item;
import game.gameconfig.LevelManager;
import game.ghostStates.EatenMode;
import game.ghostStates.FrightenedMode;

import javax.swing.*;
import java.awt.*;

//사용자 인터페이스 패널
public class UIPanel extends JPanel implements Observer {
    public static int width;
    public static int height;
    private final LevelManager levelManager;

    // private int score = 0; // <-- 삭제! (이제 점수는 LevelManager가 관리)
    private final JLabel scoreLabel;

    // 생성자가 LevelManager를 주입받도록 변경
    public UIPanel(int width, int height, LevelManager levelManager) {
        UIPanel.width = width;
        UIPanel.height = height;
        this.levelManager = levelManager;

        setPreferredSize(new Dimension(width, height)); //자신의 크기를 설정
        this.setBackground(Color.black);

        // 초기 점수 표시
        scoreLabel = new JLabel("Score: 0");
        scoreLabel.setFont(scoreLabel.getFont().deriveFont(20.0F));
        scoreLabel.setForeground(Color.white);
        this.add(scoreLabel, BorderLayout.WEST); //검은색 패널(UIPanel) 위에 흰색 텍스트 라벨(scoreLabel)을 추가
    }

    // 점수 갱신 메서드 (이름 변경: updateScore -> refreshScore)
    public void refreshScore() {
        // LevelManager에서 최신 점수를 가져와서 표시
        scoreLabel.setText("Score: " + levelManager.getCurrentScore());
    }

    //인터페이스(UI)는 팩맨이 팩껌, 슈퍼팩껌, 또는 유령과 접촉했을 때 알림을 받으며, 그에 따라 표시되는 점수를 업데이트
    //팩맨이 팩껌을 먹었다는 알림을 받으면 호출 -> 설정값만큼 추가
    @Override
    public void updatePacGumEaten(PacGum pg) {
        refreshScore();
    }

    //팩맨이 유령과 충돌했다는 알림을 받으면 호출 -> 설정값만큼 추가
    @Override
    public void updateSuperPacGumEaten(SuperPacGum spg) {
        refreshScore();
    }

    //팩맨이 팩껌을 먹었다는 알림을 받으면 호출 -> EatenMode이면, 설정값만큼 추가
    @Override
    public void updateGhostCollision(Ghost gh) {
        // 팩맨이 유령과 접촉한 경우, 유령이 "EatenMode(먹힌)" 모드일 때만 점수를 업데이트합니다.
        if (gh.getState() instanceof EatenMode) refreshScore();
    }

    @Override
    public void updateItemEaten(Item item) {refreshScore();}
}

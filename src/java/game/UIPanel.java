package game;

import game.entities.PacGum;
import game.entities.SuperPacGum;
import game.entities.ghosts.Ghost;
import game.gameconfig.LevelConfig;
import game.ghostStates.FrightenedMode;

import javax.swing.*;
import java.awt.*;

//사용자 인터페이스 패널
public class UIPanel extends JPanel implements Observer {
    public static int width;
    public static int height;
    private LevelConfig levelConfig; // <-- 추가

    private int score = 0;
    private JLabel scoreLabel;

    // 생성자가 LevelConfig를 주입받도록 변경
    public UIPanel(int width, int height, LevelConfig levelConfig) {
        this.width = width;
        this.height = height;
        this.levelConfig = levelConfig; // <-- 추가
        setPreferredSize(new Dimension(width, height)); //자신의 크기를 설정
        this.setBackground(Color.black);
        scoreLabel = new JLabel("Score: " + score);
        scoreLabel.setFont(scoreLabel.getFont().deriveFont(20.0F));
        scoreLabel.setForeground(Color.white);
        this.add(scoreLabel, BorderLayout.WEST); //검은색 패널(UIPanel) 위에 흰색 텍스트 라벨(scoreLabel)을 추가
    }

    //점수를 incrScore 만큼 증가시키고,
    //scoreLabel의 텍스트를 새로운 점수로 갱신
    public void updateScore(int incrScore) {
        this.score += incrScore;
        this.scoreLabel.setText("Score: " + score);
    }

    public int getScore() {
        return score;
    }

    //인터페이스(UI)는 팩맨이 팩껌, 슈퍼팩껌, 또는 유령과 접촉했을 때 알림을 받으며, 그에 따라 표시되는 점수를 업데이트
    //팩맨이 팩껌을 먹었다는 알림을 받으면 호출 -> 10점 추가
    @Override
    public void updatePacGumEaten(PacGum pg) {
        updateScore(levelConfig.getScorePacGum()); // 10 -> config 값으로 변경
    }

    //팩맨이 유령과 충돌했다는 알림을 받으면 호출 -> 100점 추가
    @Override
    public void updateSuperPacGumEaten(SuperPacGum spg) {
        updateScore(levelConfig.getScoreSuperPacGum()); // 100 -> config 값으로 변경
    }

    //팩맨이 팩껌을 먹었다는 알림을 받으면 호출 -> frightened이면, 500점 추가
    @Override
    public void updateGhostCollision(Ghost gh) {
        if (gh.getState() instanceof FrightenedMode) { //팩맨이 유령과 접촉한 경우, 유령이 "frightened(겁먹은)" 모드일 때만 점수를 업데이트합니다.
            updateScore(levelConfig.getScoreGhostEaten()); // 500 -> config 값으로 변경
        }
    }
}

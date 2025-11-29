package game;

import game.gameconfig.ScoreEvent;
import game.gameconfig.ScoreManager;
import game.gameconfig.ScoreObserver;

import javax.swing.*;
import java.awt.*;

//사용자 인터페이스 패널
public class UIPanel extends JPanel implements ScoreObserver {
    public static int width;
    public static int height;
    private final ScoreManager scoreManager;

    private final JLabel scoreLabel;
    private final JLabel messageLabel;
    private Timer messageTimer;

    // 생성자가 LevelManager를 주입받도록 변경
    public UIPanel(int width, int height, ScoreManager scoreManager) {
        UIPanel.width = width;
        UIPanel.height = height;
        this.scoreManager = scoreManager;

        setPreferredSize(new Dimension(width, height)); //자신의 크기를 설정
        this.setBackground(Color.black);

        this.setLayout(null);

        // 점수 라벨 설정
        scoreLabel = new JLabel("Score: 0");
        scoreLabel.setFont(new Font("Arial", Font.BOLD, 20));
        scoreLabel.setForeground(Color.white);
        scoreLabel.setHorizontalAlignment(SwingConstants.CENTER);
        scoreLabel.setBounds(0, 20, width, 30);
        this.add(scoreLabel, BorderLayout.WEST); //검은색 패널(UIPanel) 위에 흰색 텍스트 라벨(scoreLabel)을 추가

        // 메시지 라벨 설정
        messageLabel = new JLabel("");
        messageLabel.setFont(new Font("Arial", Font.BOLD, 18));
        messageLabel.setForeground(Color.YELLOW);
        messageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        messageLabel.setBounds(0, 100, width, 30);
        this.add(messageLabel);

        // 옵저버 등록
        scoreManager.addObserver(this);

        // 타이머 초기화 (2초 뒤 꺼짐)
        messageTimer = new Timer(2000, e -> {
            messageLabel.setText("");
        });
        messageTimer.setRepeats(false);
    }

    // 점수 갱신 메서드 (이름 변경: updateScore -> refreshScore)
    public void refreshScore() {
        // ScoreManager에서 최신 점수를 가져와서 표시
        scoreLabel.setText("Score: " + scoreManager.getCurrentScore());
    }

    @Override
    public void onScoreChanged(int totalScore,  ScoreEvent event) {
        // 점수 갱신
        scoreLabel.setText("Score: " + totalScore);

        // 메시지 처리
        String msg = event.message();
        if (msg != null && !msg.isEmpty()) {
            showMessage(msg);
        }
    }

    private void showMessage(String msg) {
        // 타이머가 돌고 있다면 리셋 (유령 콤보 때 중요!)
        if (messageTimer.isRunning()) {
            messageTimer.stop();
        }

        messageLabel.setText(msg);

        // 메시지 종류별 색상/시간 미세 조정 (선택사항)
        if (msg.contains("SPEED")) {
            messageLabel.setForeground(Color.CYAN);
            messageTimer.setInitialDelay(2000);
        } else if (msg.contains("MONSTER") || msg.contains("COMBO")) {
            messageLabel.setForeground(Color.ORANGE);
            messageTimer.setInitialDelay(1000); // 콤보는 짧게
        } else {
            messageLabel.setForeground(Color.YELLOW);
            messageTimer.setInitialDelay(1500);
        }

        messageTimer.start();
        repaint();
    }
}

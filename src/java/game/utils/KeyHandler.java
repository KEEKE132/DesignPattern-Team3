package game.utils;

import game.GameplayPanel;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.List;
import java.util.ArrayList;

//입력(input)을 관리하기 위한 클래스
public class KeyHandler implements KeyListener {

    public static List<Key> keys = new ArrayList<>();

    public static class Key {
        public boolean isPressed = false;       // 계속 눌림 - "팩맨 이동용"
        private boolean isDownOnce = false;     // 한 번 누름 - "메뉴/기능용"

        public Key() {
            keys.add(this);
        }

        //k_left 같은 Key 객체의 isPressed (눌렸는지) 상태를 직접 변경
        public void toggle(boolean pressed) {
            // 키가 눌린 그 순간(찰나)을 포착 (false -> true 될 때만)
            if (pressed && !isPressed) isDownOnce = true;
            // 현재 상태 업데이트
            isPressed = pressed;
        }

        // 한 번 누름 체크
        public boolean isPressedOnce() {
            if (isDownOnce) {
                isDownOnce = false;
                return true;
            }
            return false;
        }

        // 데이터 초기화 메서드
        public void reset() {
            this.isPressed = false;
            this.isDownOnce = false;
        }
    }

    // 이동 키
    public Key k_up = new Key();
    public Key k_down = new Key();
    public Key k_left = new Key();
    public Key k_right = new Key();

    // 기능 키
    public Key k_enter = new Key();
    public Key k_escape = new Key();
    public Key k_p = new Key();

    //KeyHandler 객체(자기 자신, this)를 GameplayPanel의 "키보드 리스너"로 등록
    public KeyHandler(GameplayPanel game) {
        game.addKeyListener(this);
    }

    /**
     * 모든 키의 상태를 강제로 '안 눌림' 상태로 초기화합니다.
     * 상태(State)가 바뀔 때 호출하여 잘못된 입력 이월을 방지합니다.
     */
    public void reset() {
        for (Key k : keys) {
            k.reset();
        }
    }

    //KeyEvent: 어떤 키(왼쪽, Q, Z...)가 눌렸는지에 대한 '원시 이벤트 정보'
    //pressed: keyPressed가 호출했으면 true, keyReleased가 호출했으면 false
    public void toggle(KeyEvent e, boolean pressed) {
        // 방향키 (화살표 & WASD 지원)
        if (e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_A) {
            k_left.toggle(pressed);
        }
        if (e.getKeyCode() == KeyEvent.VK_RIGHT || e.getKeyCode() == KeyEvent.VK_D) {
            k_right.toggle(pressed);
        }
        if (e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_W) {
            k_up.toggle(pressed);
        }
        if (e.getKeyCode() == KeyEvent.VK_DOWN || e.getKeyCode() == KeyEvent.VK_S) {
            k_down.toggle(pressed);
        }

        // 기능 키에 대한 이벤트 추가
        if (e.getKeyCode() == KeyEvent.VK_ENTER) k_enter.toggle(pressed);
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) k_escape.toggle(pressed);
        if (e.getKeyCode() == KeyEvent.VK_P) k_p.toggle(pressed);
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {
        toggle(e, true);
    }

    @Override
    public void keyReleased(KeyEvent e) {
        toggle(e, false);
    }
}

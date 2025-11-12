package game.utils;

import game.GameplayPanel;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.List;
import java.util.ArrayList;

//입력(input)을 관리하기 위한 클래스
public class KeyHandler implements KeyListener {

    public static List<Key> keys = new ArrayList<>();

    public class Key {
        public boolean isPressed;

        public Key() {
            keys.add(this);
        }

        //k_left 같은 Key 객체의 isPressed (눌렸는지) 상태를 직접 변경
        public void toggle(boolean pressed) {
            if (pressed != isPressed) {
                isPressed = pressed;
            }
        }
    }

    //'위', '아래', '왼쪽', '오른쪽' 키의 상태를 각각 저장하기 위한 4개의 Key 객체를 생성
    public Key k_up = new Key();
    public Key k_down = new Key();
    public Key k_left = new Key();
    public Key k_right = new Key();

    //KeyHandler 객체(자기 자신, this)를 GameplayPanel의 "키보드 리스너"로 등록
    public KeyHandler(GameplayPanel game) {
        game.addKeyListener(this);
    }

    //KeyEvent: 어떤 키(왼쪽, Q, Z...)가 눌렸는지에 대한 '원시 이벤트 정보'
    //pressed: keyPressed가 호출했으면 true, keyReleased가 호출했으면 false
    public void toggle(KeyEvent e, boolean pressed) {
        if (e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_Q) {
            k_left.toggle(pressed);
        }
        if (e.getKeyCode() == KeyEvent.VK_RIGHT || e.getKeyCode() == KeyEvent.VK_D) {
            k_right.toggle(pressed);
        }
        if (e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_Z) {
            k_up.toggle(pressed);
        }
        if (e.getKeyCode() == KeyEvent.VK_DOWN || e.getKeyCode() == KeyEvent.VK_S) {
            k_down.toggle(pressed);
        }
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

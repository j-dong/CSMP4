/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

// TODO: MUSIC http://www3.ntu.edu.sg/home/ehchua/programming/java/J8c_PlayingSound.html

package cs.spaceship;

import cs.geom.Vector2D;
import cs.spaceship.entity.Bullet;
import cs.spaceship.entity.DefaultEnemyFactory;
import cs.spaceship.entity.Enemy;
import cs.spaceship.entity.EnemyTimer;
import cs.spaceship.entity.EntityGroup;
import cs.spaceship.entity.Spaceship;
import java.awt.*;
import java.awt.event.*;

import javax.swing.Timer;

/**
 */
public class GamePanel extends ControllableStatePanel implements ActionListener {
    Spaceship player;
    public EntityGroup<Bullet> bullets;
    public EntityGroup<Enemy> enemies;
    Timer stepTimer;
    EnemyTimer<Enemy> enemyTimer;
    public static final int FPS = 60;

    public GamePanel() {
        // bullets
        bullets = new EntityGroup<Bullet>();
        // enemies
        enemies = new EntityGroup<Enemy>();
        enemyTimer = new EnemyTimer<Enemy>(60, enemies, new DefaultEnemyFactory());
        // player
        player = new Spaceship(enemies);
        player.setController(getController());
        // center player on screen
        player.setPosition(new Vector2D(GameFrame.WINDOW_WIDTH / 2, GameFrame.WINDOW_HEIGHT - 100));
        stepTimer = new Timer(1000 / FPS, this);
        stepTimer.start();
    }
    
    // timer event
    public void actionPerformed(ActionEvent e) {
        enemies.update();
        enemyTimer.update();
        bullets.update();
        player.update();
        // repaint, done updating
        repaint();
    }

    @Override
    public void removed() {
        stepTimer.stop();
    }

    @Override
    public void paint(Graphics g) {
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, GameFrame.WINDOW_WIDTH, GameFrame.WINDOW_HEIGHT);
        player.draw(g);
        enemies.draw(g);
        bullets.draw(g);
    }
}

import javafx.application.Application;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javax.swing.*;
import java.awt.*;

/**
 * 实现消息提醒效果
 *
 * @author zjw
 * @createTime 2020/9/13 15:37
 */
public class Main extends Application {

    private TrayIcon trayIcon;
    private static final String MUSIC_PATH = "/music/alert.mp3";
    private static final String ICON_PNG_PATH = "/img/icon.png";

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        // 隐藏窗口及任务栏图标、只显示托盘图标
        stage.initStyle(StageStyle.UTILITY);
        stage.setOpacity(0);

        // 设置托盘图标
        setTrayIcon();

        // 设置图标闪烁
        setTimerTask();

        stage.show();
    }

    private void setTrayIcon() {
        SystemTray systemTray = SystemTray.getSystemTray();

        // 添加退出菜单项、并设置监听事件
        PopupMenu menu = new PopupMenu();
        MenuItem quit = new MenuItem("退出");
        menu.add(quit);
        quit.addActionListener(e -> System.exit(0));

        // 设置托盘图标
        trayIcon = new TrayIcon(getImage0(), "", menu);

        // 让图片自适应、防止图标尺寸不对导致无法显示
        trayIcon.setImageAutoSize(true);

        try {
            systemTray.add(trayIcon);
        } catch (AWTException e) {
            e.printStackTrace();
        }
    }

    private void setTimerTask() {
        // 设置提示音
        MediaPlayer mp = initMusic();
        new Thread(() -> {
            mp.play();
            // 让图标无限闪烁
            while (true) {
                // 交替展示空图标以及有图图标
                sleep(310);
                trayIcon.setImage(getImage1());
                sleep(290);
                trayIcon.setImage(getImage0());
            }
        }).start();
    }

    private MediaPlayer initMusic() {
        return new MediaPlayer(new Media(getClass().getResource(MUSIC_PATH).toString()));
    }

    private Image getImage0() {
        return Toolkit.getDefaultToolkit().getImage(getClass().getResource(ICON_PNG_PATH));
    }

    private Image getImage1() {
        return new ImageIcon("").getImage();
    }

    private void sleep(int millions) {
        try {
            Thread.sleep(millions);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}

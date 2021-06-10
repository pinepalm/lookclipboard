/*
 * @Author: Zhe Chen
 * 
 * @Date: 2021-04-21 14:04:41
 * 
 * @LastEditors: Zhe Chen
 * 
 * @LastEditTime: 2021-06-10 12:05:03
 * 
 * @Description: 应用
 */
package com.buaa.lookclipboard;

import java.util.Objects;
import java.util.Optional;

import com.buaa.lookclipboard.dao.DataAccessCenter;
import com.buaa.lookclipboard.dao.impl.RecordDao;
import com.buaa.lookclipboard.service.impl.ClipboardService;
import com.buaa.lookclipboard.service.impl.ConfigService;
import com.buaa.lookclipboard.service.impl.LogService;
import com.buaa.lookclipboard.service.impl.SettingsService;
import org.apache.commons.io.FileUtils;
import javafx.application.Application;
import javafx.concurrent.Worker.State;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import netscape.javascript.JSObject;

/**
 * 应用
 */
public class App extends Application {
    private static Stage stage;
    private static WebEngine webEngine;

    /**
     * 获取 Stage 对象
     * 
     * @return Stage 对象
     */
    public static Stage getStage() {
        return stage;
    }

    /**
     * 运行JavaScript代码
     * 
     * @param code 代码
     * @return 运行结果
     */
    public static Object runJavaScript(String code) {
        return webEngine.executeScript(code);
    }

    /**
     * App的Main函数
     * 
     * @param args 参数
     * @throws Exception 可能抛出的异常
     */
    public static void main(String[] args) throws Exception {
        launch(args);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        stage = primaryStage;

        WebView webView = new WebView();
        webView.setContextMenuEnabled(false);
        webEngine = webView.getEngine();
        webEngine.getLoadWorker().stateProperty().addListener((ov, oldState, newState) -> {
            if (Objects.equals(newState, State.SUCCEEDED)) {
                try {
                    DataAccessCenter.getInstance().open();
                    RecordDao.getInstance().createIfNotExists();
                } catch (Exception e) {
                    LogService.getInstance().fatal("database access failed", e);
                    System.exit(1);
                }

                JSObject window = (JSObject) webEngine.executeScript("window");
                window.setMember("configService", ConfigService.getInstance());
                window.setMember("clipboardService", ClipboardService.getInstance());
                window.setMember("settingsService", SettingsService.getInstance());
                window.setMember("logService", LogService.getInstance());
            }
        });
        try {
            webEngine.load(FileUtils.getFile(".", "assets", "index.html").toURI().toURL().toExternalForm());
        } catch (Exception e) {
            LogService.getInstance().fatal("app ui load failed", e);
            System.exit(1);
        }

        StackPane layoutRoot = new StackPane(webView);
        Scene scene = new Scene(layoutRoot);

        try {
            primaryStage.getIcons().add(new Image(FileUtils.getFile(".", "assets", "icon.png").toURI().toURL().toExternalForm()));
        } catch (Exception e) {
            LogService.getInstance().error("app icon load failed", e);
        }
        primaryStage.setTitle(AppConfig.getInstance().getDisplayName());
        primaryStage.setOpacity(SettingsService.getInstance().getOpacity());
        primaryStage.setAlwaysOnTop(SettingsService.getInstance().getAlwaysOnTop());
        primaryStage.setMinWidth(468d);
        primaryStage.setWidth(468d);
        primaryStage.setMinHeight(880d);
        primaryStage.setHeight(880d);
        primaryStage.setScene(scene);
        primaryStage.setOnCloseRequest((event) -> {
            Alert alert = new Alert(AlertType.CONFIRMATION);
            Stage alertStage = (Stage) alert.getDialogPane().getScene().getWindow();
            if (!primaryStage.getIcons().isEmpty())
            {
                alertStage.getIcons().add(primaryStage.getIcons().get(0));
            }
            alert.initOwner(primaryStage);
            alert.setTitle("提示");
            alert.setHeaderText(null);
            alert.setContentText("确认要退出吗?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                try {
                    DataAccessCenter.getInstance().close();
                } catch (Exception e) {
                    LogService.getInstance().error("database close failed", e);
                }
                LogService.getInstance().info("app close success");
            } else {
                event.consume();
            }
        });
        primaryStage.show();
        LogService.getInstance().info("app open success");
    }
}

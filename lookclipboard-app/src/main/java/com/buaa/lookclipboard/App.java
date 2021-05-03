/*
 * @Author: Zhe Chen
 * @Date: 2021-04-21 14:04:41
 * @LastEditors: Zhe Chen
 * @LastEditTime: 2021-05-03 23:48:55
 * @Description: 应用
 */
package com.buaa.lookclipboard;

import java.util.Optional;

import com.buaa.lookclipboard.dao.DataAccessCenter;
import com.buaa.lookclipboard.dao.impl.RecordDao;
import com.buaa.lookclipboard.service.impl.ClipboardService;
import com.buaa.lookclipboard.service.impl.SettingsService;

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

    public static void main(String[] args) throws Exception {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        stage = primaryStage;

        WebView webView = new WebView();
        webView.setContextMenuEnabled(false);
        webEngine = webView.getEngine();
        webEngine.getLoadWorker().stateProperty().addListener((ov, oldState, newState) -> {
            if (newState.equals(State.SUCCEEDED)) {
                try {
                    DataAccessCenter.getInstance().open();
                } catch (Exception e) {
                    e.printStackTrace();
                    System.exit(1);
                }
                RecordDao.getInstance().createIfNotExists();

                JSObject window = (JSObject) webEngine.executeScript("window");
                window.setMember("clipboardService", ClipboardService.getInstance());
                window.setMember("settingsService", SettingsService.getInstance());
            }
        });
        webEngine.load(getClass().getResource("/assets/index.html").toExternalForm());

        StackPane layoutRoot = new StackPane(webView);
        Scene scene = new Scene(layoutRoot);

        primaryStage.getIcons().add(new Image(getClass().getResource("/assets/ClipboardIcon.png").toExternalForm()));
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
            alert.initOwner(primaryStage);
            ((Stage) alert.getDialogPane().getScene().getWindow()).getIcons().add(primaryStage.getIcons().get(0));
            alert.setTitle("提示");
            alert.setHeaderText(null);
            alert.setContentText("确认要退出吗?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                try {
                    DataAccessCenter.getInstance().close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                event.consume();
            }
        });
        primaryStage.show();
    }
}

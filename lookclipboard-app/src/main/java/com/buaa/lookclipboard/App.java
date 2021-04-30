/*
 * @Author: Zhe Chen
 * @Date: 2021-04-21 14:04:41
 * @LastEditors: Zhe Chen
 * @LastEditTime: 2021-04-30 19:56:53
 * @Description: 应用
 */
package com.buaa.lookclipboard;

import com.buaa.lookclipboard.service.ClipboardService;
import com.buaa.lookclipboard.service.SettingsService;

import javafx.application.Application;
import javafx.concurrent.Worker.State;
import javafx.scene.Scene;
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
    private static WebEngine webEngine;

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
        WebView webView = new WebView();
        webView.setContextMenuEnabled(false);
        webEngine = webView.getEngine();
        webEngine.getLoadWorker().stateProperty().addListener((ov, oldState, newState) -> {
            if (newState.equals(State.SUCCEEDED)) {
                JSObject window = (JSObject) webEngine.executeScript("window");
                window.setMember("clipboardService", ClipboardService.instance);
                window.setMember("settingsService", SettingsService.instance);
            }
        });
        webEngine.load(getClass().getResource("/assets/index.html").toExternalForm());

        StackPane layoutRoot = new StackPane(webView);
        Scene scene = new Scene(layoutRoot);

        primaryStage.getIcons().add(new Image(getClass().getResource("/assets/ClipboardIcon.png").toExternalForm()));
        primaryStage.setTitle("LookClipboard");
        primaryStage.setMinWidth(468d);
        primaryStage.setWidth(468d);
        primaryStage.setMinHeight(880d);
        primaryStage.setHeight(880d);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
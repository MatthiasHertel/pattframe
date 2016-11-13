package org.blueberry.kentuckyderby;

import javafx.application.Application;
import javafx.stage.Stage;

public class KentuckyDerby extends Application {

    private UserInterface userInterface;

    @Override
    public void init() throws Exception {
        super.init();

        userInterface = new UserInterface();
        Configuration config = new Configuration();
        if (! config.load()) {
            config.printError(System.err);
            System.exit(1);
        }
        userInterface.construct(config);
    }

    @Override
    public void start(Stage primaryStage) {
        userInterface.assemble(primaryStage);
    }

    public static void main(String args[]) {
        KentuckyDerby.launch(args);
    }
}

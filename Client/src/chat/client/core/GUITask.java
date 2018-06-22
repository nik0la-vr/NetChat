package chat.client.core;

import java.util.function.Consumer;

public class GUITask {
    public Consumer<String> action;
    public String data;

    public GUITask(Consumer<String> action, String data) {
        this.action = action;
        this.data = data;
    }
}

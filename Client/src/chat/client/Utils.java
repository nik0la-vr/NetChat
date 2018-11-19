package chat.client;

public class Utils {
    /*
    * @param  line SEND|RECEIVE all|&lt;name&gt; &lt;message&gt;
    * @return      &lt;message&gt;
    */
    public static String extractMessage(String line) {
        return line.replaceFirst("\\w+\\s+.+?\\s+", "");
    }
}

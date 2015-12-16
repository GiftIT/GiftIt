package logic.connectors;

/**
 * Allows to get response from vk. To see more details about vk api visit <a href='http://vk.com/dev'>vk.com</a>
 */
public class VkApiConnection extends HttpUrlConnection {

    private static VkApiConnection instance = new VkApiConnection();

    protected static VkApiConnection getInstance(){
        return instance;
    }

    private VkApiConnection() {
        super("https://api.vk.com/method/");
    }
}

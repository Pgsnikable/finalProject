package by.training.project.manager;

import lombok.experimental.UtilityClass;

import java.util.ResourceBundle;

@UtilityClass
public class PageManager {
    private final ResourceBundle bundle = ResourceBundle.getBundle("resources.pagename");

    /**
     * Its utility class method which return result page URI
     * by her key value in appropriate {@code ResourceBundle}
     *
     * @param key its a {@code String} representation of the
     *            key value this page
     * @return {@code String}
     */
    public String getPageURI(String key) {
        return bundle.getString(key);
    }
}

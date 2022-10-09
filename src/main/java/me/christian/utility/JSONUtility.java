package me.christian.utility;

import com.google.gson.Gson;
import me.christian.App;
import me.christian.interfaces.Action;

import java.util.Map;

public class JSONUtility {

    private static final Gson gson = App.getGson();
    private static final Map<?, ?> map = gson.fromJson(App.getConfigReader(), Map.class);

    public static void execute(String searchKey, Action callback) {
        map.forEach((k, v) -> {
            if (searchKey.equalsIgnoreCase(k.toString())) {
                callback.handle(gson.toJson(v));
            }
        });
    }

}

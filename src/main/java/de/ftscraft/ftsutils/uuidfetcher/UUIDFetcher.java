package de.ftscraft.ftsutils.uuidfetcher;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Consumer;

public class UUIDFetcher {

    /**
     * Date when name changes were introduced
     * @see UUIDFetcher#getUUIDAt(String, long)
     */
    public static final long FEBRUARY_2015 = 1422748800000L;


    private static Gson gson = new GsonBuilder().registerTypeAdapter(UUID.class, new UUIDTypeAdapter()).create();

    private static final String UUID_URL = "https://api.mojang.com/users/profiles/minecraft/%s?at=%d";
    private static final String NAME_URL = "https://api.mojang.com/user/profile/%s";

    private static final Map<String, UUID> uuidCache = new HashMap<>();
    private static final Map<UUID, String> nameCache = new HashMap<>();

    private static final ExecutorService pool = Executors.newCachedThreadPool();

    private String name;
    private UUID id;

    public static UUID getUUID(String name) {
        name = name.toLowerCase();
        UUID uuid;

        if((uuid = uuidCache.get(name)) != null) {
            return uuid;
        }

        OfflinePlayer op = Bukkit.getOfflinePlayer(name);
        uuid = op.getUniqueId();
        nameCache.put(uuid, op.getName());
        uuidCache.put(name, uuid);
        return uuid;
    }

    /**
     * Fetches the name synchronously and returns it
     *
     * @param uuid The uuid
     * @return The name
     */
    public static String getName(UUID uuid) {
        if (nameCache.containsKey(uuid)) {
            return nameCache.get(uuid);
        }
        try {
            HttpURLConnection connection = (HttpURLConnection) new URL(String.format(NAME_URL, UUIDTypeAdapter.fromUUID(uuid))).openConnection();
            connection.setReadTimeout(5000);
            UUIDFetcher currentNameData = gson.fromJson(new BufferedReader(new InputStreamReader(connection.getInputStream())), UUIDFetcher.class);

            uuidCache.put(currentNameData.name.toLowerCase(), uuid);
            nameCache.put(uuid, currentNameData.name);

            return currentNameData.name;
        } catch (Exception ignore) {

        }

        return null;
    }


    public static class UUIDTypeAdapter extends TypeAdapter<UUID> {
        public void write(JsonWriter out, UUID value) throws IOException {
            out.value(fromUUID(value));
        }

        public UUID read(JsonReader in) throws IOException {
            return fromString(in.nextString());
        }

        public static String fromUUID(UUID value) {
            return value.toString().replace("-", "");
        }

        public static UUID fromString(String input) {
            return UUID.fromString(input.replaceFirst(
                    "(\\w{8})(\\w{4})(\\w{4})(\\w{4})(\\w{12})", "$1-$2-$3-$4-$5"));
        }
    }

}

package main.java.com.djrapitops.plan.ui.graphs;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import main.java.com.djrapitops.plan.Plan;
import main.java.com.djrapitops.plan.data.SessionData;
import main.java.com.djrapitops.plan.utilities.FormatUtils;
import static org.bukkit.plugin.java.JavaPlugin.getPlugin;

/**
 *
 * @author Rsl1122
 */
public class PlayerActivityGraphCreator {

    /**
     *
     * @param sessionData
     * @param scale
     * @return
     */
    public static String[] generateDataArray(List<SessionData> sessionData, long scale) {
        Plan plugin = getPlugin(Plan.class);
        int maxPlayers = plugin.getHandler().getMaxPlayers();
        long now = new Date().toInstant().getEpochSecond() * (long) 1000;
        long nowMinusScale = now - scale;
        CopyOnWriteArrayList<Long> sessionStarts = new CopyOnWriteArrayList<>();
        CopyOnWriteArrayList<Long> sessionEnds = new CopyOnWriteArrayList<>();
        CopyOnWriteArrayList<SessionData> s = new CopyOnWriteArrayList(sessionData);
        s.parallelStream()
                .filter(session -> (session != null))
                .filter((session) -> (session.getSessionStart() > nowMinusScale))
                .forEach((session) -> {
                    sessionEnds.add(session.getSessionEnd());
                    sessionStarts.add(session.getSessionStart());
                });
        List<Integer> playersOnline = new ArrayList<>();
        List<String> labels = new ArrayList<>();

        int lastPValue = 0;
        int lastSavedPValue = -1;
        long lastSaveI = 0;
        for (long i = nowMinusScale; i <= now; i += 1000) {
            final long j = i;
            if (sessionStarts.contains(i)) {
                int amount = 0;
                amount = sessionStarts.parallelStream()
                        .filter((start) -> (start == j))
                        .map((_item) -> 1)
                        .reduce(amount, Integer::sum);
                lastPValue += amount;
            }
            if (sessionEnds.contains(i)) {
                int amount = 0;
                amount = sessionEnds.parallelStream()
                        .filter((end) -> (end == j))
                        .map((_item) -> 1)
                        .reduce(amount, Integer::sum);
                lastPValue -= amount;
            }

            if (lastSavedPValue != lastPValue || i - lastSaveI > (scale / (long) 75)) {
                lastSaveI = i;
                labels.add("\""+FormatUtils.formatTimeStamp(i+"")+"\"");
                lastSavedPValue = lastPValue;
                playersOnline.add(lastPValue);
            }
        }
        playersOnline.add(0);
        playersOnline.add(maxPlayers);
        return new String[]{playersOnline.toString(), labels.toString()};
    }

}

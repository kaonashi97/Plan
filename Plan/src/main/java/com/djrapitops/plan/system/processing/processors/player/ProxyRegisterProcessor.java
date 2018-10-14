/*
 * License is provided in the jar as LICENSE also here:
 * https://github.com/Rsl1122/Plan-PlayerAnalytics/blob/master/Plan/src/main/resources/LICENSE
 */
package com.djrapitops.plan.system.processing.processors.player;

import com.djrapitops.plan.system.database.databases.Database;
import com.djrapitops.plan.system.processing.CriticalRunnable;
import com.djrapitops.plan.system.processing.Processing;

import java.util.UUID;

/**
 * Processor that registers a new User for all servers to use as UUID - ID reference.
 *
 * @author Rsl1122
 */
public class ProxyRegisterProcessor implements CriticalRunnable {

    private final UUID uuid;
    private final String name;
    private final long registered;
    private final Runnable[] afterProcess;

    private final Processing processing;
    private final Database database;

    ProxyRegisterProcessor(
            UUID uuid, String name, long registered,
            Processing processing,
            Database database,
            Runnable... afterProcess
    ) {
        this.uuid = uuid;
        this.name = name;
        this.registered = registered;
        this.processing = processing;
        this.database = database;
        this.afterProcess = afterProcess;
    }

    @Override
    public void run() {
        try {
            if (database.check().isPlayerRegistered(uuid)) {
                return;
            }
            database.save().registerNewUser(uuid, registered, name);
        } finally {
            for (Runnable process : afterProcess) {
                processing.submit(process);
            }
        }
    }
}
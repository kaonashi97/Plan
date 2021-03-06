/*
 *  This file is part of Player Analytics (Plan).
 *
 *  Plan is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU Lesser General Public License v3 as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  Plan is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU Lesser General Public License for more details.
 *
 *  You should have received a copy of the GNU Lesser General Public License
 *  along with Plan. If not, see <https://www.gnu.org/licenses/>.
 */
package com.djrapitops.plan.capability;

import java.util.function.Consumer;

/**
 * Singleton instance implementation for {@link CapabilityService}.
 * <p>
 * Only one instance exists per runtime in order to notify others when the plugin enables.
 *
 * @author Rsl1122
 */
public class CapabilitySvc implements CapabilityService {

    private CapabilitySvc() {
        // Hide constructor
    }

    /**
     * Implementation detail.
     *
     * @param isEnabled Did the plugin enable properly.
     */
    public static void notifyAboutEnable(boolean isEnabled) {
        for (Consumer<Boolean> enableListener : CapabilityService.ListHolder.ENABLE_LISTENERS.get()) {
            enableListener.accept(isEnabled);
        }
    }
}

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
package com.djrapitops.plan.delivery.webserver.resolver.json;

import com.djrapitops.plan.delivery.rendering.json.JSONFactory;
import com.djrapitops.plan.delivery.web.resolver.Resolver;
import com.djrapitops.plan.delivery.web.resolver.Response;
import com.djrapitops.plan.delivery.web.resolver.request.Request;
import com.djrapitops.plan.delivery.web.resolver.request.WebUser;
import com.djrapitops.plan.delivery.webserver.cache.DataID;
import com.djrapitops.plan.delivery.webserver.cache.JSONCache;
import com.djrapitops.plan.identification.Identifiers;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.Optional;
import java.util.UUID;

/**
 * Resolves /v1/players JSON requests.
 *
 * @author Rsl1122
 */
@Singleton
public class PlayersTableJSONResolver implements Resolver {

    private final Identifiers identifiers;
    private final JSONFactory jsonFactory;

    @Inject
    public PlayersTableJSONResolver(
            Identifiers identifiers,
            JSONFactory jsonFactory
    ) {
        this.identifiers = identifiers;
        this.jsonFactory = jsonFactory;
    }

    @Override
    public boolean canAccess(Request request) {
        WebUser user = request.getUser().orElse(new WebUser(""));
        if (request.getQuery().get("server").isPresent()) {
            return user.hasPermission("page.server");
        }
        // Assume players page
        return user.hasPermission("page.players");
    }

    @Override
    public Optional<Response> resolve(Request request) {
        return Optional.of(getResponse(request));
    }

    private Response getResponse(Request request) {
        if (request.getQuery().get("server").isPresent()) {
            UUID serverUUID = identifiers.getServerUUID(request); // Can throw BadRequestException
            return JSONCache.getOrCache(DataID.PLAYERS, serverUUID, () -> jsonFactory.serverPlayersTableJSON(serverUUID));
        }
        // Assume players page
        return JSONCache.getOrCache(DataID.PLAYERS, jsonFactory::networkPlayersTableJSON);
    }
}

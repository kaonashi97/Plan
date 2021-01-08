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
package com.djrapitops.plan.exceptions.database;

/**
 * Thrown when something goes wrong with {@code Database#init}.
 *
 * @author Rsl1122
 */
public class DBInitException extends DBOpException {

    private static final long serialVersionUID = 1L;

    public DBInitException(String message, Throwable cause) {
        super(message, cause);
    }

    public DBInitException(String message) {
        super(message);
    }
}

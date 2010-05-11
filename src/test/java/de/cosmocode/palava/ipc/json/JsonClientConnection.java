/**
 * Copyright 2010 CosmoCode GmbH
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package de.cosmocode.palava.ipc.json;

import java.util.List;
import java.util.Map;

import de.cosmocode.palava.ipc.netty.ClientConnection;

/**
 * A json specific connection on top of {@link ClientConnection}.
 *
 * @since 1.0
 * @author Willi Schoenborn
 */
public interface JsonClientConnection extends ClientConnection {

    /**
     * Sends the specified string and notifies the given callback asynchronously.
     * 
     * @since 1.0
     * @param <T> generic return type
     * @param request the request to be sent
     * @return the response
     * @throws NullPointerException if request is null
     * @throws IllegalStateException if not connected
     */
    <T> T send(List<?> request);

    /**
     * Sends the specified string and notifies the given callback asynchronously.
     * 
     * @since 1.0
     * @param <T> generic return type
     * @param request the request to be sent
     * @return the response
     * @throws NullPointerException if request is null
     * @throws IllegalStateException if not connected
     */
    <T> T send(Map<?, ?> request);
    
}

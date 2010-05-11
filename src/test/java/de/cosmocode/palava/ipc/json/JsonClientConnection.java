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

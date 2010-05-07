package de.cosmocode.palava.ipc.json;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.List;
import java.util.Map;

import org.codehaus.jackson.map.ObjectMapper;

import de.cosmocode.palava.ipc.netty.AbstractClient;
import de.cosmocode.palava.ipc.netty.Client;
import de.cosmocode.palava.ipc.netty.Connection;
import de.cosmocode.palava.ipc.netty.NettyClient;

/**
 * Json specific {@link Client} implementation.
 *
 * @since 1.0
 * @author Willi Schoenborn
 */
public final class JsonClient extends AbstractClient implements Client {

    private final ObjectMapper mapper = new ObjectMapper();
    
    private final Client client = new NettyClient();
    
    @Override
    public JsonClientConnection connect(InetSocketAddress address) {
        final Connection connection = client.connect(address);
        return new InternalJsonConnection(connection);
    }
    
    /**
     * Internal implementation of the {@link JsonClientConnection} interface.
     *
     * @since 1.0
     * @author Willi Schoenborn
     */
    private final class InternalJsonConnection implements JsonClientConnection {
        
        private final Connection connection;
        
        public InternalJsonConnection(Connection connection) {
            this.connection = connection;
        }

        @Override
        public String send(String request) {
            return connection.send(request);
        }
        
        @Override
        public void disconnect() {
            connection.disconnect();
        }
        
        @Override
        public <T> T send(Map<?, ?> request) {
            return send(Object.class.cast(request));
        }
        
        @Override
        public <T> T send(List<?> request) {
            return send(Object.class.cast(request));
        }
        
        private <T> T send(Object request) {
            try {
                @SuppressWarnings("unchecked")
                final T result = (T) read(send(mapper.writeValueAsString(request)));
                return result;
            } catch (IOException e) {
                throw new IllegalArgumentException(e);
            }
        }
        
        private Object read(String value) throws IOException {
            if (value.charAt(0) == '[') {
                return mapper.readValue(value, List.class);
            } else if (value.charAt(0) == '{') {
                return mapper.readValue(value, Map.class);
            } else {
                throw new IllegalArgumentException(String.format("%s is no valid json", value));
            }
        }
        
    }

    @Override
    public void shutdown() {
        client.shutdown();
    }

}

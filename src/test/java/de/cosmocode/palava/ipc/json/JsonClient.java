package de.cosmocode.palava.ipc.json;

import java.net.InetSocketAddress;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.cosmocode.palava.ipc.netty.Client;
import de.cosmocode.palava.ipc.netty.Connection;

public final class JsonClient implements Client {

    private static final Logger LOG = LoggerFactory.getLogger(JsonClient.class);

    @Override
    public Connection connect(String host, int port) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Connection connect(InetSocketAddress address) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void shutdown() {
        // TODO Auto-generated method stub

    }

}

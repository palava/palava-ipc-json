package de.cosmocode.palava.ipc.json;

import org.jboss.netty.channel.ChannelHandler;

import com.google.inject.Binder;
import com.google.inject.Module;
import com.google.inject.Scopes;

/**
 * Binds {@link JsonFrameDecoder} as {@link JsonFraming} {@link ChannelHandler}.
 *
 * @since 
 * @author Willi Schoenborn
 */
public final class JsonFrameDecoderModule implements Module {

    @Override
    public void configure(Binder binder) {
        // frame decoder is stateful
        binder.bind(JsonFrameDecoder.class).in(Scopes.NO_SCOPE);
        binder.bind(ChannelHandler.class).annotatedWith(JsonFraming.class).to(JsonFrameDecoder.class);
    }

}

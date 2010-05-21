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

import org.jboss.netty.channel.ChannelHandler;
import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.jboss.netty.channel.Channels;
import org.jboss.netty.handler.codec.string.StringDecoder;
import org.jboss.netty.handler.codec.string.StringEncoder;

import com.google.common.base.Charsets;
import com.google.common.base.Predicate;
import com.google.inject.Binder;
import com.google.inject.Module;
import com.google.inject.Provides;
import com.google.inject.Singleton;

import de.cosmocode.palava.core.Registry;
import de.cosmocode.palava.ipc.netty.ConnectionManager;
import de.cosmocode.palava.ipc.netty.ProtocolHandler;
import de.cosmocode.palava.ipc.netty.ProtocolHandlerModule;
import de.cosmocode.palava.ipc.protocol.Protocol;

/**
 * Binds json {@link ChannelPipelineFactory}, {@link ChannelHandler}s, etc.
 *
 * @since 1.0
 * @author Willi Schoenborn
 */
public final class JsonNettyModule implements Module {

    @Override
    public void configure(Binder binder) {
        binder.bind(StringDecoder.class).toInstance(new StringDecoder(Charsets.UTF_8));
        binder.bind(StringEncoder.class).toInstance(new StringEncoder(Charsets.UTF_8));
        binder.bind(JsonDecoder.class).in(Singleton.class);
        binder.bind(JsonEncoder.class).in(Singleton.class);
        binder.install(new ProtocolHandlerModule(Json.class));
    }
    
    /**
     * Provides a channel pipeline.
     * 
     * @since 1.0
     * @param manager the connection manager
     * @param frameDecoder the frame decoder used to frame json structures
     * @param stringDecoder the string decoder
     * @param stringEncoder the string encoder
     * @param decoder string to json decoder
     * @param encoder json to string encoder
     * @param handler json handler
     * @return new {@link ChannelPipeline}
     */
    @Provides
    @Json
    ChannelPipeline provideChannelPipeline(
        ConnectionManager manager,
        @JsonFraming ChannelHandler frameDecoder,
        StringDecoder stringDecoder, StringEncoder stringEncoder,
        JsonDecoder decoder, JsonEncoder encoder, @Json ProtocolHandler handler) {
        return Channels.pipeline(
            manager,
            frameDecoder,
            stringDecoder, stringEncoder,
            decoder, encoder,
            handler
        );
    }
    
    /**
     * Provides all json protocols.
     * 
     * @since 1.0 
     * @param registry the current registry
     * @return iterable of json protocols
     */
    @Provides
    @Singleton
    @Json
    Iterable<Protocol> provideProtocols(Registry registry) {
        return registry.find(Protocol.class, new Predicate<Object>() {
            
            @Override
            public boolean apply(Object input) {
                return input == null || input == Json.class;
            }
            
        });
    }
    
}

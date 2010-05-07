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
import com.google.inject.Binder;
import com.google.inject.Module;
import com.google.inject.Provider;
import com.google.inject.Provides;
import com.google.inject.Singleton;

/**
 * Binds json {@link ChannelPipelineFactory}, {@link ChannelHandler}s, etc.
 *
 * @since 1.0
 * @author Willi Schoenborn
 */
public final class JsonNettyModule implements Module {

    private static final StringDecoder STRING_DECODER = new StringDecoder(Charsets.UTF_8);
    private static final StringEncoder STRING_ENCODER = new StringEncoder(Charsets.UTF_8);
    
    @Override
    public void configure(Binder binder) {
        binder.bind(JsonDecoder.class).in(Singleton.class);
        binder.bind(JsonEncoder.class).in(Singleton.class);
        binder.bind(JsonHandler.class).in(Singleton.class);
    }

    /**
     * Provides a channel pipeline.
     * 
     * @since 1.0
     * @param frameDecoder the frame decoder used to frame json structures
     * @param decoder string to json decoder
     * @param encoder json to string encoder
     * @param handler json handler
     * @return new {@link ChannelPipeline}
     */
    @Provides
    ChannelPipeline provideChannelPipeline(@JsonFraming ChannelHandler frameDecoder, JsonDecoder decoder, 
        JsonEncoder encoder, JsonHandler handler) {
        return Channels.pipeline(
            frameDecoder,
            STRING_DECODER, STRING_ENCODER,
            decoder, encoder,
            handler
        );
    }

    /**
     * Provides a channel pipeline factory.
     * 
     * @since 1.0
     * @param pipeline provider for the underlying pipeline
     * @return a {@link ChannelPipelineFactory}
     */
    @Provides
    @Singleton
    ChannelPipelineFactory providerChannelPipelineFactory(final Provider<ChannelPipeline> pipeline) {
        return new ChannelPipelineFactory() {
            
            @Override
            public ChannelPipeline getPipeline() {
                return pipeline.get();
            }
            
        };
    }
    
}

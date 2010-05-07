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

import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.jboss.netty.channel.Channels;
import org.jboss.netty.handler.codec.string.StringDecoder;
import org.jboss.netty.handler.codec.string.StringEncoder;

import com.google.common.base.Charsets;
import com.google.inject.Inject;

/**
 * Json based implementation of the {@link ChannelPipelineFactory}.
 *
 * @since 
 * @author Willi Schoenborn
 */
final class JsonChannelPipelineFactory implements ChannelPipelineFactory {

    private final JsonFrameDecoder frameDecoder;
    private final JsonDecoder decoder;
    private final JsonEncoder encoder;
    private final JsonHandler handler;

    @Inject
    public JsonChannelPipelineFactory(JsonFrameDecoder frameDecoder, JsonDecoder decoder, 
        JsonEncoder encoder, JsonHandler handler) {
        this.frameDecoder = frameDecoder;
        this.decoder = decoder;
        this.encoder = encoder;
        this.handler = handler;
    }

    @Override
    public ChannelPipeline getPipeline() throws Exception {
        final ChannelPipeline pipeline = Channels.pipeline();
        
        pipeline.addLast("framer", frameDecoder);
        pipeline.addLast("string-decoder", new StringDecoder(Charsets.UTF_8));
        pipeline.addLast("json-decoder", decoder);
        pipeline.addLast("json-encoder", encoder);
        pipeline.addLast("string-encoder", new StringEncoder(Charsets.UTF_8));
        pipeline.addLast("handler", handler);
        
        return pipeline;
    }

}

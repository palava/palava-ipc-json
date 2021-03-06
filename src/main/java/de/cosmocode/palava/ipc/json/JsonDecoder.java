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

import java.io.InputStream;
import java.util.List;
import java.util.Map;

import javax.annotation.concurrent.ThreadSafe;

import org.codehaus.jackson.map.ObjectMapper;
import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBufferInputStream;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelException;
import org.jboss.netty.channel.ChannelHandler.Sharable;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.handler.codec.oneone.OneToOneDecoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Charsets;
import com.google.common.base.Preconditions;
import com.google.inject.Inject;

/**
 * Decodes a json {@link ChannelBuffer} into a {@link List} or a {@link Map}.
 *
 * @since 1.0
 * @author Willi Schoenborn
 */
@Sharable
@ThreadSafe
public final class JsonDecoder extends OneToOneDecoder {

    private static final Logger LOG = LoggerFactory.getLogger(JsonDecoder.class);

    private final ObjectMapper mapper;
    
    @Inject
    public JsonDecoder(ObjectMapper mapper) {
        this.mapper = Preconditions.checkNotNull(mapper, "Mapper");
    }
    
    @Override
    protected Object decode(ChannelHandlerContext ctx, Channel channel, Object message) throws Exception {
        if (message instanceof ChannelBuffer) {
            final ChannelBuffer buffer = ChannelBuffer.class.cast(message);
            final InputStream stream = new ChannelBufferInputStream(buffer);
            final byte c = buffer.getByte(0);
            if (c == '{') {
                LOG.trace("Decoding map from {}", stream);
                return mapper.readValue(stream, Map.class);
            } else if (c == '[') {
                LOG.trace("Decoding list from {}", stream);
                return mapper.readValue(stream, List.class);
            } else {
                throw new ChannelException(String.format("Invalid json %s", buffer.toString(Charsets.UTF_8)));
            }
        } else {
            return message;
        }
    }
    
}

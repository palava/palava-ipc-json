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

import java.io.OutputStream;

import javax.annotation.concurrent.ThreadSafe;

import org.codehaus.jackson.map.ObjectMapper;
import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ChannelHandler.Sharable;
import org.jboss.netty.handler.codec.oneone.OneToOneEncoder;

import com.google.common.base.Preconditions;
import com.google.inject.Inject;

import de.cosmocode.palava.ipc.netty.ChannelBuffering;

/**
 * Encodes objects into json {@link ChannelBuffer}.
 *
 * @since 1.0
 * @author Willi Schoenborn
 */
@Sharable
@ThreadSafe
final class JsonEncoder extends OneToOneEncoder {

    /**
     * Defaults to the same value as {@link ChannelBuffers#dynamicBuffer()}.
     */
    private int estimatedResponseLength = 256;
    
    private final ObjectMapper mapper;
    
    @Inject
    public JsonEncoder(ObjectMapper mapper) {
        this.mapper = Preconditions.checkNotNull(mapper, "Mapper");
    }
    
    @Inject(optional = true)
    void setEstimatedResponseLength(int estimatedResponseLength) {
        this.estimatedResponseLength = estimatedResponseLength;
    }
    
    @Override
    protected Object encode(ChannelHandlerContext ctx, Channel channel, Object message) throws Exception {
        final ChannelBuffer buffer = ChannelBuffers.dynamicBuffer(estimatedResponseLength);
        final OutputStream stream = ChannelBuffering.asOutputStream(buffer);
        mapper.writeValue(stream, message);
        return buffer;
    }

}

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

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.handler.codec.frame.FrameDecoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A {@link FrameDecoder} which frames json arrays/objects.
 *
 * @since 1.0
 * @author Willi Schoenborn
 */
final class JsonFrameDecoder extends FrameDecoder {

    private static final Logger LOG = LoggerFactory.getLogger(JsonFrameDecoder.class);

    @Override
    protected Object decode(ChannelHandlerContext ctx, Channel channel, ChannelBuffer buffer) throws Exception {
        if (buffer.readable()) {
            final byte b = buffer.getByte(0);
            final int index;
            if (b == '[') {
                index = array(buffer);
            } else if (b == '{') {
                index = object(buffer);
            } else {
                LOG.warn("Unknown data starting with {}", b);
                return null;
            }
            return index == -1 ? null : buffer.readBytes(index + 1);
        } else {
            return null;
        }
    }
    
    private int array(ChannelBuffer buffer) {
        return structure('[', ']', buffer);
    }
    
    private int object(ChannelBuffer buffer) {
        return structure('{', '}', buffer);
    }
    
    // TODO handle escapes/strings
    private int structure(char open, char close, ChannelBuffer buffer) {
        int arrays = 0;
        
        int i = buffer.readerIndex();
        
        while (i < buffer.writerIndex()) {
            final byte current = buffer.getByte(i);
            
            if (current == open) {
                arrays += 1;
            } else if (current == close) {
                arrays -= 1;
                if (arrays == 0) return i - buffer.readerIndex();
            }
            
            i++;
        }
        
        return -1;
    }

}

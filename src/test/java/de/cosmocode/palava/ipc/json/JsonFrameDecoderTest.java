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

import java.io.File;
import java.io.IOException;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;
import org.junit.Assert;
import org.junit.Test;

import com.google.common.base.Charsets;
import com.google.common.io.Files;

/**
 * Tests {@link JsonFrameDecoder}.
 *
 * @since 1.1
 * @author Willi Schoenborn
 */
public final class JsonFrameDecoderTest {


    /**
     * Tests {@link JsonFrameDecoder#decode(.ChannelHandlerContext, Channel, ChannelBuffer)} using
     * a random json structure.
     * 
     * @since 1.1
     * @throws IOException if reading the dummy file failed 
     */
    @Test
    public void test() throws IOException {
        final File file = new File("src/test/resources/large.json");
        final String content = Files.toString(file, Charsets.UTF_8);
        final ChannelBuffer buffer = ChannelBuffers.copiedBuffer(content, Charsets.UTF_8);
        final JsonFrameDecoder decoder = new JsonFrameDecoder();
        
        try {
            decoder.decode(null, null, buffer);
        /* CHECKSTYLE:OFF */
        } catch (Exception e) {
        /* CHECKSTYLE:ON */
            throw new AssertionError(e);
        }
        
        Assert.assertEquals(0, decoder.getCounter());
    }
    
}

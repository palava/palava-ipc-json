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

import org.codehaus.jackson.map.ObjectMapper;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.handler.codec.oneone.OneToOneEncoder;

/**
 * Encodes objects into json.
 *
 * @since 1.0
 * @author Willi Schoenborn
 */
final class JsonEncoder extends OneToOneEncoder {

    private static final ObjectMapper MAPPER = new ObjectMapper();
    
    @Override
    protected Object encode(ChannelHandlerContext ctx, Channel channel, Object message) throws Exception {
        return MAPPER.writeValueAsString(message);
    }

}

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

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

import com.google.inject.Binder;
import com.google.inject.Key;
import com.google.inject.Module;

import de.cosmocode.palava.concurrent.DefaultThreadProviderModule;
import de.cosmocode.palava.concurrent.ExecutorModule;
import de.cosmocode.palava.core.DefaultRegistryModule;
import de.cosmocode.palava.core.inject.TypeConverterModule;
import de.cosmocode.palava.core.lifecycle.LifecycleModule;
import de.cosmocode.palava.ipc.IpcEventModule;
import de.cosmocode.palava.ipc.netty.Boss;
import de.cosmocode.palava.ipc.netty.ChannelPipelineFactoryModule;
import de.cosmocode.palava.ipc.netty.DefaultConnectionManagerModule;
import de.cosmocode.palava.ipc.netty.NettyServiceModule;
import de.cosmocode.palava.ipc.netty.NioServerSocketChannelFactoryModule;
import de.cosmocode.palava.ipc.netty.Worker;
import de.cosmocode.palava.ipc.protocol.EchoProtocol;
import de.cosmocode.palava.jmx.FakeMBeanServerModule;

/**
 * Test module.
 *
 * @since 1.0
 * @author Willi Schoenborn
 */
public final class JsonTestModule implements Module {

    @Override
    public void configure(Binder binder) {
        binder.install(new LifecycleModule());
        binder.install(new TypeConverterModule());
        binder.install(new DefaultRegistryModule());
        binder.install(new DefaultThreadProviderModule());
        binder.install(new FakeMBeanServerModule());
        
        binder.install(new ExecutorModule(Boss.class, Boss.NAME));
        binder.install(new ExecutorModule(Worker.class, Worker.NAME));
        
        binder.install(new IpcEventModule());
        
        binder.install(new NettyServiceModule());
        binder.install(new ChannelPipelineFactoryModule());
        binder.install(new NioServerSocketChannelFactoryModule());
        binder.install(new DefaultConnectionManagerModule());
        binder.bind(ChannelPipeline.class).to(Key.get(ChannelPipeline.class, Json.class));
        binder.install(new JsonFrameDecoderModule());
        binder.install(new JsonNettyModule());
        binder.bind(EchoProtocol.class).asEagerSingleton();
    }

}

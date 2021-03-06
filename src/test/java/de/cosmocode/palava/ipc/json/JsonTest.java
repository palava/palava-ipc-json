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

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;

import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.JsonGenerator;
import org.junit.Assert;
import org.junit.Test;

import de.cosmocode.palava.core.Framework;
import de.cosmocode.palava.core.Palava;
import de.cosmocode.palava.ipc.netty.Client;
import de.cosmocode.palava.ipc.netty.ClientConnection;
import de.cosmocode.palava.ipc.netty.NettyClient;

/**
 * Tests json communication.
 *
 * @since 
 * @author Willi Schoenborn
 */
public final class JsonTest {

    /**
     * Tests application boot.
     * 
     * @since 1.0
     * @throws IOException should not happen
     */
    @Test
    public void boot() throws IOException {
        final Framework framework = Palava.newFramework();
        framework.start();
        
        final Client client = new NettyClient();
        final ClientConnection connection = client.connect("localhost", 8081);
        
        Writer writer;
        JsonGenerator generator;
        
        writer = new StringWriter();
        generator = new JsonFactory().createJsonGenerator(writer);
        
        generator.writeStartObject();
        generator.writeFieldName("name");
        generator.writeString(getClass().getName());
        generator.writeEndObject();
        generator.flush();
        generator.close();
        
        Assert.assertEquals(writer.toString(), connection.send(writer.toString()));
        
        writer = new StringWriter();
        generator = new JsonFactory().createJsonGenerator(writer);
        
        generator.writeStartArray();
        generator.writeString(getClass().getName());
        generator.writeEndArray();
        generator.flush();
        generator.close();
        
        Assert.assertEquals(writer.toString(), connection.send(writer.toString()));
        
        framework.stop();
    }
    
}

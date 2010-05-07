package de.cosmocode.palava.ipc.json;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.jboss.netty.channel.ChannelHandler;

import com.google.inject.BindingAnnotation;

/**
 * Binding annotation for a {@link ChannelHandler} used for framing
 * json values.
 *
 * @since 
 * @author Willi Schoenborn
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({
    ElementType.METHOD,
    ElementType.PARAMETER
})
@BindingAnnotation
public @interface JsonFraming {

}

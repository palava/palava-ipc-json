package de.cosmocode.palava.ipc.json;

/**
 * MBean interface for {@link JsonHandler}.
 *
 * @since 1.0
 * @author Willi Schoenborn
 */
public interface JsonHandlerMBean {

    /**
     * Returns the amount all bytes written.
     * 
     * @since 1.0
     * @return total amount of bytes written
     */
    long getOverallThroughput();
    
    /**
     * Returns the amount of bytes written per day.
     * 
     * @since 1.0
     * @return the rounded amount of bytes written per day
     */
    long getDailyThrougput();
    
}

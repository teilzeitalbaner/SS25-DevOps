package de.fherfurt.taskvault.core;

import java.io.IOException;
import java.util.logging.Logger;

public class ExceptionHandler {
    private static final Logger LOGGER = Logger.getLogger( ExceptionHandler.class.getSimpleName() );

    /**
     * Method to handle <code>IOExceptions</code>
     *
     * @param ioe the exception which should be handled
     * @param source the class that catched the exception
     * @param message an optional message to describe the exception
     */
    public static void handle(IOException ioe, Object source, String message) {
        LOGGER.severe( "Error during I/O operation" );
        LOGGER.severe( "Error occurred in " + source.getClass() );

        if( message != null ) {
            LOGGER.severe( "Message: " + message );
        }

        ioe.printStackTrace();
    }

    /**
     * Method to handle <code>IOExceptions</code>
     *
     * @param ioe the exception which should be handled
     * @param source the class that catched the exception
     */
    public static void handle( IOException ioe, Object source ) {
        handle( ioe, source, null );
    }

    /**
     * Method to handle <code>ClassNotFoundException</code>
     *
     * @param cnfe the exception which should be handled
     * @param source the class that catched the exception
     * @param message an optional message to describe the exception
     */
    public static void handle( ClassNotFoundException cnfe, Object source, String message ) {
        LOGGER.severe( "Class could not be loaded" );
        LOGGER.severe( "Error occurred in " + source.getClass() );

        if( message != null ) LOGGER.severe( "Message: " + message );

        cnfe.printStackTrace();
    }

    /**
     * Method to handle <code>ClassNotFoundException</code>
     *
     * @param cnfe the exception which should be handled
     * @param source the class that catched the exception
     */
    public static void handle( ClassNotFoundException cnfe, Object source ) {
        handle( cnfe, source, null );
    }
}

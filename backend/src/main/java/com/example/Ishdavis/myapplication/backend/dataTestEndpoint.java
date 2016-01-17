package com.example.Ishdavis.myapplication.backend;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;

import java.util.logging.Logger;

import javax.inject.Named;

/**
 * An endpoint class we are exposing
 */
@Api(
        name = "dataTestApi",
        version = "v1",
        resource = "dataTest",
        namespace = @ApiNamespace(
                ownerDomain = "backend.myapplication.Ishdavis.example.com",
                ownerName = "backend.myapplication.Ishdavis.example.com",
                packagePath = ""
        )
)
public class dataTestEndpoint {

    private static final Logger logger = Logger.getLogger(dataTestEndpoint.class.getName());

    /**
     * This method gets the <code>dataTest</code> object associated with the specified <code>id</code>.
     *
     * @param id The id of the object to be returned.
     * @return The <code>dataTest</code> associated with <code>id</code>.
     */
    @ApiMethod(name = "getdataTest")
    public dataTest getdataTest(@Named("id") Long id) {
        // TODO: Implement this function
        logger.info("Calling getdataTest method");
        return null;
    }

    /**
     * This inserts a new <code>dataTest</code> object.
     *
     * @param dataTest The object to be added.
     * @return The object to be added.
     */
    @ApiMethod(name = "insertdataTest")
    public dataTest insertdataTest(dataTest dataTest) {
        // TODO: Implement this function
        logger.info("Calling insertdataTest method");
        return dataTest;
    }
}
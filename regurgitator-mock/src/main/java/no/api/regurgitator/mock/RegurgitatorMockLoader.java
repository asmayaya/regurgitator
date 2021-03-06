package no.api.regurgitator.mock;

import no.api.regurgitator.storage.MD5DiskStorage;
import no.api.regurgitator.storage.ServerResponse;
import no.api.regurgitator.storage.ServerResponseKey;
import no.api.regurgitator.storage.ServerResponseStore;
import no.api.regurgitator.storage.header.ServerRequestMethod;

import java.util.Optional;

/**
 * Helper class for loading Regurgitator mock files from disk and into ServerResponse objects.
 *
 * This class is intended to be used in Unit and Integration tests, so it is possible to test on cache files generated
 * by Regurgitator.
 *
 * Typical usage:
 * <pre>
 *     // Remember trailing slash in path
 *     RegurgitatorMockLoader mockLoader = new RegurgitatorMockLoader("./src/test/resources/path/to/mock/dir/");
 *     Optional&lt;ServerResponse&gt; response = mockLoader.getMockFor(ServerResponseKey.RequestMethod.GET,
 * "http://www.vg.no/index.php");
 * </pre>
 */
public class RegurgitatorMockLoader {

    private final ServerResponseStore storage;

    /**
     * Mock loader constructor that will use the MD5DiskStorage implementation for managing mock files.
     *
     * @param folder
     *         The full path to the directory where the Regurgitator files are located. Remember trailing slash.
     */
    public RegurgitatorMockLoader(String folder) {
        this.storage = new MD5DiskStorage(folder);
    }

    /**
     * Mock loader constructor.
     *
     * @param serverResponseStore
     *         The response store to use for managing mock files.
     */
    public RegurgitatorMockLoader(ServerResponseStore serverResponseStore) {
        this.storage = serverResponseStore;

    }

    /**
     * Get a Regurgitator response from the mock folder.
     *
     * @param requestMethod
     *         The HTTP request method for the response.
     * @param requestURI
     *         The URI for the response.
     * @param responseStatus
     *         The expected response code. Whether more than one response type is supported depends on the
     *         ServerResponseStore implementation.
     *
     * @return Optional containing the requested server response if found, else empty.
     */
    public Optional<ServerResponse> getMockFor(ServerRequestMethod requestMethod, String requestURI,
                                               int responseStatus) {
        return storage.read(new ServerResponseKey(requestMethod, requestURI), responseStatus);
    }

}

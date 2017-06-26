package util;

/**
 * Constants for {@link MockInterceptor MockInterceptor} and unit tests.
 */
class MockConstants {
    static final String MOCK_BASE_URL = "https://mock.filestackapi.com/";
    static final String TEST_HEADER_PATH = "test-header";
    static final String TEST_BAD_REQUEST_PATH = "test-bad-request";
    static final String TEST_FORBIDDEN_PATH = "test-forbidden";
    static final String TEST_NOT_FOUND_PATH = "test-not-found";
    static final String TEST_UNMATCHED_PATH = "test-unmatched";

    static final String CDN_MOCK_FILENAME = "filestack_test.txt";
    static final String CDN_MOCK_CONTENT = "Test content for handle: %s\n%s\n";

    static final String HEADER_FILENAME = "x-file-name";

    static final int CODE_OK = 200;
    static final int CODE_BAD_REQUEST = 400;
    static final int CODE_FORBIDDEN = 403;
    static final int CODE_NOT_FOUND = 404;

    static final String MESSAGE_OK = "OK";
    static final String MESSAGE_BAD_REQUEST = "BAD REQUEST";
    static final String MESSAGE_FORBIDDEN = "FORBIDDEN";
    static final String MESSAGE_NOT_FOUND = "NOT FOUND";

    static final String MIME_TEXT = "text/plain; charset=utf-8";
    static final String MIME_JSON = "application/json";

    static final String TEST_HEADER_URL = MOCK_BASE_URL + TEST_HEADER_PATH;
    static final String TEST_BAD_REQUEST_URL = MOCK_BASE_URL + TEST_BAD_REQUEST_PATH;
    static final String TEST_FORBIDDEN_URL = MOCK_BASE_URL + TEST_FORBIDDEN_PATH;
    static final String TEST_NOT_FOUND_URL = MOCK_BASE_URL + TEST_NOT_FOUND_PATH;
    static final String TEST_UNMATCHED_URL = MOCK_BASE_URL + TEST_UNMATCHED_PATH;
}

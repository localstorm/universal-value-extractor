package org.localstorm.extraction;

/**
 * @author localstorm
 *         Date: 21.06.13
 */
public class UnsupportedDocumentType extends RuntimeException {
    public UnsupportedDocumentType() {
        super();
    }

    public UnsupportedDocumentType(String message) {
        super(message);
    }

    public UnsupportedDocumentType(String message, Throwable cause) {
        super(message, cause);
    }

    public UnsupportedDocumentType(Throwable cause) {
        super(cause);
    }
}

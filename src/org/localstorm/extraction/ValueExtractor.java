package org.localstorm.extraction;

import java.io.IOException;

/**
 * @author localstorm
 *         Date: 21.06.13
 */
public interface ValueExtractor {
     public String getValue(SourceDocument doc) throws UnsupportedDocumentType, IOException;
}

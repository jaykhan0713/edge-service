package com.jay.edge.core.port.dependency.gotenberg;

import org.springframework.web.multipart.MultipartFile;

public interface GotenbergDependency {
    byte[] convertHtmlToPdf(MultipartFile htmlFile);
}

package com.jay.edge.app.experiment.service;

import org.springframework.stereotype.Service;

import com.jay.edge.core.port.dependency.gotenberg.GotenbergDependency;
import org.springframework.web.multipart.MultipartFile;


@Service
public class PdfConverterService {

    private final GotenbergDependency gotenbergDependency;

    public PdfConverterService(
            GotenbergDependency gotenbergDependency
    ) {
        this.gotenbergDependency = gotenbergDependency;
    }

    public byte[] runExperiment(MultipartFile htmlFile) {
        return gotenbergDependency.convertHtmlToPdf(htmlFile);
    }
}

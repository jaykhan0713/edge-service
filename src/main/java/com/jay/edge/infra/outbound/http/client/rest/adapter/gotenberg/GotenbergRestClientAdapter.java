package com.jay.edge.infra.outbound.http.client.rest.adapter.gotenberg;

import java.io.IOException;

import com.jay.edge.core.port.dependency.gotenberg.GotenbergDependency;
import com.jay.edge.infra.outbound.http.client.rest.error.RestClientExceptionTranslator;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.MediaType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.client.RestClient;
import org.springframework.web.multipart.MultipartFile;

public class GotenbergRestClientAdapter implements GotenbergDependency {

    private final RestClient restClient;
    private final String clientName;
    private final String htmlUri;
    private final String officeUri;

    public GotenbergRestClientAdapter(
            RestClient restClient,
            String clientName,
            String htmlUri, //chromium
            String officeUri //libreoffice
    ) {
        this.restClient = restClient;
        this.clientName = clientName;
        this.htmlUri = htmlUri;
        this.officeUri = officeUri;
    }

    @Override
    public byte[] convertHtmlToPdf(MultipartFile htmlFile) {
        return convertMultipartToFile(htmlFile, htmlUri);
    }

    @Override
    public byte[] convertOfficeToPdf(MultipartFile officeFile) {
        return convertMultipartToFile(officeFile, officeUri);
    }

    private byte[] convertMultipartToFile(MultipartFile file, String uri) {
        return RestClientExceptionTranslator.execute(
                () -> {
                    var body = new LinkedMultiValueMap<String, Object>();

                    try {
                        body.add("files", new ByteArrayResource(file.getBytes()) {
                            @Override
                            public String getFilename() {
                                return file.getOriginalFilename();
                            }
                        });
                    } catch (IOException e) {
                        throw new RuntimeException("Failed to read multipart file", e);
                    }

                    var spec = restClient.post()
                            .uri(uri)
                            .contentType(MediaType.MULTIPART_FORM_DATA)
                            .body(body)
                            .retrieve();

                    spec = RestClientExceptionTranslator.applyDefaultOnStatusHandlers(spec, clientName);

                    return spec.body(byte[].class);
                },
                clientName
        );
    }
}

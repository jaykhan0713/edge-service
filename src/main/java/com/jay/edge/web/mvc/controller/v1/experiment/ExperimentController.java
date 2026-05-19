package com.jay.edge.web.mvc.controller.v1.experiment;

import com.jay.edge.app.experiment.service.PdfConverterService;
import com.jay.edge.core.context.identity.IdentityContextHolder;
import com.jay.edge.web.mvc.controller.v1.experiment.mapping.ExperimentMapper;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.jay.edge.app.experiment.service.ExperimentService;
import com.jay.edge.api.v1.experiment.EdgeExperimentApi;
import com.jay.edge.api.v1.experiment.model.EdgeExperimentResponse;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1/experiments")
public class ExperimentController implements EdgeExperimentApi {

    private final ExperimentService expService;
    private final PdfConverterService pdfConverterService;
    private final ExperimentMapper mapper;

    public ExperimentController(
            ExperimentService expService,
            PdfConverterService pdfConverterService,
            ExperimentMapper mapper
    ) {
        this.expService = expService;
        this.pdfConverterService = pdfConverterService;
        this.mapper = mapper;
    }

    @GetMapping("/{experimentId}")
    public EdgeExperimentResponse getExperimentResponse(
            @PathVariable String experimentId
    ) {
        var experimentResult = this.expService.runExperiment();

        return new EdgeExperimentResponse(
                experimentResult.msg(),
                experimentId,
                IdentityContextHolder.context().identity().requestId(),
                experimentResult
                        .products()
                        .stream()
                        .map(mapper::toExperimentProduct)
                        .toList()
        );
    }

    @PostMapping(value = "/convert/html", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<byte[]> convertHtmlToPdf(@RequestPart("files") MultipartFile htmlFile) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentDispositionFormData("attachment", "output.pdf");
        return new ResponseEntity<>(pdfConverterService.runConvertHtmlToPdfExperiment(htmlFile), headers, HttpStatus.OK);
    }

    @PostMapping(value = "/convert", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<byte[]> convertOfficeToPdf(@RequestPart("files") MultipartFile htmlFile) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentDispositionFormData("attachment", "output.pdf");
        return new ResponseEntity<>(pdfConverterService.convertOfficeToPdfExperiment(htmlFile), headers, HttpStatus.OK);
    }
}

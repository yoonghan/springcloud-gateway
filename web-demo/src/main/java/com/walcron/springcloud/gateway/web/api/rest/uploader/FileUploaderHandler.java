package com.walcron.springcloud.gateway.web.api.rest.uploader;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

@RestController
public class FileUploaderHandler {
    private final StorageService storageService;

    @Autowired
    public FileUploaderHandler(StorageService storageService) {
        this.storageService = storageService;
    }

    private List<String> processAndGetLinesAsList(String string) {
        return new ArrayList<>();
    }

    @PostMapping(value="/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Mono<Message> upload(@RequestPart("files") Flux<FilePart> filePartFlux) {
        Mono<List<String>> list =  handleFileUpload(filePartFlux).collectList();
        return Mono.just(new Message("ok"));
    }


    public Flux<String> handleFileUpload(Flux<FilePart> filePartFlux) {
         return filePartFlux.flatMap(filePart ->
             filePart.content().map(dataBuffer -> {
                 storageService.store(dataBuffer);
                 return new String("HI");
             })
         );
    }
//
//    @ExceptionHandler(StorageFileNotFoundException.class)
//    public ResponseEntity<?> handleStorageFileNotFound(StorageFileNotFoundException exc) {
//        return ResponseEntity.notFound().build();
//    }
}

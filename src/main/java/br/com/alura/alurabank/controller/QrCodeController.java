package br.com.alura.alurabank.controller;

import br.com.alura.alurabank.service.QRCodeService;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

@RestController
@RequestMapping("/qr-code")
public class QrCodeController {

    private final QRCodeService service;

    public QrCodeController(QRCodeService service) {
        this.service = service;
    }

    @GetMapping("{id}")
    public ResponseEntity<?> generate(@PathVariable String id) throws IOException {
        var image = service.generateQRCodeByHash(id);


        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_PNG)
                .body(image);
    }
}

package br.com.alura.alurabank.service;

import br.com.alura.alurabank.repositorio.TransferenciaIntentRepository;
import net.glxn.qrgen.core.image.ImageType;
import net.glxn.qrgen.javase.QRCode;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;

@Service
public class QRCodeService {

    private final TransferenciaIntentRepository repository;

    public QRCodeService(TransferenciaIntentRepository repository) {
        this.repository = repository;
    }

    public BufferedImage generateQRCodeByHash(String hash) throws IOException {

        var intent = repository.findByHash(hash).orElseThrow();

        var qrCode = QRCode.from("/pagar/" + intent.getId())
                .to(ImageType.PNG)
                .withSize(450, 450)
                .stream();

        var inputStream = new ByteArrayInputStream(qrCode.toByteArray());

        return ImageIO.read(inputStream);
    }
}

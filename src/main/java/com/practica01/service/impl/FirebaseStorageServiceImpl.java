package com.practica01.service.impl;

import com.google.auth.Credentials;
import com.google.auth.ServiceAccountSigner;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.*;
import com.practica01.service.FirebaseStorageService;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.util.concurrent.TimeUnit;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FirebaseStorageServiceImpl implements FirebaseStorageService {

    @Override
    public String cargaImagen(MultipartFile archivoLocalCliente, String carpeta, Long id) {
        try {
            // Validar que el archivo no esté vacío
            if (archivoLocalCliente == null || archivoLocalCliente.isEmpty()) {
                throw new IOException("El archivo esta vacío o no fue recibido correctamente.");
            }

            // Obtener la extensión del archivo
            String originalFilename = archivoLocalCliente.getOriginalFilename();
            String extension = (originalFilename != null && originalFilename.contains("."))
                    ? originalFilename.substring(originalFilename.lastIndexOf(".")) : ".tmp";

            // Generar un nombre de archivo único
            String fileName = "img" + sacaNumero(id) + extension;

            // Convertir MultipartFile a archivo temporal
            File file = this.convertToFile(archivoLocalCliente);

            // Subir archivo a Firebase y obtener la URL
            String URL = this.uploadFile(file, carpeta, fileName);

            // Eliminar archivo temporal
            file.delete();

            return URL;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private String uploadFile(File file, String carpeta, String fileName) throws IOException {

        // Verificar que el archivo existe
        if (!file.exists()) {
            throw new IOException("El archivo temporal no existe.");
        }

        // Verificar que carpeta no sea nula
        if (carpeta == null || carpeta.trim().isEmpty()) {
            carpeta = "default_folder";
        }

        // Verificar que fileName no sea nulo
        if (fileName == null || fileName.trim().isEmpty()) {
            throw new IOException("El Nombre del archivo no puede estar vacío.");
        }

        // Verificar credenciales
        ClassPathResource json = new ClassPathResource("firebase/techshop-3db54-firebase-adminsdk-fbsvc-7cc4030d6a.json");
        if (!json.exists()) {
            throw new IOException("No se encontró el archivo JSON de credenciales.");
        }

        Credentials credentials = GoogleCredentials.fromStream(json.getInputStream());

        // Crear servicio de almacenamiento
        Storage storage = StorageOptions.newBuilder()
                .setCredentials(credentials)
                .setProjectId("techshop-3db54")
                .build()
                .getService();

        // Crear el BlobId
        BlobId blobId = BlobId.of(FirebaseStorageService.BUCKET_NAME, carpeta + "/" + fileName);
        BlobInfo blobInfo = BlobInfo.newBuilder(blobId).setContentType("media").build();

        // Subir archivo a Firebase
        storage.create(blobInfo, Files.readAllBytes(file.toPath()));

        // Generar URL de acceso
        String url = storage.signUrl(blobInfo, 3650, TimeUnit.DAYS, Storage.SignUrlOption.signWith((ServiceAccountSigner) credentials)).toString();

        return url;
    }

    private File convertToFile(MultipartFile archivoLocalCliente) throws IOException {
        // Obtener extensión del archivo
        String originalFilename = archivoLocalCliente.getOriginalFilename();
        String extension = (originalFilename != null && originalFilename.contains("."))
                ? originalFilename.substring(originalFilename.lastIndexOf(".")) : ".tmp";

        // Crear archivo temporal con la extensión correcta
        File tempFile = File.createTempFile("img", extension);

        // Escribir los bytes en el archivo temporal
        try (FileOutputStream fos = new FileOutputStream(tempFile)) {
            fos.write(archivoLocalCliente.getBytes());
        }

        return tempFile;
    }

    private String sacaNumero(long id) {
        return String.format("%019d", id);
    }
}

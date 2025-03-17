/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */

/**
 *
 * @author josed
 */

package com.practica01.service;

import org.springframework.web.multipart.MultipartFile;

public interface FirebaseStorageService {

    /**
     * Sube un archivo a Firebase Storage y devuelve la URL de acceso.
     * 
     * @param archivoLocalCliente Archivo recibido desde el cliente.
     * @param carpeta Carpeta dentro del bucket donde se almacenará el archivo.
     * @param id Identificador único del archivo.
     * @return URL de acceso al archivo subido.
     */
    String cargaImagen(MultipartFile archivoLocalCliente, String carpeta, Long id);

    String BUCKET_NAME = "techshop-3db54.appspot.com";
    String RUTA_SUPERIOR_STORAGE = "techshopPractica#3";

    String RUTA_JSON_FILE = "firebase/";
    String ARCHIVO_JSON_FILE = "techshop-3db54-firebase-adminsdk-fbsvc-7cc4030d6a.json";
}


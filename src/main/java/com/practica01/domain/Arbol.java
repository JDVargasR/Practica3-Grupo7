/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.practica01.domain;

/**
 *
 * @author josed
 */

import lombok.Data;
import jakarta.persistence.*;
import java.io.Serializable;


@Data
@Entity
@Table(name = "Arbol")
public class Arbol implements Serializable {
    
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_arbol")
    private Long idArbol;
    
    @Column(name = "tipo_arbol")
    private String tipoArbol;
    
    @Column(name = "tipo_flora")
    private String tipoFlora;

    private String descripcion;
    
    private double altura;
    
    private int dureza;

    @Column(name = "ruta_imagen")
    private String rutaImagen;

    private boolean activo;

    public Arbol() {
        
    }
    
    public Arbol(String tipo_arbol, String tipo_flora, String descripcion, double altura, int dureza, boolean activo) {
        this.tipoArbol = tipo_arbol;
        this.tipoFlora = tipo_flora;
        this.descripcion = descripcion;
        this.altura = altura;
        this.dureza = dureza;
        this.activo = activo;
    }
}

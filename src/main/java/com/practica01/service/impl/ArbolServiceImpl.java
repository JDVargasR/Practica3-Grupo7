/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.practica01.service.impl;

import com.practica01.dao.ArbolDao;
import com.practica01.domain.Arbol;
import com.practica01.service.ArbolService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author josed
 */

@Service
public class ArbolServiceImpl implements ArbolService{

    @Autowired
    private ArbolDao arbolDao;

    @Override
    @Transactional(readOnly = true)
    public List<Arbol> getArboles(boolean activos) {
        var lista = arbolDao.findAll();
        if (activos) {
            lista.removeIf(e -> !e.isActivo());
        }
        return lista;
    }

    @Override
    @Transactional(readOnly = true)
    public Arbol getArbol(Arbol arbol) {
        return  arbolDao.findById(arbol.getIdArbol()).orElse(null);
    }

    @Override
    @Transactional
    public void save(Arbol arbol) {
        arbolDao.save(arbol);
    }

    @Override
    @Transactional
    public void delete(Arbol arbol) {
        arbolDao.delete(arbol);
    }
}

package com.mitocode.service;

import com.mitocode.model.SignoVital;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ISignoVitalService extends ICRUD<SignoVital, Integer>{

    Page<SignoVital> listarPageable(Pageable page);
}


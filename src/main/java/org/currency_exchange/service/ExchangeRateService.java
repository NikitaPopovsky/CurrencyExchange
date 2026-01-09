package org.currency_exchange.service;

import org.currency_exchange.db.ExchangeRateDAO;
import org.currency_exchange.dto.ExchangeRateDTO;
import org.currency_exchange.mapper.ExchangeRateServiceMapper;
import org.currency_exchange.model.ExchangeRate;

import java.util.List;

public class ExchangeRateService {
    private final ExchangeRateDAO dao;
    private final ExchangeRateServiceMapper mapper;

    public ExchangeRateService() {
        this.dao = new ExchangeRateDAO();
        this.mapper = new ExchangeRateServiceMapper();
    }

    public List<ExchangeRateDTO> getAll() {
        List<ExchangeRate> exchangeRates = dao.findAll();
        return mapper.toDtoList(exchangeRates);
    }


}

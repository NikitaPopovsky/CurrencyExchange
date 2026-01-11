package org.currency_exchange.service;

import org.currency_exchange.db.CurrencyDAO;
import org.currency_exchange.db.ExchangeRateDAO;
import org.currency_exchange.dto.ExchangeRateDTO;
import org.currency_exchange.dto.ExchangeRateRequestDTO;
import org.currency_exchange.exception.CodeIsMissing;
import org.currency_exchange.exception.ModelNotFoundException;
import org.currency_exchange.exception.ObjectAlreadyExists;
import org.currency_exchange.mapper.ExchangeRateServiceMapper;
import org.currency_exchange.model.Currency;
import org.currency_exchange.model.ExchangeRate;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public class ExchangeRateService {
    private final ExchangeRateDAO dao;
    private final ExchangeRateServiceMapper mapper;
    private final CurrencyDAO daoCurrency;

    public ExchangeRateService() {
        this.dao = new ExchangeRateDAO();
        this.mapper = new ExchangeRateServiceMapper();
        this.daoCurrency = new CurrencyDAO();
    }

    public List<ExchangeRateDTO> getAll() {
        List<ExchangeRate> exchangeRates = dao.findAll();
        return mapper.toDtoList(exchangeRates);
    }

    public ExchangeRateDTO getByPairCode (String pairCode) {
        ExchangeRate exchangeRate = getModelByPairCode(pairCode);

        return mapper.toDTO(exchangeRate);
    }

    public void create (ExchangeRateRequestDTO exchangeRateRequestDTO) {
        String baseCurrencyCode = exchangeRateRequestDTO.baseCurrencyCode();
        String targetCurrencyCode = exchangeRateRequestDTO.targetCurrencyCode();

        Optional <ExchangeRate> exchangeRateOptional = dao.findByPairCodeCurrency(baseCurrencyCode, targetCurrencyCode);

        if (exchangeRateOptional.isPresent()) {
            throw new ObjectAlreadyExists("Обменный курс с такими полями уже существует");
        }

        Optional <Currency> baseCurrencyOptional = daoCurrency.findByCode(baseCurrencyCode);
        Optional <Currency> targetCurrencyOptional = daoCurrency.findByCode(targetCurrencyCode);

        if (baseCurrencyOptional.isEmpty() || targetCurrencyOptional.isEmpty()) {
            throw new ModelNotFoundException("Одна (или обе) валюта из валютной пары не существует в БД");
        }

        dao.save(mapper.toModel(new ExchangeRateDTO(0,baseCurrencyOptional.get(),
                targetCurrencyOptional.get(), exchangeRateRequestDTO.rate())));
    }

    public void updateRate (String pairCode, BigDecimal rate) {
        ExchangeRate exchangeRate = getModelByPairCode(pairCode);

        dao.updateRate(exchangeRate, rate);
    }

    private ExchangeRate getModelByPairCode (String pairCode) {
        if (pairCode.isEmpty()) {
            throw new CodeIsMissing("Не указаны коды валют");
        }

        String baseCurrencyCode = pairCode.substring(0,3);
        String targetCurrencyCode = pairCode.substring(0,6);

        Optional<ExchangeRate> exchangeRateOptional = dao.findByPairCodeCurrency(baseCurrencyCode, targetCurrencyCode);

        if(exchangeRateOptional.isEmpty()) {
            throw new ModelNotFoundException("Обменный курс не найден");
        }

        return exchangeRateOptional.get();
    }




}

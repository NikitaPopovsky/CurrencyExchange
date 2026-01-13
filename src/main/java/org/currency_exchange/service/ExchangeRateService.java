package org.currency_exchange.service;

import org.currency_exchange.db.CurrencyDAO;
import org.currency_exchange.db.ExchangeRateDAO;
import org.currency_exchange.dto.ExchangeDTO;
import org.currency_exchange.dto.ExchangeRateDTO;
import org.currency_exchange.dto.ExchangeRateRequestDTO;
import org.currency_exchange.exception.CodeIsMissing;
import org.currency_exchange.exception.ModelNotFoundException;
import org.currency_exchange.exception.ObjectAlreadyExists;
import org.currency_exchange.mapper.ExchangeRateServiceMapper;
import org.currency_exchange.model.Currency;
import org.currency_exchange.model.ExchangeRate;

import java.math.BigDecimal;
import java.util.HashMap;
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
        HashMap<String, String> codes = splitCodes(pairCode);
        ExchangeRate exchangeRate = getModelByCodes(codes);

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



        dao.save(new ExchangeRate(0,baseCurrencyOptional.get(),
                targetCurrencyOptional.get(), exchangeRateRequestDTO.rate()));
    }

    public void updateRate (String pairCode, BigDecimal rate) {
        HashMap<String, String> codes = splitCodes(pairCode);
        ExchangeRate exchangeRate = getModelByCodes(codes);

        dao.updateRate(exchangeRate, rate);
    }

    public ExchangeDTO exchange(String baseCurrencyCode , String targetCurrencyCode, BigDecimal amount) {

    }

    private ExchangeRate getModelByCodes (HashMap<String, String> codes) {

        Optional<ExchangeRate> exchangeRateOptional = dao.findByPairCodeCurrency(codes.get("baseCurrencyCode")
                , codes.get("targetCurrencyCode"));

        if(exchangeRateOptional.isEmpty()) {
            throw new ModelNotFoundException("Обменный курс не найден");
        }

        return exchangeRateOptional.get();
    }

    private HashMap<String, String> splitCodes(String pairCode) {
        if (pairCode.isEmpty()) {
            throw new CodeIsMissing("Не указаны коды валют");
        }

        HashMap<String, String> codes = new HashMap <String, String>();
        codes.put("baseCurrencyCode", pairCode.substring(0,3));
        codes.put("targetCurrencyCode", pairCode.substring(3,6));

        return codes;
    }


}

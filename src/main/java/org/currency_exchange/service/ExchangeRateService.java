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
import org.currency_exchange.model.Exchange;
import org.currency_exchange.model.ExchangeRate;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

public class ExchangeRateService {
    private final ExchangeRateDAO dao;
    private final ExchangeRateServiceMapper mapper;
    private final CurrencyService currencyService;

    public ExchangeRateService() {
        this.dao = new ExchangeRateDAO();
        this.mapper = new ExchangeRateServiceMapper();
        this.currencyService = new CurrencyService();
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

    public ExchangeRateDTO create (ExchangeRateRequestDTO exchangeRateRequestDTO) {
        Currency baseCurrency;
        Currency targetCurrency;

        String baseCurrencyCode = exchangeRateRequestDTO.baseCurrencyCode();
        String targetCurrencyCode = exchangeRateRequestDTO.targetCurrencyCode();

        Optional <ExchangeRate> exchangeRateOptional = dao.findByPairCodeCurrency(baseCurrencyCode, targetCurrencyCode);

        if (exchangeRateOptional.isPresent()) {
            throw new ObjectAlreadyExists("Обменный курс с такими полями уже существует");
        }

        try {
            baseCurrency = currencyService.getModelByCode(baseCurrencyCode);
            targetCurrency = currencyService.getModelByCode(targetCurrencyCode);
        } catch (RuntimeException e) {
            throw new ModelNotFoundException("Одна (или обе) валюта из валютной пары не существует в БД");
        }

        ExchangeRate exchangeRate = dao.save(new ExchangeRate(0,baseCurrency,
                targetCurrency, exchangeRateRequestDTO.rate()));

        return mapper.toDTO(exchangeRate);
    }

    public ExchangeRateDTO updateRate (String pairCode, BigDecimal rate) {
        HashMap<String, String> codes = splitCodes(pairCode);
        ExchangeRate exchangeRate = getModelByCodes(codes);

        exchangeRate = dao.updateRate(exchangeRate, rate);
        return mapper.toDTO(exchangeRate);

    }

    public ExchangeDTO exchange(String baseCurrencyCode , String targetCurrencyCode, BigDecimal amount) {

        ExchangeRate exchangeRate = findForExchange(baseCurrencyCode, targetCurrencyCode);

        return mapper.toExchangeDTO(exchangeRate, amount);
    }

    private ExchangeRate findForExchange(String baseCurrencyCode , String targetCurrencyCode) {
        Map<String, String> codes = new HashMap<>();

        try {
            codes.put("from", baseCurrencyCode);
            codes.put("to", targetCurrencyCode);

            return getModelByCodes(codes);
        } catch (ModelNotFoundException e) {
            //ничего не делаем
        }

        try {
            codes.put("from", targetCurrencyCode);
            codes.put("to", baseCurrencyCode);

            ExchangeRate exchangeRate = getModelByCodes(codes);
            BigDecimal rate = BigDecimal.ONE.divide(exchangeRate.rate(), 3 , RoundingMode.HALF_UP);

            return new ExchangeRate(0, exchangeRate.targetCurrency(), exchangeRate.baseCurrency(),  rate);
        } catch (ModelNotFoundException e) {
            //ничего не делаем
        }

        try {
            codes.put("from", "USD");
            codes.put("to", baseCurrencyCode);

            ExchangeRate exchangeRateUSDToBase = getModelByCodes(codes);

            codes.put("from", "USD");
            codes.put("to", targetCurrencyCode);

            ExchangeRate exchangeRateUSDToTarget = getModelByCodes(codes);

            BigDecimal rate = exchangeRateUSDToBase.rate()
                    .divide(exchangeRateUSDToTarget.rate(), 3, RoundingMode.HALF_UP);

            return new ExchangeRate(0, exchangeRateUSDToBase.targetCurrency(),
                    exchangeRateUSDToTarget.targetCurrency(),  rate);

        } catch (RuntimeException e) {
            throw e;
        }

    }

    private ExchangeRate getModelByCodes (Map<String, String> codes) throws ModelNotFoundException{
        Optional<ExchangeRate> exchangeRateOptional = dao.findByPairCodeCurrency(codes.get("from")
                , codes.get("to"));

        return exchangeRateOptional.orElseThrow(() -> new ModelNotFoundException("Обменный курс не найден"));
    }

    private HashMap<String, String> splitCodes(String pairCode) {
        if (pairCode.isEmpty()) {
            throw new CodeIsMissing("Не указаны коды валют");
        }

        HashMap<String, String> codes = new HashMap <String, String>();
        codes.put("from", pairCode.substring(0,3));
        codes.put("to", pairCode.substring(3,6));

        return codes;
    }


}

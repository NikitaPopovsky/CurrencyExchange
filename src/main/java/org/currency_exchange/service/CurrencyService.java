package org.currency_exchange.service;

import org.currency_exchange.db.CurrencyDAO;
import org.currency_exchange.dto.CurrencyDTO;
import org.currency_exchange.exception.CodeIsMissing;
import org.currency_exchange.exception.ModelNotFoundException;
import org.currency_exchange.exception.ObjectAlreadyExists;
import org.currency_exchange.mapper.CurrencyServiceMapper;
import org.currency_exchange.model.Currency;

import java.util.List;
import java.util.Optional;

public class CurrencyService {
    private final CurrencyDAO dao;
    private final CurrencyServiceMapper mapper;

    public CurrencyService() {
        this.dao = new CurrencyDAO();
        this.mapper = new CurrencyServiceMapper();
    }

    public List<CurrencyDTO> getAll() {
        List<Currency> currencies = dao.findAll();
        return mapper.toDtoList(currencies);
    }

    public CurrencyDTO getByCode(String code) {
        return mapper.toDTO(getModelByCode(code));
    }

    public void create (CurrencyDTO currencyDTO) {
        Optional <Currency>  currencyOptional = dao.findByCode(currencyDTO.code());

        if (currencyOptional.isPresent()) {
            throw new ObjectAlreadyExists("Валюта с такими полями уже существет");
        }

        dao.save(mapper.toModel(currencyDTO));
    }

    public Currency getModelByCode(String code) throws ModelNotFoundException {
        if (code.isEmpty()) {
            throw new CodeIsMissing("Не указан код валюты");
        }
        Optional <Currency> currencyOptional = dao.findByCode(code);

        return currencyOptional.orElseThrow(() -> new ModelNotFoundException("Валюта не найдена"));
    }
}

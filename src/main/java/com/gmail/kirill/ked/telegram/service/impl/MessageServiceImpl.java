package com.gmail.kirill.ked.telegram.service.impl;

import com.gmail.kirill.ked.telegram.repository.CityRepository;
import com.gmail.kirill.ked.telegram.repository.model.Attraction;
import com.gmail.kirill.ked.telegram.repository.model.City;
import com.gmail.kirill.ked.telegram.service.MessageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import static com.gmail.kirill.ked.telegram.web.tbot.constant.MessagesConstant.CITY_INFO;
import static com.gmail.kirill.ked.telegram.web.tbot.constant.MessagesConstant.CITY_NOT_FOUND;
import static com.gmail.kirill.ked.telegram.web.tbot.constant.MessagesConstant.CITY_WITH_MANY_ATTRACTION;
import static com.gmail.kirill.ked.telegram.web.tbot.constant.MessagesConstant.CITY_WITH_ONE_ATTRACTION;

@Service
public class MessageServiceImpl implements MessageService {
    private final static Logger logger = LoggerFactory.getLogger(MessageServiceImpl.class);
    private final int ATTRACTIONS_LIMIT_ONE = 1;
    private final int ATTRACTIONS_LIMIT_TWO = 2;
    private final CityRepository cityRepository;
    private final Random random;

    public MessageServiceImpl(CityRepository cityRepository) {
        this.cityRepository = cityRepository;
        random = new Random();
    }

    @Override
    @Transactional
    public String getInfoAboutCity(String messageText) {
        City city = cityRepository.getByName(messageText);
        if (city == null) {
            logger.info("no country found in database with name {}", messageText);
            return CITY_NOT_FOUND;
        }
        return createInfoMessage(city);
    }

    private String createInfoMessage(City city) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(String.format(CITY_INFO, city.getName()));
        if (city.getAttractions().size() >= ATTRACTIONS_LIMIT_TWO) {
            List<String> randomAttractions = getRandomNames(city.getAttractions().
                    stream()
                    .map(Attraction::getName)
                    .collect(Collectors.toList()));
            stringBuilder.append(String.format(CITY_WITH_MANY_ATTRACTION, randomAttractions.get(0), randomAttractions.get(1)));
            return stringBuilder.toString();
        } else if (city.getAttractions().size() == ATTRACTIONS_LIMIT_ONE) {
            stringBuilder.append(String.format(CITY_WITH_ONE_ATTRACTION, city.getAttractions().get(0).getName()));
            return stringBuilder.toString();
        }
        return stringBuilder.toString();
    }

    private List<String> getRandomNames(List<String> attractionsNames) {
        List<String> randomNames = new ArrayList<>();
        for (int i = 0; i < ATTRACTIONS_LIMIT_TWO; i++) {
            int randIndex = random.nextInt(attractionsNames.size());
            randomNames.add(attractionsNames.get(randIndex));
            attractionsNames.remove(randIndex);
        }
        return randomNames;
    }
}
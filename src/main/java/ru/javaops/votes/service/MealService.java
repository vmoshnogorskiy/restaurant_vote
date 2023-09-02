package ru.javaops.votes.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.javaops.votes.model.Meal;
import ru.javaops.votes.repository.MealRepository;
import ru.javaops.votes.repository.UserRepository;

@Service
@AllArgsConstructor
public class MealService {
    private final MealRepository mealRepository;
    private final UserRepository userRepository;

    @Transactional
    public Meal save(int userId, Meal meal) {
        meal.setUser(userRepository.getExisted(userId));
        return mealRepository.save(meal);
    }
}

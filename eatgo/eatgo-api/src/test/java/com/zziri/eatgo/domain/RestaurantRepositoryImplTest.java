package com.zziri.eatgo.domain;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class RestaurantRepositoryImplTest {

    @Test
    public void save(){
        //
        RestaurantRepositoryImpl repository = new RestaurantRepositoryImpl();

        int oldCount = repository.findAll().size();

        Restaurant restaurant=new Restaurant("BeRyoung", "Seoul");
        repository.save(restaurant);


        assertThat(restaurant.getId()).isEqualTo(1234L);
        int newCount = repository.findAll().size();

        assertThat(newCount - oldCount).isEqualTo(1);
    }

}
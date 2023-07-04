package com.example.cbm.Controller;

import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;



import java.io.Serializable;
import java.util.Random;



public class RandomStringGenerator implements IdentifierGenerator {



    @Override
    public Serializable generate(SharedSessionContractImplementor session, Object obj) {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder randomString = new StringBuilder();
        Random random = new Random();



        while (randomString.length() < 4) {
            int index = random.nextInt(characters.length());
            randomString.append(characters.charAt(index));
        }



        return randomString.toString();
    }
}

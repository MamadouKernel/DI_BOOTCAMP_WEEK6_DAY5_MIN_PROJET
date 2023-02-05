/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.exerciceninja.blackjack;

import java.util.*;
/**
 *
 * @author konate-mk
 */

public class Deck {
    List<Card> cards = new ArrayList<Card>();
    public Deck() {
        // Create an initial list of 52 cards (13 of each of the 4 types)
        this.setCards();
    }

    public void setCards() {
        this.cards.clear();
        String[] types = {"Heart", "Clover", "Spade", "Diamond"};
        Map<String, Integer> titleValueMap = new HashMap<String, Integer>(){{
            put("2", 2);
            put("3", 3);
            put("4", 4);
            put("5", 5);
            put("6", 6);
            put("7", 7);
            put("8", 8);
            put("9", 9);
            put("10", 10);
            put("Jack", 10);
            put("Queen", 10);
            put("King", 10);
            put("Ace", 11);
        }};
        for (int i = 0; i < types.length; i++) {
            int _i = i;
            titleValueMap.forEach((t, v) -> {
                Card card = new Card(v, types[_i], t);
                this.cards.add(card);
            });
        }
    }

    public List<Card> getHitCards(){
        return (cards.stream().filter(c -> c.isHit)).toList();
    }

    public List<Card> getRemainingCards() {
        return (cards.stream().filter(c -> !c.isHit)).toList();
    }

    public Card hit() {
        List<Card> remainingCards = getRemainingCards();
        Random rand = new Random();

        return remainingCards.size() == 0 // This should <never> happen
                ? null
                : remainingCards.get(rand.nextInt(0)).hit();
    }
}

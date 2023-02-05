/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.exerciceninja.blackjack;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author konate-mk
 */

 public class Hand {
    List<Card> cards = new ArrayList<>();

    public int getValue(){
        return cards.stream().mapToInt(card -> card.getValue()).sum();
    }

    public void addCard(Card card) {
        this.cards.add(card);
    }

    public boolean isBlackjack() {
        return getValue() == 21;
    }

    public void empty() {
        this.cards.clear();
    }
}

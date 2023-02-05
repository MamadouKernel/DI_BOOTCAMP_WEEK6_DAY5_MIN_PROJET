/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.exerciceninja.blackjack;

import java.util.Random;
import java.util.Scanner;

/**
 *
 * @author konate-mk
 */

public class Game {
    private Deck deck;
    private int balance;

    public Game() {
        super();
        this.deck = new Deck();
        this.balance = 100;
    }

    public void setBalance(int value) {
        this.balance = value;
    }

    public int getBalance() {
        return this.balance;
    }

    public static void main(String[] args) {
        Game game = new Game();
        Hand userHand = new Hand();
        Hand dealerHand = new Hand();
        System.out.println("=====================================");
        System.out.println("Welcome to the Game.");
        System.out.println("Your initial balance is :: $" + game.getBalance());
        System.out.println("\nReady to play? Let's go!!!");
        System.out.println("+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+\n");
        int betValue = 0;
        String userInput = "";
        Scanner sc = new Scanner(System.in);
        do {
            System.out.println("How much do you want to bet?");
            System.out.println("Enter a value between 1 and " + game.getBalance());
            System.out.println("*** Type 'Quit' to exit the game.");
            userInput = sc.nextLine();
            userInput = userInput.trim().toLowerCase();
            if (!userInput.equals("quit")) {
                try {
                    betValue = Integer.valueOf(userInput);
                    if (betValue > game.getBalance())
                        throw new Exception("Bet value can not be greater than balance.");
                    else if (betValue < 1)
                        throw new Exception("Bet value can not be less than 1$.");
                    boolean userWins = game.play(userHand, dealerHand);
                    if (userWins) {
                        System.out.println("***********************************");
                        System.out.println("\t You WIN !");
                    } else {
                        System.out.println("-----------------------------------");
                        System.out.println("\t You LOSE");
                    }
                    System.out.println("\t You: " + userHand.getValue());
                    userHand.cards.forEach(c -> System.out.println("[+] " + c.getTitle() + " of " + c.getType() + " = " + c.getValue()));
                    System.out.println("\t Dealer: " + dealerHand.getValue());
                    dealerHand.cards.forEach(c -> System.out.println("[+] " + c.getTitle() + " of " + c.getType() + " = " + c.getValue()));
                    game.setBalance(userWins ? game.getBalance() + betValue : game.getBalance() - betValue);
                    System.out.println();
                    System.out.println("~~~~~~~ Current balance: $" + game.getBalance() + " ~~~~~~~~");
                } catch (Exception e) {
                    System.out.println("Wrong input. " + e.getMessage());
                    System.out.println("Please enter an integer between 1 an " + game.getBalance());
                }
            } else break;
        } while (game.getBalance() >= 1);
        sc.close();

        System.out.println("############### THE END ################");
        System.out.println("Your final balance is :: $" + game.getBalance());
    }

    public boolean play(Hand userHand, Hand dealerHand) {
        // Clear all existing cards in the hand - in case it's not a fresh game
        userHand.empty();
        dealerHand.empty();
        this.deck.setCards();

        // Initial hands of 2 cards chosen randomly
        userHand.addCard(deck.hit());
        userHand.addCard(deck.hit());

        dealerHand.addCard(deck.hit());
        dealerHand.addCard(deck.hit());

        return this.verifyHands(userHand, dealerHand);
    }

    public boolean verifyHands(Hand userHand, Hand dealerHand) {
        if (dealerHand.isBlackjack() || userHand.getValue() > 21) {
            return false;
        } else if (userHand.isBlackjack() || dealerHand.getValue() > 21) {
            return true;
        } else {
            Random rand = new Random();
            System.out.println("You currently have a total value of " + userHand.getValue());
            System.out.println("Your cards are :");
            userHand.cards.forEach(c -> System.out.println(c.getTitle() + " of " + c.getType() + " = " + c.getValue()));
            Card oneCardFromDealerHand = dealerHand.cards.get(rand.nextInt(0));
            System.out.println("The dealer has one " + oneCardFromDealerHand.getTitle() + " of " + oneCardFromDealerHand.getType() + " = " + oneCardFromDealerHand.getValue());
            System.out.println("What next?");
            String userChoice = "";
            Scanner scanner = new Scanner(System.in);
            do {
                System.out.println("Type 'Hit' or 'Stand':");
                userChoice = scanner.nextLine();
                userChoice = userChoice.trim().toLowerCase();
                if (!(userChoice.equals("hit") || userChoice.equals("stand")))
                    System.out.println("Wrong input.");
            } while (!(userChoice.equals("hit") || userChoice.equals("stand")));
            // sc.close();
            if (userChoice.equals("hit")) {
                Card newCard = this.deck.hit();
                System.out.println("New card: " + newCard.getTitle() + " of " + newCard.getType() + " = " + newCard.getValue());
                userHand.addCard(newCard);
                return verifyHands(userHand, dealerHand);
            } else { // Stand
                if (dealerHand.getValue() <= 16) {
                    do {
                        System.out.println("It's up to the dealer to hit!");
                        System.out.println("...");
                        dealerHand.addCard(this.deck.hit());
                        System.out.println("The dealer has now a total value of " + dealerHand.getValue());
                        System.out.println("His cards are :");
                        dealerHand.cards.forEach(c -> System.out.println(c.getTitle() + " of " + c.getType() + " = " + c.getValue()));
                    } while (dealerHand.getValue() <= 16);
                }
                return dealerHand.getValue() > 21 || userHand.getValue() > dealerHand.getValue();
            }
        }
    }
}

package edu.augustana;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class HandleSearch {

    /*

    Here we build the card object,
    We make any sort of query we do it here,
    We need anytype of cards or list of cards we do it here.


     */


    private ArrayList<Card> allCards;

    private ArrayList<Card> filteredSearchBoxCards;
    private ArrayList<Card> filteredCheckBoxCards;




    public HandleSearch(CardListDB allCards) {

        this.allCards = allCards.getAllCards();
        filteredSearchBoxCards = new ArrayList<>();
        filteredCheckBoxCards = new ArrayList<>();

    }

    public ArrayList<Card> getAllCards() {
        return allCards;
    }


    public ArrayList<Card> textBoxSearch(String inputWord) {

        List<String> searchWordArray = Arrays.asList(inputWord.trim().split(" "));

        for (Card card : allCards) {
            for (String word : searchWordArray) {
                for (String data : card.getData()) {
                    if (data.toLowerCase().equals(word.toLowerCase())) {
                        filteredSearchBoxCards.add(card);
                        break;
                    }
                }
            }
        }

        return filteredSearchBoxCards;
    }


    public ArrayList<Card> getFilteredSearchBoxCards(){
        return filteredSearchBoxCards;
    }


    public ArrayList<Card> checkBoxSearch(String filterOne , String filterTwo){
        if (filteredSearchBoxCards.size() != 0) {
            System.out.println("This is running");
            for (Card card : filteredSearchBoxCards) {
                if (card.getGender().equals(filterOne) || card.getGender().equals(filterTwo)) {
                    filteredCheckBoxCards.add(card);
                }
            }

        } else {
            //get cards from the search view
            System.out.println("This is running else");

            for (Card card : allCards) {
                if (card.getGender().equals(filterOne) || card.getGender().equals(filterTwo)) {
                    filteredCheckBoxCards.add(card);
                }
            }
        }

        return filteredCheckBoxCards;

    }


    //Gives back the list of cards that has been the most recently searched if nothing then gives back all of the cards

    public ArrayList<Card> queryIfTextInBoxSearch(){

        if(filteredSearchBoxCards.isEmpty()){
            return allCards;
        }else{
            return filteredSearchBoxCards;
        }
    }


    public void clearSearchBoxFilter(){

        filteredSearchBoxCards.clear();

    }

    public void clearCheckBoxFilter(){

        filteredCheckBoxCards.clear();

    }






}

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
    private ArrayList<Card> allCardsExceptFavorites;
    private ArrayList<Card> filteredSearchBoxCards;
    private ArrayList<Card> filteredCheckBoxCards;
    private ArrayList<Card> favoriteCards;




    public HandleSearch(CardListDB allCards) {

        this.allCards = allCards.getAllCards();
        filteredSearchBoxCards = new ArrayList<>();
        filteredCheckBoxCards = new ArrayList<>();
        favoriteCards = allCards.getFavoriteCards();
        allCardsExceptFavorites = allCards.getAllCardsExceptfavorites();
    }
    public ArrayList<Card> getAllCards() {
        return allCards;
    }
    public ArrayList<Card> getAllCardsExceptFavorites(){
        return allCardsExceptFavorites;
    }
    public ArrayList<Card> getFavoriteCards(){
        return favoriteCards;
    }

    public ArrayList<Card> textBoxSearch(String inputWord) {

        List<String> searchWordArray = Arrays.asList(inputWord.trim().split(" "));

        int count = 0;
        for (Card card : allCards) {
            for (String word : searchWordArray) {
                count = 0;
                for (String data : card.getDirectStringMatchingData()) {
                    System.out.println("Dats stream: " + data);

                    if(inputWord.toLowerCase().equals("all")){
                        filteredSearchBoxCards.add(card);
                        break;
                    }
                        if (data.toLowerCase().equals(word.toLowerCase())) {
                            filteredSearchBoxCards.add(card);
                            break;
                        }

//                        if (count >= 6) {
//                            if (data.toLowerCase().contains(word.toLowerCase())) {
//                                filteredSearchBoxCards.add(card);
//                                break;
//                            }
//                        }
                    count++;
                }


                for (String listData : card.getListDataForSearching()) {
                    if (listData.toLowerCase().contains(word.toLowerCase())) {
                        filteredSearchBoxCards.add(card);
                        break;
                    }
                }
            }
        }

        System.out.println("Filter search box cards value: " + filteredCheckBoxCards.size());
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
                    if(card.getFavoriteStatus()){
                        favoriteCards.add(card);
                    }
                }
            }

        } else {
            //get cards from the search view
            System.out.println("This is running else");

            for (Card card : allCards) {
                if (card.getGender().equals(filterOne) || card.getGender().equals(filterTwo)) {
                    filteredCheckBoxCards.add(card);
                    if (card.getFavoriteStatus()){
                        favoriteCards.add(card);
                    }
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

    public void clearFavoriteCards(){
        favoriteCards.clear();
    }






}

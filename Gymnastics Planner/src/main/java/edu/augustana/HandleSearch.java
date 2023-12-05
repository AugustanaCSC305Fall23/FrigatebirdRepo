package edu.augustana;

import java.io.File;
import java.util.*;

public class HandleSearch {

    /*

    Here we build the card object,
    We make any sort of query we do it here,

     */

    private ArrayList<Card> allCards;
    private ArrayList<Card> allCardsExceptFavorites;
    private ArrayList<Card> filteredSearchBoxCards;
    private ArrayList<Card> filteredCheckBoxCards;
    private ArrayList<Card> favoriteCards;

    private HashSet<Card> overAllFlteredCardsWhichIsShown = new HashSet<>();



    public HandleSearch(CardListDB allCards) {

        this.allCards = allCards.getAllCards();
        filteredSearchBoxCards = new ArrayList<>();
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



    private void printInfo(){

        System.out.println("Overall filterdCards: " + overAllFlteredCardsWhichIsShown.size());
        System.out.println("filteredGender filtered card: " + filteredGender.size());
        System.out.println("filteredModel filtered card: " + filteredModel.size());
        System.out.println("filteredModel filtered card: " + filteredLevel.size());
        System.out.println("filteredEquipment filtered card: " + filteredEquipment.size());
        System.out.println("filteredSearchBoxCards filtered card: " + filteredSearchBoxCards.size());

    }
    public ArrayList<Card> buildOverAllFilterCard(){

        overAllFlteredCardsWhichIsShown.clear();
        printInfo();

        ArrayList<ArrayList<Card>> allFilters = new ArrayList<>(Arrays.asList(filteredGender, filteredModel, filteredLevel,
                filteredEquipment, filteredSearchBoxCards));

        Set<Card> intersection = new HashSet<>(allCards);

        Boolean everythingIsEmpty = true;
        for (int i = 0; i < allFilters.size(); i++) {
            if (allFilters.get(i).size() != 0) {
                everythingIsEmpty = false;
                intersection.retainAll(new HashSet<>(allFilters.get(i)));
               // System.out.println("Something is being retained: " + intersection.size());
            }
        }

        if(everythingIsEmpty){
            return new ArrayList<>();
        }

// Now, 'intersection' contains the common elements among all filters


        overAllFlteredCardsWhichIsShown.addAll(intersection);

        System.out.println("After filtering the cards: " + overAllFlteredCardsWhichIsShown.size());

        return new ArrayList<>(overAllFlteredCardsWhichIsShown);
    }




    public ArrayList<Card> textBoxSearch(String inputWord) {
            filteredSearchBoxCards.clear();

            if(inputWord.equals(" ")){
                filteredSearchBoxCards.addAll(allCards);
                ArrayList<Card> overallCardArray  = buildOverAllFilterCard();
                return overallCardArray;
            }
            List<String> searchWordArray = Arrays.asList(inputWord.trim().split(" "));

        for (Card card : allCards) {
                for (String word : searchWordArray) {
                    Boolean searchWordFoundDuringTextMatching = false;
                    //We see if the word is directly string matched
                    for (String data : card.getDirectStringMatchingData()) {


                        if (inputWord.toLowerCase().equals("all")) {
                            filteredSearchBoxCards.add(card);
                            searchWordFoundDuringTextMatching = true;
                            break;
                        }

                        if (data.toLowerCase().equals(word.toLowerCase())) {
                            filteredSearchBoxCards.add(card);
                            searchWordFoundDuringTextMatching = true;
                            break;
                        }
                    }

                    if (!searchWordFoundDuringTextMatching) {
                        for (String listData : card.getListDataForSearching()) {
                            if (listData.toLowerCase().contains(word.toLowerCase())) {
                                System.out.println(card);
                                filteredSearchBoxCards.add(card);
                                break;
                            }
                        }
                    }
                }
        }

        ArrayList<Card> overallCardArray  = buildOverAllFilterCard();
        return overallCardArray;

    }

    ArrayList<Card> filteredGender = new ArrayList<>();

    public ArrayList<Card> filterGender(String gender){
        filteredGender.clear();

        System.out.println("Gender String in filter function " + gender);

        if(gender.equals("All")){
            filteredGender.addAll(allCards);
            ArrayList<Card> overallCardArray  = buildOverAllFilterCard();
            return overallCardArray;
        }

        for (Card card : allCards) {

            if (card.getGender().toLowerCase().equals(gender.toLowerCase()) || card.getGender().toLowerCase().equals("n")){
                filteredGender.add(card);
            }

        }
        ArrayList<Card> overallCardArray  = buildOverAllFilterCard();
        return overallCardArray;
    }


    ArrayList<Card> filteredModel = new ArrayList<>();

    public ArrayList<Card> filterModel(String sex){
        filteredModel.clear();

        if(sex.equals("All")){
            filteredModel.addAll(allCards);
            ArrayList<Card> overallCardArray  = buildOverAllFilterCard();
            return overallCardArray;        }


        for (Card card : allCards) {

            if (card.getSex().toLowerCase().equals(sex.toLowerCase()) || card.getSex().toLowerCase().equals("n")){
                filteredModel.add(card);
            }
        }

        ArrayList<Card> overallCardArray  = buildOverAllFilterCard();
        return overallCardArray;
    }

    ArrayList<Card> filteredEquipment = new ArrayList<>();
    public ArrayList<Card> filterByEquipment(String equipment){
        filteredEquipment.clear();

        if(equipment.equals("All")){
            filteredEquipment.addAll(allCards);
            ArrayList<Card> overallCardArray  = buildOverAllFilterCard();
            return overallCardArray;        }

        for (Card cards : allCards) {
            String[] listOfEquipments = cards.getEquipment().split(",");

            for(String eqpInList : listOfEquipments){
                if(eqpInList.equals(equipment)){
                    filteredEquipment.add(cards);
                    break;
                }
            }
        }
        ArrayList<Card> overallCardArray  = buildOverAllFilterCard();
        return overallCardArray;

    }

    ArrayList<Card> filteredLevel = new ArrayList<>();
    public ArrayList<Card> filteredLevel(String level){

        filteredLevel.clear();

        if(level.equals("All")){
            filteredLevel.addAll(allCards);
            ArrayList<Card> overallCardArray  = buildOverAllFilterCard();
            return overallCardArray;
        }


        for (Card cards : allCards) {

            String[] listOfLevels= cards.getLevel().split(" ");
            for(String levelInList : listOfLevels){
                if(levelInList.equals(level)){
                    filteredLevel.add(cards);
                    break;
                }
            }
        }

        ArrayList<Card> overallCardArray  = buildOverAllFilterCard();
        return overallCardArray;
    }


    public ArrayList<Card> getFilteredSearchBoxCards(){
        return filteredSearchBoxCards;
    }


    public ArrayList<Card> checkBoxSearch(String filterOne , String filterTwo){
        if (filteredSearchBoxCards.size() != 0) {
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

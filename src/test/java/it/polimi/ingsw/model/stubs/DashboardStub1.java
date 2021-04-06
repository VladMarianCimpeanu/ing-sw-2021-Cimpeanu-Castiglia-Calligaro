package it.polimi.ingsw.model.stubs;

import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.benefit.Resource;

import java.util.ArrayList;
import java.util.Map;

public class DashboardStub1 extends Dashboard {
    private WarehouseDepotStub1 depot;
    private int testcardsNumber;
    private int testcardsLevel;
    private Color testcolor;
    private int testresourceNumber;
    private Resource testresource;
    private Discount discount;
    private ExtraProduction extraProduction;
    private boolean resSatisfy;
    private int cardAdded;

    public DashboardStub1(){
        super(null, null);
        testcardsNumber = 0;
        testcardsLevel = 0;
        testcolor = null;
        testresourceNumber = 0;
        testresource = null;
        discount = null;
        extraProduction = null;
        depot = new WarehouseDepotStub1();
        resSatisfy = true;
        cardAdded = 0;
    }

    public DashboardStub1(boolean resSatisfy) {
        super(null, null);
        testcardsNumber = 0;
        testcardsLevel = 0;
        testcolor = null;
        testresourceNumber = 0;
        testresource = null;
        discount = null;
        extraProduction = null;
        depot = new WarehouseDepotStub1();
        this.resSatisfy = resSatisfy;
        cardAdded = 0;
    }
    @Override
    public boolean checkDevRequirement(int cardsNumber, int cardsLevel, Color color) {
        testcardsNumber = cardsNumber;
        testcardsLevel = cardsLevel;
        testcolor = color;
        return true;
    }

    @Override
    public boolean checkResRequirement(int resourceNumber, Resource resource){
        testresourceNumber = resourceNumber;
        testresource = resource;
        return true;
    }
    @Override
    public void addDiscount(Discount discount){
        this.discount = discount;
    }

    public Discount getDiscount() {
        return discount;
    }
    @Override
    public void addExtraProduction(ExtraProduction extraProd) {
        extraProduction = extraProd;
    }

    public ExtraProduction getExtraProduction() {
        return extraProduction;
    }

    public int getTestresourceNumber() {
        return testresourceNumber;
    }

    public int getTestcardsLevel() {
        return testcardsLevel;
    }

    public Color getTestcolor() {
        return testcolor;
    }

    public int getTestcardsNumber() {
        return testcardsNumber;
    }

    public Resource getTestresource() {
        return testresource;
    }

    @Override
    public WarehouseDepot getWarehouseDepot(){
        return depot;
    }

    @Override
    public boolean checkResources(Map<Resource,Integer> resources){
        return resSatisfy;
    }

    @Override
    public void addDevelopmentCard(DevelopmentCard cardToAdd, int deckPosition, ArrayList<Discount> discounts){
        cardAdded = 1;
    }

    public int getCardAdded() {
        return cardAdded;
    }
}


package com.mohit.restaurant;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CheapestRestaurant {
	
	private static int orderedItemsSize;
	private static double minPrice;
	private static int restaurantID;
	private static List<MenuItem> completeMenuItemListFromCsv;
	private static Map<Integer, List<MenuItem>> orderedItemsRestaurantIdsMap;
	

	public static void main(String[] args) {		
		if (args.length < 2)
			System.out.println("Wrong input Format. \n Correct Format: java CheapestRestaurant item1 item2....");
		else {
			String path = args[0];
			completeMenuItemListFromCsv = csvParser(path);
			if (!completeMenuItemListFromCsv.isEmpty()) {
				List<String> orderedItems = Arrays.asList(args)
						.subList(1, args.length);
				cheapestPriceCalculator(orderedItems,completeMenuItemListFromCsv);
			} else
				System.out.println("Nil");
		}
	}
  
	/*
	 * Method to read the input csv and create an list containg MenuItem objects
	 */
	private static List<MenuItem> csvParser(String fileName) {
		List<MenuItem> itemList = new ArrayList<MenuItem>();
		BufferedReader reader = null;
		String line = "";
		final String TOKKEN = ",";

		try {
			reader = new BufferedReader(new FileReader(new File(fileName)));
			while ((line = reader.readLine()) != null) {
				String[] itemArray = line.split(TOKKEN);
				if (itemArray.length == 3)
					itemList.add(new MenuItem(itemArray[2].trim(), new Double(
							itemArray[1]), new Integer(itemArray[0])));
				else if (itemArray.length > 3) {
					StringBuilder itemComboName = new StringBuilder();
					for (int i = 2; i < itemArray.length; i++) {
						itemComboName.append(itemArray[i].trim()).append(" ");
					}
					itemList.add(new MenuItem(itemComboName.toString(), new Double(
							itemArray[1]), new Integer(itemArray[0])));
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return itemList;
	}
	
	/*
	 * Static method that takes input as ordered items list and calculates minimum price
	 */
	private static void cheapestPriceCalculator(List<String> orderedItems, List<MenuItem> completeMenuItemListFromCsv) {

		minPrice = Double.MAX_VALUE;
		restaurantID = -1;
		orderedItemsRestaurantIdsMap = new HashMap<Integer, List<MenuItem>>(); 
		orderedItemsSize = orderedItems.size();


		for (MenuItem currentMenuItem : completeMenuItemListFromCsv) {
			String label = currentMenuItem.getName();
			for (String eachItem : orderedItems) {
				if (Arrays.asList(label.split(" ")).contains(eachItem)) {
					if (orderedItemsRestaurantIdsMap.containsKey(currentMenuItem.getRestaurantID())) {
						List<MenuItem> savedItemList = orderedItemsRestaurantIdsMap.get(currentMenuItem.getRestaurantID());						
						List<MenuItem> tempList = new ArrayList<MenuItem>();
						for (MenuItem itemSaved : savedItemList) {
							makeCombo(itemSaved,currentMenuItem,eachItem,tempList);
						}

						if (tempList.size() > 0)
							savedItemList.addAll(tempList);

						if (!savedItemList.contains(currentMenuItem)) {
							if(!addItemToCurrMenuItem(currentMenuItem, eachItem))							
								savedItemList.add(currentMenuItem);							
						} else if (!currentMenuItem.getSetOfOrderedItemInCurrItem().contains(eachItem)) {
							addItemToCurrMenuItem(currentMenuItem, eachItem);
						}						
					} else {	
						
						if(!addItemToCurrMenuItem(currentMenuItem, eachItem)) {
							List<MenuItem> savedItemList = new ArrayList<MenuItem>();
							savedItemList.add(currentMenuItem);
							orderedItemsRestaurantIdsMap.put(currentMenuItem.getRestaurantID(), savedItemList);
						}						
					}
				}
			}
		}
		if (restaurantID == -1)
			System.out.println("Nil");
		else
			System.out.println(restaurantID + " " + minPrice);
	}
	
	
	/*
	 * This method will make the combination of current Menu item 
	 */
	private static void makeCombo(MenuItem itemSaved, MenuItem currentMenuItem,
			String labelToBuy, List<MenuItem> tempList) {
		if (!Arrays.asList(itemSaved.getName().split(" ")).contains(labelToBuy)) {

			MenuItem newCombo = new MenuItem(itemSaved.getName() + " " + currentMenuItem.getName(),
					itemSaved.getPrice() + currentMenuItem.getPrice(),currentMenuItem.getRestaurantID());
			newCombo.getSetOfOrderedItemInCurrItem().addAll(itemSaved.getSetOfOrderedItemInCurrItem());

			if(!addItemToCurrMenuItem(newCombo, labelToBuy))				
				tempList.add(newCombo);

		} else if (!itemSaved.getSetOfOrderedItemInCurrItem().contains(labelToBuy)) 
			addItemToCurrMenuItem(itemSaved, labelToBuy);				
	}

	/*
	 * This method adds Current ordered item to the list containing desired labels for the Current Menu item
	 * And check for the desired order items  size and minimum price. 
	 */
	private static boolean addItemToCurrMenuItem(MenuItem currentMenuItem, String labelToBuy) {		
		currentMenuItem.getSetOfOrderedItemInCurrItem().add(labelToBuy);
		return checkForLeastPrice(currentMenuItem);				
	}	
	
	 
	private static boolean checkForLeastPrice(MenuItem itemSaved) {
		if (itemSaved.getSetOfOrderedItemInCurrItem().size() == orderedItemsSize) {
			if (itemSaved.getPrice() < minPrice) {
				minPrice = itemSaved.getPrice();
				restaurantID = itemSaved.getRestaurantID();
			}
			return true;
		}
		return false;
	}


}


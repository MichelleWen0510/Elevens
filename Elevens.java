import java.util.*;
public class Elevens{
        
   /**
	 * The ranks of the cards for this game to be sent to the deck.
	 */
	private static final String[] RANKS =
		{"A", "2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K"};

	/**
	 * The suits of the cards for this game to be sent to the deck.
	 */
	private static final String[] SUITS =
		{"Sp", "He", "Di", "Cl"};

	/**
	 * The values of the cards for this game to be sent to the deck.
	 */
	private static final int[] POINT_VALUES =
		{1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 0, 0, 0};
      
  public static boolean isValid (Card [] player, String[] inputArr)
   {
       int[] arr = new int[inputArr.length];
       for(int i = 0; i < inputArr.length; i++) {
         arr[i] = Integer.parseInt(inputArr[i]);
       }
       for (int val: arr)
       {
         if (val < 0 || val > 8)
            return false;
       }
       if(arr.length == 2)
       {
         return (player[arr[0]].pointValue() + player[arr[1]].pointValue() == 11);
       }
       if(arr.length == 3)
       {
         ArrayList<String> faces = new ArrayList<>(Arrays.asList("J", "Q", "K"));
         for(int i = 0; i < arr.length; i++) {
           if(player[arr[i]].rank().equals("J")) {
             faces.remove("J");
           }
           if(player[arr[i]].rank().equals("Q")) {
             faces.remove("Q");
           }
           if(player[arr[i]].rank().equals("K")) {
             faces.remove("K");
           }
         }
         return faces.size() == 0;
       }
       return false;
     } 
    
   public static boolean hasCombinations(Card[] player)
   {
     ArrayList<String> faces = new ArrayList <> (Arrays.asList("J", "Q", "K"));
     for(int i = 0; i < 9; i++)
     {
       for(int j = i; j < 9; j++)
       {
         if(player[i].pointValue()+player[j].pointValue()==11)
         {
           return true;
         }
       }
       if(player[i].rank().equals("J")) {
          faces.remove("J");
        }
       if(player[i].rank().equals("Q")) {
          faces.remove("Q");
        }
       if(player[i].rank().equals("K")) {
          faces.remove("K");
        }
     }
     //System.out.println(faces);
     return faces.size() == 0;
   }

   public static void printHand(Card[] player) {
     System.out.print(" ");
     for (int i = 0; i < 9; i++)
       System.out.print(i+"     ");
     System.out.println();
     for (int i = 0; i < 9; i++)
       System.out.print(player[i] + ((i!=8) ? ", " : "\n"));
   }

   public static void main(String[] args)
   {
     //beginningDeck is the Deck we start with.  When we deal, it gets split into two 
     //Decks for each player 
     Deck beginningDeck = new Deck(RANKS,SUITS,POINT_VALUES);
     beginningDeck.shuffle();
     //System.out.println(beginningDeck);

     //There are nine cards on the table using an array
     Card[] cardsOnTable = new Card[9];    
     for (int i = 0; i < 9; i++)
     {
       cardsOnTable[i] = beginningDeck.deal();
     }
        
     Scanner in = new Scanner(System.in);
     //Make sure we get two or three entries
     int size = 0;
     String[] inAr={};

     while(hasCombinations(cardsOnTable)) {
      System.out.println("Cards left in deck: " + beginningDeck.size());
      printHand(cardsOnTable);

      do {
          System.out.print("Enter two or three cards to remove: ");
          inAr = in.nextLine().split("\\s+");  //Any number of white space
          size = inAr.length;
      } while(!isValid(cardsOnTable,inAr));
      
      cardsOnTable[Integer.parseInt(inAr[0])] = beginningDeck.deal();
      cardsOnTable[Integer.parseInt(inAr[1])] = beginningDeck.deal();
      if(inAr.length == 3) {
        cardsOnTable[Integer.parseInt(inAr[2])] = beginningDeck.deal();
      }
      System.out.println("----------------------");
     }

     
    System.out.println ("_________");
    printHand(cardsOnTable);
    if (cardsOnTable.length == 0) {
      System.out.println("You win");
    }
    else
    {
      System.out.println ("You Lose. There are no combinations left in your hand. (You still suck at card games)");
    }
  }     
}
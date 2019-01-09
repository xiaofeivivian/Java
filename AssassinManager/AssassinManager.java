// Xiaofei Wu
//
// This program implements an object which can model a game of assassin and can keep track of
// players. Each person inside the kill ring has his or her own target for assassination. Those
// who are killed will be place in the graveyard. The game will end as long as there is only
// one person left.
import java.util.*;

public class AssassinManager {
   // a list of kill ring for storing players who still alive
   private AssassinNode ring;
   // a list of graveyard for storing dead players
   private AssassinNode graveyard;
   
   // names: a list of players that will be stored in the kill ring and play game
   // pre: the list of players should not be empty (throws IllegalArgumentException if empty)
   // post: create an AssassinManager object
   public AssassinManager(List<String> names) {
      if (names.isEmpty()) {
         throw new IllegalArgumentException();
      }
      ring = null;
      graveyard = null;
      for (int i = names.size() - 1; i >= 0; i--) {
         ring = new AssassinNode(names.get(i), ring);
      }
   }
   
   // list: the list that needed to be examined if it contains the given name
   // name: the given name that needed to be checked if inside the current list
   // post: return if the given list contains the given name
   private boolean ifContains(AssassinNode list, String name) {
      AssassinNode current = list;
      while(current != null) {
         if (current.name.equalsIgnoreCase(name)) {
            return true;
         }
         current = current.next;
      }
      return false;
   }
   
   // post: print out the list of kill ring orderly
   public void printKillRing() {
      AssassinNode current = ring;
      while(current.next != null) {
         System.out.println("    " + current.name + " is stalking " + current.next.name);
         current = current.next;
      }
      System.out.println("    " + current.name + " is stalking " + ring.name);
   }
   
   // post: print out the list of graveyard and the killer of particular dead player if
   // the list is not empty
   public void printGraveyard() {
      AssassinNode current = graveyard;
      while(current != null) {
         System.out.println("    " + current.name + " was killed by " + current.killer);
         current = current.next;
      }
   }
   
   // name: the given name that needed to be checked if inside the kill ring
   // post: return if the kill ring contains the given name
   public boolean killRingContains(String name) {
      return ifContains(ring, name);
   }
   
   // name: the given name that needed to be checked if inside the graveyard
   // post: return if the graveyard contains the given name
   public boolean graveyardContains(String name) {
      return ifContains(graveyard, name);
   }
   
   // post: return if the game is over
   public boolean gameOver() {
      return (ring.next == null);
   }
   
   // post: return the name of winner if the game is over; otherwise return null
   public String winner() {
      if (gameOver()) {
         return ring.name;
      }
      return null;
   }
   
   // name: the name of player who will be assassinated
   // pre: the given name should be included in the kill ring (throws IllegalArgumentException
   // if not) and the game should not be over (throws IllegalStateException if game over)
   // post: kill the particular player in the kill ring and remove him or her to the graveyard
   public void kill(String name) {
      if (!killRingContains(name)) {
         throw new IllegalArgumentException();
      }
      if (gameOver()) {
         throw new IllegalStateException();
      }
      AssassinNode currentRing = ring;
      AssassinNode currentGraveyard = graveyard;
      if (currentRing.name.equalsIgnoreCase(name)) {
         ring = ring.next;
         graveyard = currentRing;
         graveyard.next = currentGraveyard;
         currentRing = ring;
         while (currentRing.next != null) {
            currentRing = currentRing.next;
         }
         graveyard.killer = currentRing.name;
      } else {
         while (currentRing.next != null && !currentRing.next.name.equalsIgnoreCase(name)) {
            currentRing = currentRing.next;
         }
         graveyard = currentRing.next;
         graveyard.killer = currentRing.name;
         currentRing.next = currentRing.next.next;
         graveyard.next = currentGraveyard;
      }
   }
}
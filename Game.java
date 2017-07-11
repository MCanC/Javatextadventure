/**
 *  This class is the main class of the "World of Zuul" application.
 *  "World of Zuul" is a very simple, text based adventure game.  Users
 *  can walk around some scenery. That's all. It should really be extended
 *  to make it more interesting!
 *
 *  To play this game, create an instance of this class and call the "play"
 *  method.
 *
 *  This main class creates and initializes all the others: it creates all
 *  rooms, creates the parser and starts the game.  It also evaluates and
 *  executes the commands that the parser returns.
 *
 * @author  Michael Kolling and David J. Barnes
 * @version 1.0 (February 2002)
 */

class Game
{
    private Parser parser;
    private Player player;
    private Item torch, key, potion, chest, cigar;
    private Inventory inv;

    /**
     * Create the game and initialize its internal map.
     */
    public Game()
    {
    	player = new Player();
    	createItems();
        createRooms();
        parser = new Parser();
    }

    /**
     * Create all the rooms and link their exits together.
     */
    private void createRooms()
    {
        Room outside, theatre, pub, lab, office, attic, secretroom, roof;

        // create the rooms
        outside = new Room("outside the main entrance of the an 'old mansion' ");
        theatre = new Room("in a lecture theatre. The slaves had to entertain their masters here");
        pub = new Room("in the pub, people used to drink here and have a fun time..");
        lab = new Room("in a lab where they used to do expirements on the slaves");
        office = new Room("in the office, this is where the slaves were shipped to");
        attic = new Room("in an attic with dead slave bodies");
        secretroom = new Room("in a secret room with a chest, could this be the treasure?");
        roof = new Room("on the roof of the building");


        // Initialize room exits
        outside.setExit("east", theatre);
        outside.setExit("south", lab);
        outside.setExit("west", pub);
        outside.addItem(torch);

        theatre.setExit("west", outside);
        theatre.addItem(potion);

        pub.setExit("east", outside);
        pub.setExit("up", attic);
        pub.addItem(potion);
        pub.addItem(cigar);
        
        attic.setExit("down", pub);
        attic.setExit("east", secretroom);
        
        secretroom.setExit("west", attic);
        secretroom.addItem(chest);
        

        lab.setExit("north", outside);
        lab.setExit("east", office);

        office.setExit("west", lab);
        office.setExit("up", roof);
        
        roof.setExit("down", office);
        roof.addItem(key);
        player.setCurrentRoom(outside);
    }
    
    private void createItems()
    {
    	torch = new Item("torch","You can use this torch to light up the area",3);
    	key = new Item("key","A key, maybe you'll need it",3);
    	potion = new Item("potion","A health potion, i'm sure you'll need that",3);
    	chest = new Item ("chest","A chest? I think you found the treasure!",3);
    	cigar = new Item("cigar","A cigar, Do you smoke?",1);
    }

    /**
     *  Main play routine.  Loops until end of play.
     */
    public void play()
    {
        printWelcome();

        // Enter the main command loop.  Here we repeatedly read commands and
        // execute them until the game is over.

        boolean finished = false;
        while (! finished) {
            Command command = parser.getCommand();
            finished = processCommand(command);
        }
        System.out.println("What happened? Did you just quit!?");
    }

    /**
     * Print out the opening message for the player.
     */
    private void printWelcome()
    {
        System.out.println();
        System.out.println("Welcome player, are you ready for an adventure");
        System.out.println("");
        System.out.println("Type 'help' if you need help (noob).");
        System.out.println();
        System.out.println(player.getCurrentRoom().getLongDescription());
    }

    /**
     * Given a command, process (that is: execute) the command.
     * If this command ends the game, true is returned, otherwise false is
     * returned.
     */
    private boolean processCommand(Command command)
    {
        boolean wantToQuit = false;

        if(command.isUnknown()) {
            System.out.println("That command doesnt exist man!");
            return false;
        }

        String commandWord = command.getCommandWord();
        if (commandWord.equals("help")){
            printHelp();
        }
        else if (commandWord.equals("go")){
            goToRoom(command);
        }
        else if (commandWord.equals("quit")){
            wantToQuit = quit(command);
        }
        else if (commandWord.equals("look")){
        	System.out.print(player.getCurrentRoom().getLongDescription());
        } 
        else if (commandWord.equals("inspect")){
        	System.out.println("You looked around and found a"); player.getCurrentRoom().showItems();
        	if (player.getCurrentRoom().isInventoryEmpty()) {
        		System.out.println("NOTHING, yes you found a nothing. Makes sense right?");
        	}
        }
        else if (commandWord.equals("drop")){
        	if (command.hasSecondWord()) {
        		Item item = player.takeItem(command.getSecondWord());
        		if (item != null) {
        			player.getCurrentRoom().addItem(item);
        			System.out.println("You dropped: "+ item.getName());
        		}else {
        			System.out.println("You don't have a(n)" +" "+ command.getSecondWord());
        		}
        	}
        }	
        else if (commandWord.equals("take")) {
        	if (command.hasSecondWord()) {
        		Item item = player.getCurrentRoom().takeItem(command.getSecondWord());
        		if (item != null) {
        			if(player.take(item)) {
        				System.out.println("You picked up: "+ item.getName());
        			}else {
        				 System.out.println("you are carrying too many items.");
        				 player.getCurrentRoom().addItem(item);
        			}
        			if(item.getName().equalsIgnoreCase("cigar")) {
        				player.damage(5);
        			}
        		}else {
        			System.out.println("The item you are looking for was not found heheheh");
        		}
        	}
        }
        	
        return wantToQuit;
    }

    // implementations of user commands:

    /**
     * Print out some help information.
     * Here we print some stupid, cryptic message and a list of the
     * command words.
     */
    private void printHelp()
    {
        System.out.println("Did you loose track of what you were doing?!?");
        System.out.println("Seriously get a hold of yourself");
        System.out.println();
        System.out.println("Here are some commands you can use:");
        parser.showCommands();
    }
    
    private void goToRoom(Command command) {
    	player.goRoom(command);
    }

    /**
     * Try to go to one direction. If there is an exit, enter the new
     * room, otherwise print an error message.
     */

    /**
     * "Quit" was entered. Check the rest of the command to see
     * whether we really quit the game. Return true, if this command
     * quits the game, false otherwise
     */
    private boolean quit(Command command)
    {
        if(command.hasSecondWord()) {
            System.out.println("Quit what?");
            return false;
        }
        else
            return true;  // signal that we want to quit
    }


    public static void main(String[] args)
    {
        Game game = new Game();
        game.play();
    }
}

package o1.adventure

import o1.*

/** The class `Adventure` represents text adventure games. An adventure consists of a player and
  * a number of areas that make up the game world. It provides methods for playing the game one
  * turn at a time and for checking the state of the game.
  *
  * N.B. This version of the class has a lot of “hard-coded” information that pertains to a very
  * specific adventure game that involves a small trip through a twisted forest. All newly created
  * instances of class `Adventure` are identical to each other. To create other kinds of adventure
  * games, you will need to modify or replace the source code of this class. */
class Adventure:

  /** the name of the game */
  val title = "Find Alko"
  //kaikki areat
  private val mainHall      = Area("Main hall", "You are in the main hall. There's too much going on here...\nBetter get moving before someone notices that you are drunk.")
  private val brokenElevator     = Area("Broken elevator", "This elevator is broken.", true)
  private val workingElevator = Area("Elevator", "Niiiice!!!\nThe elevator works now, but it might break soon. Use it carefully!")
  private val fifthFloor = Area("Hallway", "This is the fifth floor. There isn't that much going on here, but you see some a random dude holding a paper.\nType the following to take a look at it: [look]")
  private val securityRoom = Area("security room", "You enter the security room and see some guards playing Fifa.\nThey ask if you wan't to report anything, but since they notice that you are drunk they need some evidence.")
  private val secondFloorHall    = Area("2nd floor hall", "2nd floor")
  private val metroStation      = Area("Metro", "You arrive at the Kalasatama's metro station. You see a vending machine and a security room door.")
  private val store = Area("Store", "Store\nWoah there are so many tools here!\nUnfortunately you don't have enough money to buy them.\nYou could take a risk and steal a few of them while nobody is watching...")
  private val sixthFloor = Area("Sixth floor", "This is the sixth floor.\nYou see a camera on the ground. Pick it up and try using it!")
  private val someHallway = Area("Some hallway", "Ohhhh allthought you are quite drunk, you are pretty sure that the Alko is just around the corner.\nOnly problem is that there are some spurgus fighting in the middle of the hallway.\nMaybe someone should notify the guards...")
  private val almostDone = Area("Almost there", "The guards are furious! As soon as they see the photo, you and the guards get moving and break up the figth.\nNow your way is clear and you can continue walking towards Alko!")
  private val alko = Area("Alko", "alko")
  private val jail = Area("jail", "You must be quite drunk to fight the security guards....")
  private val destination = alko
  // alueitten naapurit
  mainHall       .setNeighbors(Vector("metro" -> metroStation,  "secondfloor" -> secondFloorHall))
  almostDone     .setNeighbors(Vector("alko" -> alko))
  someHallway    .setNeighbors(Vector("metro" -> metroStation, "sixthfloor" -> sixthFloor, "secret" -> almostDone))
  sixthFloor     .setNeighbors(Vector("hallway" -> someHallway, "elevator" -> workingElevator))
  fifthFloor     .setNeighbors(Vector("elevator" -> workingElevator))
  brokenElevator .setNeighbors(Vector("secondfloor" -> secondFloorHall, "secret" -> workingElevator))
  workingElevator.setNeighbors(Vector("fifthfloor" -> fifthFloor,  "sixthfloor" -> sixthFloor, "secondfloor" -> secondFloorHall))
  securityRoom   .setNeighbors(Vector("metro" -> metroStation, "the hallway where the spurgus were fighting" -> almostDone, "jail" -> jail))
  secondFloorHall.setNeighbors(Vector("elevator" -> brokenElevator, "mainhall" -> mainHall, "store" -> store))
  store          .setNeighbors(Vector("secondfloor" -> secondFloorHall))
  metroStation   .setNeighbors(Vector("security" -> securityRoom,   "mainhall" -> mainHall))


  store.addItem(Item("tools", "Looks like some old drunk could use these to fix an elevator."))
  sixthFloor.addItem(Item("camera", "It seems like it is just a normal polaroid camera."))

  /** The character that the player controls in the game. */
  val player = Player(mainHall)


  /** The number of turns that have passed since the start of the game. */
  var turnCount = 0
  /** The maximum number of turns that this adventure game allows before time runs out. */
  var timeLimit = 16


  /** Determines if the adventure is complete, that is, if the player has won. */
  def isComplete = this.player.location == alko

  /** Determines whether the player has won, lost, or quit, thereby ending the game. */
  def isOver = (this.isComplete) || (this.player.hasQuit) || (this.turnCount == this.timeLimit) || (this.drinkCounter == 8) || (this.player.location.name == "jail" )

  /** Returns a message that is to be displayed to the player at the beginning of the game. */
  def welcomeMessage = "Welcome my drunk friend. You have arrived at Redi and you are thirsty. Your mission is to find Alko from this maze before you alcohol wears off...\nYou have 1 beer, REMEMBER TO TAKE A SIP ONCE IN A WHILE.\nType [help] to see all actions.\nGood Luck!"


  /** Returns a message that is to be displayed to the player at the end of the game. The message
    * will be different depending on whether or not the player has completed their quest. */
  def goodbyeMessage =
    if this.isComplete then
      "Phewww. Almost didn't make it. Now go get more booze!"
    else if this.turnCount == this.timeLimit then
      "Oh no! Your alcohol levels are too low and you have become depressed.\nRemember to drink your beer!\nGame over!"
    else if this.drinkCounter == 8 then
      "You are way too wasted and you pass out.\nGame over!"
    else if this.player.location.name == "jail" then
      "Game over."
    else  // game over due to player quitting
      "I'm sorry but you are done here"

  var drinkCounter = 0
  /** Plays a turn by executing the given in-game command, such as “go west”. Returns a textual
    * report of what happened, or an error message if the command was unknown. In the latter
    * case, no turns elapse. */
  def playTurn(command: String) =
    val action = Action(command)
    val outcomeReport = action.execute(this.player)
    if outcomeReport.isDefined then
      if command == "drink" then
        this.timeLimit += 3
        this.drinkCounter += 1
      this.turnCount += 1
    outcomeReport.getOrElse(s"Unknown command: \"$command\".")

end Adventure


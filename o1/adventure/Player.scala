package o1.adventure

import scala.collection.mutable.Map

/** A `Player` object represents a player character controlled by the real-life user
  * of the program.
  *
  * A player object’s state is mutable: the player’s location and possessions can change,
  * for instance.
  *
  * @param startingArea  the player’s initial location */
class Player(startingArea: Area):

  private var currentLocation = startingArea        // gatherer: changes in relation to the previous location
  private var quitCommandGiven = false              // one-way flag
  /** Determines if the player has indicated a desire to quit the game. */
  def hasQuit = this.quitCommandGiven

  /** Returns the player’s current location. */
  def location = this.currentLocation


  /** Attempts to move the player in the given direction. This is successful if there
    * is an exit from the player’s current location towards the direction name. Returns
    * a description of the result: "You go DIRECTION." or "You can't go DIRECTION." */
  def go(direction: String) =
    val destination = this.location.neighbor(direction)
    this.currentLocation = destination.getOrElse(this.currentLocation)
    if destination.isDefined then
      if direction == "secret" then
        "You fix the elevator" + "."
      else if direction == "the hallway where the spurgus were fighting" then
        "You go back to the hallway"
      else
        "You go " + direction + "."
    else "You can't go " + direction + "."


  /** Causes the player to rest for a short while (this has no substantial effect in game terms).
    * Returns a description of what happened. */
  def rest() =
    "You rest for a while. Better get a move on, though."


  /** Signals that the player wants to quit the game. Returns a description of what happened within
    * the game as a result (which is the empty string, in this case). */
  def quit() =
    this.quitCommandGiven = true
    ""

  def drop(itemName: String): String =
    if this.personalItems.contains(itemName) then
      this.location.addItem(this.personalItems(itemName))
      this.personalItems -= itemName
      "You drop the " + itemName + "."
    else
      "You don't have that!"

  def look(): String =
    if currentLocation.name == "Hallway" then
      "fifthfloor               ???\n" +
        "    |                     |\n" +
        "elevator---sixthfloor---hallway\n" +
        "    |                     |    \n" +
        "2NDfloor---mainhall  ---metro\n" +
        "    |                     |\n" +
        "store                   securityroom"
    else
      "There's nothing to look at here"

  def examine(itemName: String): String =
    if this.has(itemName) then
      "You look closely at the " + this.personalItems(itemName).name + ".\n" + this.personalItems(itemName).description
    else
      "If you want to examine something, you need to pick it up first."

  def get(itemName: String): String =
    if this.location.contains(itemName) then
      this.personalItems += itemName -> this.location.removeItem(itemName).get
      "You pick up the " + itemName + "."
    else
      "There is no " + itemName + " here to pick up."

  def has(itemName: String): Boolean =
    this.personalItems.contains(itemName)

  private var personalItems = Map[String, Item]()

  def inventory: String =
    if this.personalItems.isEmpty then
      "You are empty-handed."
    else
      "You are carrying:" + "\n" +this.personalItems.keys.mkString("\n")

  var haveUsedCamera: Boolean = false

  def fight =
    if this.location.name == "Some hallway" then
      "You join the fight but you are so wasted that you cannot win it."
    else if this.location.name == "security room" then
      go("jail")
      "That was stupid"
    else
      "You cannot fight here."

  def use(itemName: String) =
    if this.has(itemName) then
      if itemName == "tools" && this.currentLocation.name == "Broken elevator" then
        go("secret")
      else if itemName == "tools" && this.currentLocation.name == "Some hallway" then
        go("supersecret")
      else if itemName == "camera" && this.currentLocation.name == "Some hallway" then
        haveUsedCamera = true
        "You took a picture of the fight."
      else if (itemName == "camera") && (this.currentLocation.name == "security room") && (haveUsedCamera == true) then
        go("the hallway where the spurgus were fighting")
      else if itemName == "camera" then
        "You took a selfie. You take a quick look at it and realise how drunk you are :D"
      else
        "You cannot use that item here"
    else
      "You cannot use an item that you don't have"

  /** Returns a brief description of the player’s state, for debugging purposes. */
  override def toString = "Now at: " + this.location.name

end Player


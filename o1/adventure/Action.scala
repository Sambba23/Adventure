package o1.adventure

/** The class `Action` represents actions that a player may take in a text adventure game.
  * `Action` objects are constructed on the basis of textual commands and are, in effect,
  * parsers for such commands. An action object is immutable after creation.
  * @param input  a textual in-game command such as “go east” or “rest” */
class Action(input: String):

  private val commandText = input.trim.toLowerCase
  private val verb        = commandText.takeWhile( _ != ' ' )
  private val modifiers   = commandText.drop(verb.length).trim

  /** Causes the given player to take the action represented by this object, assuming
    * that the command was understood. Returns a description of what happened as a result
    * of the action (such as “You go west.”). The description is returned in an `Option`
    * wrapper; if the command was not recognized, `None` is returned. */
  def execute(actor: Player) = this.verb match
    case "go"        => Some(actor.go(this.modifiers))
    case "drink"      => Some("You take a sip from your Lidl olut.")
    case "quit"      => Some(actor.quit())
    case "get"       => Some(actor.get(this.modifiers))
    case "examine"   => Some(actor.examine(this.modifiers))
    case "inventory" => Some(actor.inventory)
    case "use"       => Some(actor.use(this.modifiers))
    case "look"      => Some(actor.look())
    case "fight"     => Some(actor.fight)
    case "help"      => Some("Commands are:\ngo; followed by a direction displayed after (Exits available).\ndrink; you get 3 extra turns.\nget; followed by an item that is in the same area that you are in.\nexamine; followed by an item from your inventory.\ninventory; shows your intentory.\nfight; your character will try to fight. DO NOT FIGHT IN THE SECURITY ROOM!\nuse; followed by an item that you have in your inventory. Uses that item if it's possible." )
    case other       => None

  /** Returns a textual description of the action object, for debugging purposes. */
  override def toString = s"$verb (modifiers: $modifiers)"

end Action


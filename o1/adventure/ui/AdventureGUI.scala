package o1.adventure.ui

import scala.swing.*
import o1.*

import math.{max, min}
import scala.swing.event.*
import javax.swing.{ImageIcon, UIManager}
import o1.adventure.Adventure
import java.awt.{Dimension, Insets, Point}
import scala.language.adhocExtensions // enable extension of Swing classes

////////////////// NOTE TO STUDENTS //////////////////////////
// For the purposes of our course, it’s not necessary
// that you understand or even look at the code in this file.
//////////////////////////////////////////////////////////////

/** The singleton object `AdventureGUI` represents a GUI-based version of the Adventure
  * game application. The object serves as a possible entry point for the game app, and can
  * be run to start up a user interface that operates in a separate window. The GUI reads
  * its input from a text field and displays information about the game world in uneditable
  * text areas.
  *
  * **NOTE TO STUDENTS: In this course, you don’t need to understand how this object works
  * or can be used, apart from the fact that you can use this file to start the program.**
  *
  * @see [[AdventureTextUI]] */

object AdventureGUI extends SimpleSwingApplication:
  UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName)
  def top = new MainFrame:

    // Access to the application’s internal logic:
    val game = Adventure()
    val player = game.player

    //kuvat
    val mainhallPic = ImageIcon(this.getClass.getResource("redi-in-h-big.jpg"))
    val secondFloorPic = ImageIcon(this.getClass.getResource("2floor.jpg"))
    val storePic = ImageIcon(this.getClass.getResource("supermarket-employee-holds-thumbs-up-smiling-vegetable-crate-his-thumb-180376497.jpg"))
    val brokenPic = ImageIcon(this.getClass.getResource("beetleelevator.jpg"))
    val elevatorPic = ImageIcon(this.getClass.getResource("beetlethumbs.jpg"))
    val readingpic = ImageIcon(this.getClass.getResource("manreading.jpg"))
    val hallwayPic = ImageIcon(this.getClass.getResource("kaytava.jpg"))
    val fightPic = ImageIcon(this.getClass.getResource("fight.jpg"))
    val guardPic = ImageIcon(this.getClass.getResource("amongus.jpg"))
    val alkoPic = ImageIcon(this.getClass.getResource("alko.jpg"))
    val metroPic = ImageIcon(this.getClass.getResource("Kalasatama_metro_station_April_2022.jpg"))
    val securityroomPic = ImageIcon(this.getClass.getResource("securityroom.png"))
    val selfiePic = ImageIcon(this.getClass.getResource("E2u4GvPWYAAP84f.jpg"))
    val beetlefight = ImageIcon(this.getClass.getResource("fightingbeetle.jpg"))
    val drinkingBeetle = ImageIcon(this.getClass.getResource("drinkingbeetle.jpg"))
    val jailPic = ImageIcon(this.getClass.getResource("jail.jpg"))
    val piclabel= Label()
    // Components:

    val locationInfo = new TextArea(7, 80):
      editable = false
      wordWrap = true
      lineWrap = true
    val turnOutput = new TextArea(7, 80):
      editable = false
      wordWrap = true
      lineWrap = true
    val input = new TextField(40):
      minimumSize = preferredSize
    this.listenTo(input.keys)
    val turnCounter = Label()
    // Events:

    this.reactions += {
      case keyEvent: KeyPressed =>
        if keyEvent.source == this.input && keyEvent.key == Key.Enter && !this.game.isOver then
          val command = this.input.text.trim
          if command.nonEmpty then
            this.input.text = ""
            this.playTurn(command)
    }



    //vaihtaa kuvan locaation mukaan
    private def updatePic() =
        if this.player.location.name == "Metro" then
          this.piclabel.icon = metroPic
        else if this.player.location.name == "Main hall" then
          this.piclabel.icon = mainhallPic
        else if this.player.location.name == "2nd floor hall" then
          this.piclabel.icon = secondFloorPic
        else if this.player.location.name == "Store" then
          this.piclabel.icon = storePic
        else if this.player.location.name == "Broken elevator" then
          this.piclabel.icon = brokenPic
        else if this.player.location.name == "Elevator" then
          this.piclabel.icon = elevatorPic
        else if this.player.location.name == "Hallway" then
          this.piclabel.icon = readingpic
        else if this.player.location.name == "Sixth floor" then
          this.piclabel.icon = hallwayPic
        else if this.player.location.name == "Some hallway" then
          this.piclabel.icon = fightPic
        else if this.player.location.name == "security room" then
          this.piclabel.icon = securityroomPic
        else if this.player.location.name == "Almost there" then
          this.piclabel.icon = guardPic
        else if this.player.location.name == "jail" then
          this.piclabel.icon = jailPic
        else
          this.piclabel.icon = alkoPic




    // Layout:

    this.contents = new GridBagPanel:
      import scala.swing.GridBagPanel.Anchor.*
      import scala.swing.GridBagPanel.Fill
      layout += Label("Location:") -> Constraints(0, 0, 3, 3, 0, 1, NorthWest.id, Fill.None.id, Insets(8, 5, 5, 5), 0, 0)
      layout += Label("Command:")  -> Constraints(0, 1, 1, 1, 0, 0, NorthWest.id, Fill.None.id, Insets(8, 5, 5, 5), 0, 0)
      layout += Label("Events:")   -> Constraints(0, 2, 1, 1, 0, 0, NorthWest.id, Fill.None.id, Insets(8, 5, 5, 5), 0, 0)
      layout += turnCounter        -> Constraints(0, 3, 2, 1, 0, 0, NorthWest.id, Fill.None.id, Insets(8, 5, 5, 5), 0, 0)
      layout += locationInfo       -> Constraints(1, 0, 1, 1, 1, 1, NorthWest.id, Fill.Both.id, Insets(5, 5, 5, 5), 0, 0)
      layout += input              -> Constraints(1, 1, 1, 1, 1, 0, NorthWest.id, Fill.None.id, Insets(5, 5, 5, 5), 0, 0)
      layout += turnOutput         -> Constraints(1, 2, 1, 1, 1, 1, SouthWest.id, Fill.Both.id, Insets(5, 5, 5, 5), 0, 0)
      layout += piclabel           -> Constraints(1, 1, 1, 1, 1, 0, NorthWest.id, Fill.None.id, Insets(5, 5, 5, 5), 0, 0)
    // Menu:
    this.menuBar = new MenuBar:
      contents += new Menu("Program"):
        val quitAction = Action("Quit")( dispose() )
        contents += MenuItem(quitAction)


    // Set up the GUI’s initial state:
    this.title = game.title
    this.updateInfo(this.game.welcomeMessage)
    this.location = Point(50, 50)
    this.minimumSize = Dimension(200, 200)
    this.pack()
    this.input.requestFocusInWindow()


    def playTurn(command: String) =
      val turnReport = this.game.playTurn(command)
      if this.player.hasQuit then
        this.dispose()
      else
        if ((command == "use camera") && (this.player.has("camera"))) && ((this.player.location.name != "security room") && (this.player.location.name != "Some hallway")) then
          this.updateInfo(turnReport)
          this.input.enabled = !this.game.isOver
          this.piclabel.icon = selfiePic
        else if (command == "fight") && (this.player.location.name == "Some hallway") then
          this.updateInfo(turnReport)
          this.input.enabled = !this.game.isOver
          this.piclabel.icon = beetlefight
        else if command == "drink" then
          this.updateInfo(turnReport)
          this.input.enabled = !this.game.isOver
          this.piclabel.icon = drinkingBeetle
        else
          this.updateInfo(turnReport)
          this.input.enabled = !this.game.isOver




    def updateInfo(info: String) =
      if !this.game.isOver then
        this.turnOutput.text = info
      else
        this.turnOutput.text = info + "\n\n" + this.game.goodbyeMessage
      this.locationInfo.text = this.player.location.fullDescription
      this.turnCounter.text = "Turns played: " + this.game.turnCount
      this.updatePic()

  end top

  // Enable this code to work even under the -language:strictEquality compiler option:
  private given CanEqual[Component, Component] = CanEqual.derived
  private given CanEqual[Key.Value, Key.Value] = CanEqual.derived

end AdventureGUI


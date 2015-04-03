import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import processing.serial.*; 
import java.io.*; 
import java.util.*; 

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.File; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 

public class StringToSerial extends PApplet {

 //Import all required packages



PFont font;                // Saves font

int interval = 5;          // Time Interval for next update
int lastTime;              // Stores last update time

String saved="", typing="";// Store Saved string and live user input

Serial port;               // Save Serial port number

public void setup()
{
  size(640,480);           // Set display dimenions
  frameRate(10);           // Set Rate of change in display
  
  lastTime = 0;            // Initial value of last update
  
  font = loadFont("Gulim-40.vlw");  // Set font
  fill(255);               // Set text color
  textFont(font, 32);      // Set text font and size
  
  String arduPort = Serial.list()[0]; // Set port
  port = new Serial(this, arduPort, 9600); 
}

public void draw()
{
  background(246, 243, 241);      // Set Background color(rgb)
  int indent = 25;                // Set left indentation
  int n = (interval - ((millis()-lastTime)/1000)); //calculate time 
  
  // Display everything
  text("INSTRUCTIONS ", 300, 40);
  text("1. Click in this window", 300, 70);
  text("2. Enter String data ", 300, 100);  
  text("3. Hit Enter", 300, 130);
  
  text("Enter data: ", indent, 60);  
  text(typing,indent,90);
  text("Your input: ",indent, 120);
  text(saved,indent,150);  
  
  text("Next update in "+n+" seconds", indent, 200);
  fill(0, 0, 0);
  
  if(n<=0)
{      
  port.write(saved);   // Send Data
  lastTime = millis(); // Update last time of update
}
}

public void keyPressed() { 
  if(key == '\n') // When user hits enter
  {
    saved = typing;    // save the input
    typing = "";       // clear the input bar
  }
   else {
   
    typing = typing + key;  // update live user input (Backspace is also added here(any key is added))
  }
  if(key == BACKSPACE)
  {
    typing = typing.substring(0, typing.length()-2); // to remove character and the backspace
  }
  }
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "StringToSerial" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}

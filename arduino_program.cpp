#include <Servo.h>
#include <NewPing.h>
#include <SoftwareSerial.h>

Servo servo;
const int trigPin = 12;
const int echoPin = 13;

int distance;
int setRange;
int duration;
int detectedDistance;

const int RX_PIN = 0;
const int TX_PIN = 1;
SoftwareSerial serial(RX_PIN, TX_PIN);
char commandChar;


// SETUP


void setup() {
  pinMode(trigPin, OUTPUT);
  pinMode(echoPin, INPUT);
  
  servo.attach(9); 
  Serial.begin(9600);

  randomSeed(analogRead(0));
}


// LOOP


void loop() {

 for (int i=0; i<18; i++){
   servo.write(45);
   delay(150);
   servo.write(90);
   delay(500);

   distance = calculateDistance();

   if (distance < 200){
     detectedDistance = distance;

     if(Serial.available()){
       commandChar = Serial.read();
       switch(commandChar){
         case '*':
         Serial.print(detectedDistance);
         break;
       }  
     }
   }  
 }


 for (int i=0; i<18; i++){
   servo.write(135);
   delay(150);
   servo.write(90);
   delay(500);

   distance = calculateDistance();

   if (distance < 200){
     detectedDistance = distance;

     if(Serial.available()){
       commandChar = Serial.read();
       switch(commandChar){
         case '*':
         Serial.print(detectedDistance);
         break;
       }  
     }
   }
 }


    if(Serial.available()){
      commandChar = Serial.read();
      Serial.print(commandChar);
      switch(commandChar){
        case '*':
        Serial.print(random(1000) + "#");
        break;
      }  
    } 
}

int calculateDistance(){
  digitalWrite(trigPin, LOW);
  delayMicroseconds(2);
  digitalWrite(trigPin, HIGH);
  delayMicroseconds(10);
  digitalWrite(trigPin, LOW);

  duration = pulseIn(echoPin, HIGH);

  distance = duration/58;
  return distance;
}

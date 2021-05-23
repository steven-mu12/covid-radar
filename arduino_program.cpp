#include <Servo.h>
#include <NewPing.h>

Servo servo;
const int trigPin = 12;
const int echoPin = 13;

int distance;
int setRange;
int duration;


void setup() {
  pinMode(trigPin, OUTPUT);
  pinMode(echoPin, INPUT);
  servo.attach(9); 
  Serial.begin(9600);
}

void loop() {
  // servo code for the spin mechanism 

  for (int i=0; i<18; i++){
    servo.write(45);
    delay(150);
    servo.write(90);
    delay(500);

    distance = calculateDistance();
    Serial.print(distance);
  }
  
  for (int u=0; u<18; u++){
    servo.write(135);
    delay(150);
    servo.write(90);
    delay(500);

    distance = calculateDistance();
    Serial.print(distance);
  }
}

int calculateDistance(){
  digitalWrite(trigPin, LOW);
  delayMicroseconds(2);
  digitalWrite(trigPin, HIGH);
  delayMicroseconds(10);
  digitalWrite(trigPin, LOW);

  duration = pulseIn(echoPin, HIGH);

  distance = duration*0.034/2;
  return distance;
}

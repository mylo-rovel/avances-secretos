
#define outputPin 8

void setup() {
  pinMode(outputPin, OUTPUT);
  pinMode(LED_BUILTIN, OUTPUT);
}

// the loop function runs over and over again forever
void loop() {
  digitalWrite(outputPin, HIGH);   // turn the LED on (HIGH is the voltage level)
  digitalWrite(LED_BUILTIN, HIGH);
  delay(1000);                       // wait for a second
  digitalWrite(outputPin, LOW);    // turn the LED off by making the voltage LOW
  digitalWrite(LED_BUILTIN, LOW);
  delay(1000);                       // wait for a second
}

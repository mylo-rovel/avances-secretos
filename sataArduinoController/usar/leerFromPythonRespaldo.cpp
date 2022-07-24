#include <ArduinoJson.h>

DynamicJsonDocument doc(10240);

void setup() {
  Serial.begin(9600);
  while (!Serial) continue;
  Serial.setTimeout(1);
}
void loop() {
 while (!Serial.available());
 //deserializeJson(doc, json);
 String secuenciasJson = Serial.readString();
 Serial.print(secuenciasJson);

}
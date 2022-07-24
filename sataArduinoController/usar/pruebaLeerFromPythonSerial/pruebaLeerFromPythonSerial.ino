#include <ArduinoJson.h>

void setup() {
  Serial.begin(9600);
  while (!Serial) continue;
}
void loop() {
  while (!Serial.available());
  if (Serial.available()) {
    String secuenciasRecibidas = Serial.readString();
    StaticJsonDocument<768> doc; 
    DeserializationError error = deserializeJson(doc, secuenciasRecibidas);
    
    if (error) {
      Serial.print(F("deserializeJson() failed: "));
      Serial.print("#");
      Serial.print(error.f_str());
      return;
    }
    
    int valvulas = doc["valvulas"]; // 2
    
    for (JsonObject secuencias_1_item : doc["secuencias"]["1"].as<JsonArray>()) {    
      int secuencias_1_item_i = secuencias_1_item["i"]; // 30, 31, 32, 32, 32, 32, 32, 32, 32, 32, 32
      int secuencias_1_item_d = secuencias_1_item["d"]; // 20, 21, 22, 22, 22, 22, 22, 22, 22, 22, 22    
    }
    
    for (JsonObject secuencias_2_item : doc["secuencias"]["2"].as<JsonArray>()) {    
      int secuencias_2_item_i = secuencias_2_item["i"]; // 40, 41, 42, 42, 42, 42, 42, 42, 42, 42
      int secuencias_2_item_d = secuencias_2_item["d"]; // 30, 31, 32, 32, 32, 32, 32, 32, 32, 32    
    }
    Serial.print("SUCCESS"); 
  }
}

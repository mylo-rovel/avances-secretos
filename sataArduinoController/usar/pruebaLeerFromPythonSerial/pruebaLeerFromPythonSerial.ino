#include <ArduinoJson.h>

void setup() {
  Serial.begin(9600);
  while (!Serial) continue;
}
void loop() {
  while (!Serial.available());
  if (Serial.available()) {
    //deserializeJson(doc, json);
    String secuenciasRecibidas = Serial.readString();
    //String secuenciasRecibidas = Serial.readStringUntil( '\n' );
    const char* json = secuenciasRecibidas.c_str();
    //int inputLength = strlen(json);
    StaticJsonDocument<768> doc;
    Serial.print(secuenciasRecibidas);
    Serial.print("#####");
    Serial.print(json);
    Serial.print("#####");    
    DeserializationError error = deserializeJson(doc, secuenciasRecibidas);
    
    if (error) {
      Serial.print(F("deserializeJson() failed: "));
      Serial.print("#");
      Serial.print(error.f_str());
      return;
    }
    
    for (JsonObject root_1_item : doc["1"].as<JsonArray>()) {
    
      int root_1_item_intensidad = root_1_item["intensidad"]; // 30, 31, 32, 32, 32, 32, 32, 32, 32, 32, 32
      int root_1_item_duracion = root_1_item["duracion"]; // 20, 21, 22, 22, 22, 22, 22, 22, 22, 22, 22
    
    }
    
    for (JsonObject root_2_item : doc["2"].as<JsonArray>()) {
    
      int root_2_item_intensidad = root_2_item["intensidad"]; // 40, 41, 42, 42, 42, 42, 42, 42, 42, 42
      int root_2_item_duracion = root_2_item["duracion"]; // 30, 31, 32, 32, 32, 32, 32, 32, 32, 32
    
    }
    Serial.print("SUCCESS"); 
  }
}

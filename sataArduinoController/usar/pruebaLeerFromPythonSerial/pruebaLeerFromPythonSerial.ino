#include <ArduinoJson.h>

StaticJsonDocument<768> doc; 
String modoOperacion = "init";

void calibrateValvula(float caudalObjetivo) {
  
}

void executeSecuencias(){
    int cantValvulas = doc["ids"].size(); // 2
    
    for (int i = 0; i < cantValvulas; i++) {
      const char* currentIdValvula= doc["ids"][i];
      for (JsonObject secuencias_item : doc["secuencias"][currentIdValvula].as<JsonArray>()) {    
        int intensidadEvento = secuencias_item["i"]; // 30, 31, 32, 32, 32, 32, 32, 32, 32, 32, 32
        //int duracionEvento = secuencias_item["d"]; // 20, 21, 22, 22, 22, 22, 22, 22, 22, 22, 22
        Serial.print(intensidadEvento);
        Serial.print("#");
      }
      Serial.print("%%");
    }
    Serial.print("SUCCESS"); 
 }

void storeSecuencias() {
  String secuenciasRecibidas = Serial.readString();
  DeserializationError error = deserializeJson(doc, secuenciasRecibidas);
  
  if (error) {
    Serial.print(F("deserializeJson() failed: "));
    Serial.print("#");
    Serial.print(error.f_str());
    return;
  }
  modoOperacion = "execute";
}

volatile int x;
void setup() {
  Serial.begin(9600);
  while (!Serial) continue;
}

void loop() {
  while (!Serial.available());
  if (Serial.available()) {
    String mensajeFromRaspi = Serial.readString();
    Serial.print(++x);
  }
}

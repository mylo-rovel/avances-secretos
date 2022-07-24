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

{
    int     size_ = 0;
  while ( !Serial.available()  ){}
  //if ( Serial.available() ){}
  String payload = Serial.readString();
  const char* json = payload.c_str();
  StaticJsonDocument<512> doc;
  String secuenciasJson = Serial.readString();
  
  DeserializationError error = deserializeJson(doc, json);
  if (error) {
    Serial.print(secuenciasJson);
    //Serial.print("error.c_str()"); 
  }
  else{
    Serial.print("success");
  }
  delay(20);
}


{
  
  DeserializationError error = deserializeJson(doc, json, inputLength);
  
  if (error) {
    Serial.print(F("deserializeJson() failed: "));
    Serial.println(error.f_str());
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
  Serial.print("Success"); 
}
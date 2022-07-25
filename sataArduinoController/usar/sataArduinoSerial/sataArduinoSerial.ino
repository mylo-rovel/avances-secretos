#include <ArduinoJson.h>

volatile int NumPulsos; //variable para la cantidad de pulsos recibidos
int PinSensor = 2;    //Sensor conectado en el pin 2
float factor_conversion=7.5; //para convertir de frecuencia a caudal

//---Función que se ejecuta en interrupción---------------
void ContarPulsos (){ 
  NumPulsos++;  //incrementamos la variable de pulsos
} 

//---Función para obtener frecuencia de los pulsos--------
float ObtenerFrecuencia(){
  float frecuencia;
  NumPulsos = 0;   //Ponemos a 0 el número de pulsos
  interrupts();    //Habilitamos las interrupciones
  delay(1000);   //muestra de 1 segundo
  noInterrupts(); //Desabilitamos las interrupciones
  interrupts();    //Habilitamos las interrupciones
  frecuencia = NumPulsos; //Hz(pulsos por segundo)
  return frecuencia;
}




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

//volatile int x;
void setup() {
  Serial.begin(9600);
  while (!Serial) continue;
  pinMode(PinSensor, INPUT); 
  attachInterrupt(0,ContarPulsos,RISING); //(Interrupcion 0(Pin2),funcion,Flanco de subida)
}

void loop() {
  while (!Serial.available());
  if (Serial.available()) {
    String secuenciasRecibidas = Serial.readString();
    float frecuencia = ObtenerFrecuencia(); //obtenemos la Frecuencia de los pulsos en Hz
    float caudal_L_min = frecuencia/factor_conversion; //calculamos el caudal en L/min
    //-----Enviamos por el puerto serie---------------
    Serial.print(caudal_L_min);
  }
}

#include <ArduinoJson.h>
StaticJsonDocument<768> doc; 

//---Variables globales para el uso del flujometro
int PinSensor = 2;    //Sensor conectado en el pin 2
volatile int NumPulsos; //variable para la cantidad de pulsos recibidos
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
  interrupts();    //Habilitamos las interrupciones para poder usar Serial.print()
  frecuencia = NumPulsos; //Hz(pulsos por segundo)
  return frecuencia;
}

//---Función para obtener caudal usando la frecuencia de los pulsos y enviarla al boardRaspberry--------
void sendCaudalToSataBoard() {
  float frecuencia = ObtenerFrecuencia(); //obtenemos la Frecuencia de los pulsos en Hz
  float caudal_L_min = frecuencia/factor_conversion; //calculamos el caudal en L/min
  Serial.print(caudal_L_min);
}

//---Funcion para mover la válvula segun el caudal que se debería obtener
void calibrateValvula(float caudalObjetivo, float caudalActual) {
  //if (caudalObjetivo > caudalActual + delta
}

//---Funcion para iniciar el proceso de simulacion
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


void setup() {
  Serial.begin(9600);
  while (!Serial) continue;
  pinMode(PinSensor, INPUT); 
  attachInterrupt(0,ContarPulsos,RISING); //(Interrupcion 0(Pin2),funcion,Flanco de subida)
}

void loop() {
  while (!Serial.available());
  if (Serial.available()) {
    String mensajeRecibido = Serial.readString();
    Serial.print(mensajeRecibido);
    Serial.print("   ");
    if (mensajeRecibido == "sendCaudalToSataBoard") {
      // acá hacer lo de abrir y cerrar valvulas, y enviar caudal de vuelta al sataBoard
      // habilitar interrupciones solo cuand enviemos informacion y calculemos la frecuencia?
      // hashmap con las ids como key y una variable que cuente las iteraciones de cada jsonarray de secuencias
      Serial.print("EXECUTING");
      int cantValvulas = doc["ids"].size(); // 2
      Serial.print(cantValvulas);
    }
    else {
      String resultSavingJSON = "";
      DeserializationError error = deserializeJson(doc, mensajeRecibido);
      if (error) {
        Serial.print(F("deserializeJson() failed: "));
        Serial.print("#");
        Serial.print(error.f_str());
        Serial.print("failedSaving");
        return;
      }  
      Serial.print("JSON SETUP FINISHED");
    }
  }
}

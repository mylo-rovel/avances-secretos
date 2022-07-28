#include <ArduinoJson.h>
StaticJsonDocument<768> doc; 

//---Variables globales para el uso del flujometro
int PinSensor = 2;    //Sensor conectado en el pin 2
volatile int NumPulsos; //variable para la cantidad de pulsos recibidos
float factor_conversion=7.5; //para convertir de frecuencia a caudal

//---Variables globales para el manejo de las valvulas
int cantValvulas = 0;
int indicesValvula[5]; // estos indices iran desde 0 hasta n, con cada n = cantEventos de cada valv
long currentTiemposInicioEvento[5]; // tiemposInicio de cada evento para saber si ya expiro eventoActual


//---Función que se ejecuta en interrupción---------------
void ContarPulsos (){ 
  NumPulsos++;  //incrementamos la variable de pulsos
} 

//---Función para obtener frecuencia de los pulsos--------
float obtenerFrecuencia(){
  float frecuencia;
  NumPulsos = 0;   //Ponemos a 0 el número de pulsos
  interrupts();    //Habilitamos las interrupciones
  delay(1000);   //muestra de 1 segundo
  noInterrupts(); //Desabilitamos las interrupciones
  interrupts();    //Habilitamos las interrupciones para poder usar Serial.print()
  frecuencia = NumPulsos; //Hz(pulsos por segundo)
  frecuencia = random(30,51);
  return frecuencia;
}

//OBTENER EL CAUDAL EN MILILITROS POR MINUTO
float obtenerCaudalActual() {
  float frecuencia = obtenerFrecuencia(); //obtenemos la Frecuencia de los pulsos en Hz
  float caudal_L_min = frecuencia/factor_conversion; //calculamos el caudal en L/min
  return caudal_L_min*1000;
}

//---Funcion para mover la válvula segun el caudal que se debería obtener
void calibrateValvula(float caudalObjetivo, float caudalActual) {
    
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
    if (mensajeRecibido == "sendCaudalToSataBoard") {
      float caudalActual = obtenerCaudalActual();
      Serial.print(caudalActual);
      // LA IDEA ES ITERAR SOBRE UNA LISTA DE INDICES (3 VALVULAS, 3 INDICES)
      // CHEQUEANDO DURACIONES VS TIEMPOS TRANSCURRIDOS (PARA CAMBIAR LA INTENSIDAD SI ES NECESARIO)
      for (int i = 0; i < cantValvulas; i++) {
        // REVISAR SI CONVIENE DECLARAR ESTAS VARIABLES EN OTRO SCOPE PARA MEJORAR PERFORMANCE
        char* currentIdValvula = doc["ids"][i];
        int currentIndexValvula = indicesValvula[i];
        int minsDuracionEventoActual = doc["secuencias"][currentIdValvula][currentIndexValvula]["i"];
        long duracionEventoActual = duracionEventoActual * 60 * 1000;
        long tiempoTranscurridosEvento = currentTiemposInicioEvento[i];
        
        // PASAR AL EVENTO SIGUIENTE SI YA SE SUPERO LA DURACION DEL EVENTO
        if ( (millis() - tiempoTranscurridosEvento) >= duracionEventoActual) {
          indicesValvula[i]++;
          currentTiemposInicioEvento[i] = millis();
          int intensidadNuevoEvento = doc["secuencias"][currentIdValvula][currentIndexValvula+1]["i"];
          calibrateValvula(intensidadNuevoEvento, caudalActual);
        }
        // SI NO SE PASA A OTRO EVENTO, CHEQUEAR SI LA VALVULA ESTÁ DENTRO DEL RANGO DE LA INTENSIDAD
        else {
          int intensidadEventoActual = doc["secuencias"][currentIdValvula][currentIndexValvula]["i"];
          calibrateValvula(intensidadEventoActual, caudalActual);
        }

      }
    }
    
    // ---------------------- PARTE SÓLO EJECUTADA AL RECEBIR EL JSON ------------------------
    else {
      // REALIZANDO EL SETUP Y GUARDADO DE LAS SECUENCIAS EN "doc" PARA LA POSTERIOR EJECUCION
      DeserializationError error = deserializeJson(doc, mensajeRecibido);
      if (error) {
        Serial.print(F("deserializeJson() failed: #"));
        Serial.print(error.f_str());
        return;
      }  
      Serial.print("JSON SETUP FINISHED");
      cantValvulas = doc["ids"].size();
      
      // INICIALIZANDO LOS VALORES AUXILIARES A USAR EN LA ITERACION PRINCIPAL
      for (int i = 0; i < cantValvulas; i++) { 
        indicesValvula[i] = 0; 
        currentTiemposInicioEvento[i] = millis();
      }
    }
  }
}

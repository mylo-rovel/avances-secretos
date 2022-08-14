#include <ArduinoJson.h>
//---Variable global para el manejo del input JSON
StaticJsonDocument<768> doc; 

//---Variables globales para el uso del flujometro
int PinSensor = 2;    //Sensor conectado en el pin 2
volatile int NumPulsos; //variable para la cantidad de pulsos recibidos
float factor_conversion=7.5; //para convertir de frecuencia a caudal
// VALORES OBTENIDOS AL CREAR UN INTERVALO DE CONFIANZA DEL 95% con 575 muestras del caudal sin variar algun parametro
float caudalMaximoMedido = 465.3901;
float diferenciaCotaSupInf = 7.2738;

//---Variables globales para el manejo de las valvulas
const int R1 = 13;
const int R2 = 12;
int cantValvulas = 0;
// ACTUALMENTE SON 5 PORQUE NO REINICIALIZAMOS EL ARRAY => NO CONOCEMOS LA CANTIDAD DE VALVULAS
// TENEMOS DOS ARRAYS QUE MANEJAN HASTA 5 VALVULAS => SI SE NECESITAN MAS, AUMENTAR SU VALOR
int indicesEventoValvula[5]; // estos indices iran desde 0 hasta n, con cada n = cantEventos de cada valv
long currentTiemposInicioEvento[5]; // tiemposInicio de cada evento para saber si ya expiro eventoActual

long tiempoTranscurridoGlobal;
long duracionTotalSecuenciaLarga;


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




// --- SECCION PARA MANIPULAR LA VALVULA ----------------------------------------------
void inmovilizarValvula(){
    // ambos en HIGH implica que las valvulas no se moveran
    digitalWrite(R1, HIGH);
    digitalWrite(R2, HIGH);
}
void abrirValvula() {
    digitalWrite(R1, HIGH); //cerrado = 1
    digitalWrite(R2, LOW); // abierta = 0
}
void cerrarValvula() {
    digitalWrite(R1, LOW); //abierta = 0
    digitalWrite(R2, HIGH); //cerrado = 1
}
//---Funcion para mover la válvula segun el caudal que se debería obtener
void calibrateValvula(float intensidadObjetivo, float caudalActual) {
  float caudalObjetivo = (intensidadObjetivo/100)*caudalMaximoMedido;
  float cotaInferior = caudalObjetivo - diferenciaCotaSupInf;
  float cotaSuperior = caudalObjetivo + diferenciaCotaSupInf;
  
  //SI EL CAUDAL ACTUAL ESTA EN EL INTERVALO ACEPTABLE => QUE NO SE MUEVA
  // if (cotaInferior <= caudalActual && caudalActual <= cotaSuperior) {
  //   inmovilizarValvula();
  // }
  // // SI NO ESTÁ EN EL INTERVALO ACEPTABLE => MOVER LA VALVULA
  // else {
  //   // EL CAUDAL OBJETIVO ES SIGNFICATIVAMENTE MAYOR AL CAUDAL ACTUAL => ABRIR VALVULA
  //   if (caudalObjetivo > caudalActual) {
  //     abrirValvula();
  //   }
  //   else {
  //     cerrarValvula();
  //   }
  // }

  // TEMPORAL PARA TESTEAR SI FUNCIONA EL ABRIR Y CERRAR VALVULA
  if (intensidadObjetivo >= 50) {
    abrirValvula();
  }
  else {
    cerrarValvula();
  }
}
// --- SECCION PARA MANIPULAR LA VALVULA -----------------------------------------------


void setup() {
  Serial.begin(9600);
  while (!Serial) continue;
  pinMode(PinSensor, INPUT); 
  attachInterrupt(0,ContarPulsos,RISING); // Interrupcion 0 (Pin2), funcion, Flanco de subida

  // se necesitan 2 reles para abrir y cerrar 1 valvula
  // HIGH => desenergizar el rele
  // LOW => energizar el rele
  pinMode(R1, OUTPUT);
  pinMode(R2, OUTPUT);

  //dar la orden de cerrar la valvula y esperar hasta que esté completo
  cerrarValvula();
  delay(9000);
}

void loop() {

  while (!Serial.available());
  if (Serial.available()) {
    String mensajeRecibido = Serial.readString();
    
    // Este bloque siempre es ejecutado DESPUÉS de recibir las secuencias JSON desde la raspberry
    if (mensajeRecibido == "sendCaudalToSataBoard") {
      // OBTENER EL CAUDAL EN MILILITROS POR MINUTO
      float caudalActual = obtenerCaudalActual();
      
      
      // TERMINO DEL PROCESO
      // PARA SABER SI LA SIMULACION TERMINO, HAY QUE COMPARAR EL TIEMPO TRANSCURRIDO
      // vs LA DURACION TOTAL DE LA SIMULACION (COMBINACION ENTRE LAS SECUENCIAS)
      // usar las variables:
      // long tiempoTranscurridoGlobal;
      // long duracionTotalSecuenciaLarga;



      // if (indiceEventoValvula >= doc["secuencias"][idValvula].size()) {
      //   // SI ENVIAMOS UN VALOR NEGATIVO, LA RASPBERRY SABRA QUE LA EJECUCION FINALIZO
      //   Serial.print(-2);
      //   cerrarValvula();
      //   delay(9000); // tiempo que tarda la valvula en cerrarse desde 100% abierto
      //   return;
      // }


      Serial.print(caudalActual);

      // LA IDEA ES ITERAR SOBRE UNA LISTA DE INDICES (n VALVULAS => n INDICES)
      // BUSCAMOS CHEQUEAR DURACIONES VS TIEMPOS TRANSCURRIDOS 
      // (PARA CAMBIAR AL SIGUIENTE EVENTO SI HA PASADO SUFICIENTE TIEMPO)
      for (int i = 0; i < cantValvulas; i++) {
        String currentIdValvula = doc["ids"][i]; //ESTO ES NECESARIO PARA CHEQUEAR CADA VALVULA
        int currentIndexEventoValvula = indicesEventoValvula[i];

        // SI SE ACABARON LOS EVENTOS PARA UNA VALVULA, CONTINUAR CON LA SIGUIENTE VALVULA
        if (currentIndexEventoValvula >= doc["secuencias"][currentIdValvula].size()) {
          continue;
        }

        long tiempoTranscurridosEvento = currentTiemposInicioEvento[i]; // en milisegundos
        int minsDuracionEventoActual = doc["secuencias"][currentIdValvula][currentIndexEventoValvula]["i"];
        // duracion en milisegundos => mins * 60min * 1000 ms => milisegundos
        long duracionEventoActual = minsDuracionEventoActual * 60 * 1000;
        
        // PASAR AL EVENTO SIGUIENTE SI YA SE SUPERO LA DURACION DEL EVENTO
        if ( (millis() - tiempoTranscurridosEvento) >= duracionEventoActual) {
          // PASAR AL SIGUIENTE EVENTO Y REINICIAR EL TIEMPO TRANSCURRIDO DESDE EL INICIO DEL NUEVO EVENTO
          indicesEventoValvula[i]++; 
          currentTiemposInicioEvento[i] = millis(); // reiniciar el tiempo transcurrido por el nuevo evento
          // OBTENER EL VALOR QUE DEBEMOS TENER COMO OBJETIVO
          int intensidadNuevoEvento = doc["secuencias"][currentIdValvula][currentIndexEventoValvula+1]["i"];
          // MOVER LA VALVULA AL NUEVO OBJETIVO
          calibrateValvula(intensidadNuevoEvento, caudalActual);
        }

        // SI NO SE PASA A OTRO EVENTO, CHEQUEAR SI LA VALVULA ESTÁ DENTRO DEL RANGO DE LA INTENSIDAD
        else {
          // OBTENER EL VALOR QUE DEBEMOS TENER COMO OBJETIVO
          int intensidadEventoActual = doc["secuencias"][currentIdValvula][currentIndexEventoValvula]["i"];
          calibrateValvula(intensidadEventoActual, caudalActual);
        }
      }
    }
    
    // ------------ PARTE EJECUTADA SÓLO AL RECIBIR EL JSON (COMIENZO SIMULACION) -----------------
    else {
      // REALIZANDO EL SETUP Y GUARDADO DE LAS SECUENCIAS EN "doc" PARA LA POSTERIOR EJECUCION
      // "doc" esta en las primeras lineas
      DeserializationError error = deserializeJson(doc, mensajeRecibido);
      if (error) {
        Serial.print(F("deserializeJson() failed: #"));
        Serial.print(error.f_str());
        return; // NO CONTINUAR SI HUBO UN ERROR
      }
      
      cantValvulas = doc["ids"].size(); // VALOR USADO EN 'if (mensajeRecibido == "sendCaudalToSataBoard")'
      
      // INICIALIZANDO LOS VALORES AUXILIARES A USAR EN LA ITERACION PRINCIPAL
      // ALSO: REINICIANDO VALORES EN CASO DE ENVIAR OTRA SIMULACION AL FINALIZAR UNA
      for (int i = 0; i < cantValvulas; i++) { 
        indicesEventoValvula[i] = 0; 
        currentTiemposInicioEvento[i] = millis();
      }

      Serial.print("JSON SETUP FINISHED");
    }
  }
}

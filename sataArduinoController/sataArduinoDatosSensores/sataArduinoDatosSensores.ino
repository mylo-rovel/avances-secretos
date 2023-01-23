#include <SHT21.h>  // include SHT21 library

SHT21 sht;

float t; 	// variable to store temperature
float h; // variable to store hemidity


volatile int NumPulsos; //variable para la cantidad de pulsos recibidos por el flujometro
volatile int NumPulsosPluv;//variable para la cantidad de pulsos recibidos por el pluviometro
int PinSensor = 2;    //Sensor conectado en el pin 2
int PinSensorP = 3;   //Pluviometro conectado en el pin 3
float factor_conversion=7.5; //para convertir de frecuencia a caudal
float factor_conversionP = 0.27945; //para convertir de mm de lluvia a litros de agua por metro cuadrado
float volumen=0;
float volumenPluv=0;
long dt=0; //variación de tiempo por cada bucle
long t0=0; //millis() del bucle anterior
long dt2=0; //variación de tiempo por cada bucle pluv
long t02=0; //millis() del bucle anterior pluv
//---Función que se ejecuta en interrupción---------------
void ContarPulsos ()
{
  NumPulsos++;   //incrementamos la variable de pulsos
}
void ContarPulsos2 ()
{
  NumPulsosPluv++;  //incrementamos la variable de pulsos
}

//---Función para obtener frecuencia de los pulsos--------
int ObtenerFrecuencia()
{
  int frecuencia;
  NumPulsos = 0;   //Ponemos a 0 el número de pulsos
  interrupts();    //Habilitamos las interrupciones
  delay(1000);   //muestra de 1 segundo
  noInterrupts(); //Desabilitamos las interrupciones
  interrupts();    //Habilitamos las interrupciones
  frecuencia=NumPulsos; //Hz(pulsos por segundo)
  return frecuencia;
}
//---Función para obtener frecuencia de los pulsos del pluviometro--------
int ObtenerFrecuencia2()
{
  int frecuencia2;
  NumPulsosPluv = 0;   //Ponemos a 0 el número de pulsos
  interrupts();    //Habilitamos las interrupciones
  delay(1000);   //muestra de 1 segundo
  noInterrupts(); //Desabilitamos las interrupciones
  interrupts();    //Habilitamos las interrupciones
  frecuencia2=NumPulsosPluv; //Hz(pulsos por segundo)
  return frecuencia2;
}

void setup()
{
  Wire.begin();		// begin Wire(I2C)
  Serial.begin(9600);
  delay(500);//Delay to let system boot
  delay(1000);//Wait before accessing Sensor
  // dht.begin();
  pinMode(PinSensor, INPUT);
  pinMode(PinSensorP, INPUT);
  // attachInterrupt(0,ContarPulsos,RISING); //(Interrupcion 0(Pin2),funcion,Flanco de subida)
  // attachInterrupt(1,ContarPulsos2,RISING); //(Interrupcion 1(Pin3),funcion,Flanco de subida)
  attachInterrupt(digitalPinToInterrupt(PinSensor),ContarPulsos,RISING); //(Interrupcion 0(Pin2),funcion,Flanco de subida)
  attachInterrupt(digitalPinToInterrupt(PinSensorP),ContarPulsos2,RISING); //(Interrupcion 1(Pin3),funcion,Flanco de subida)
  //Serial.println ("Envie 'r' para restablecer el volumen a 0 Litros");
  t0=millis();
  t02=millis();
}

void loop ()
{
  t = sht.getTemperature();  // get tempeture from SHT
  h = sht.getHumidity(); // get humidity from SHT


  // if (Serial.available()) {
  //   if(Serial.read()=='r')volumen=0;//restablecemos el volumen si recibimos 'r'
  //   if(Serial.read()=='r2')volumenPluv=0;//restablecemos el volumen si recibimos 'r'
  //   Serial.print("aaaaaaaabbbbbbbbbbcccccccc");
  // }
  float frecuencia=ObtenerFrecuencia(); //obtenemos la Frecuencia de los pulsos en Hz
  float caudal_L_m=frecuencia/factor_conversion; //calculamos el caudal en L/m
  float caudal_L_h=caudal_L_m*60; //calculamos el caudal en L/h
  dt=millis()-t0; //calculamos la variación de tiempo
  t0=millis();
  volumen=volumen+(caudal_L_m/60)*(dt/1000); // volumen(L)=caudal(L/s)*tiempo(s)


  int sensorVal=analogRead(A2);
//Serial.print(" Sensor Value: ");
//Serial.print(sensorVal);
  float voltage = (sensorVal*5.0)/1024.0;
    //Serial.print("Volts: ");
    //Serial.print(voltage);

  float pressure_pascal = (3.0*((float)voltage-0.47))*1000000.0;
  float pressure_bar = (pressure_pascal/10e5) - 0.10;

  float frecuencia2=ObtenerFrecuencia2(); //obtenemos la Frecuencia del pluviometro de los pulsos en Hz
  float agua_caida_L_m2=frecuencia2*factor_conversionP; //calculamos el agua caida en L/m2
  dt2=millis()-t02; //calculamos la variación de tiempo
  t02=millis();
  volumenPluv=volumenPluv+(agua_caida_L_m2/60)*(dt2/1000); // volumen(L)=caudal(L/s)*tiempo(s)

// En este apartado transformamos las variables en string para luego concatenarlas y dejar un solo msje, eventualmente con .split en python separamos los valores.
  while (!Serial.available());
  if (Serial.available()) {
    String mensajeFromRaspi = Serial.readString();
    String strH = (String) h;
    String strT = (String) t;
    String strC = (String) caudal_L_m;
    String strV = (String) volumen;
    String strVPluv = (String) volumenPluv;
    String strP = (String) pressure_bar;
    String strA = (String) agua_caida_L_m2;
    Serial.print(strH + "" + strT + "" + strC + "" + strV + "" + strVPluv + "" + strP + "" + strA);
  }

  delay(2000);

}
const int R1 = 13;
const int R2 = 12;

void setup(){
    Serial.begin(9600);

    pinMode(R1, OUTPUT);
    digitalWrite(R1, HIGH); //cerrado
    pinMode(R2, OUTPUT);
    digitalWrite(R2, HIGH); //cerrado
}

void loop(){
    if(Serial.available() > 0){
        String dato = Serial.readStringUntil('\n');

        if(dato == "abrir"){
            digitalWrite(R1, HIGH); //cerrado = 1
            digitalWrite(R2, LOW); // abierta = 0
            Serial.write("válvula abierta\n");
        }else if(dato == "cerrar"){
            digitalWrite(R1, LOW); //abierta = 0
            digitalWrite(R2, HIGH); //cerrado = 1
            Serial.write("vávula cerrada\n");
        }else{
            Serial.write("Comando incorrecto\n");
        }
    }
    delay(100);
}
# import RPi.GPIO as GPIO # Import Raspberry Pi GPIO library
# from time import sleep # Import the sleep function from the time module

# PIN 8 => AMARILLO
# PIN 10 => AZUL
# PIN 12 => VERDE
        

# ESTAMOS IMPORTANDO LAS LIBRERIAS DENTRO DE LAS FUNCIONES PARA QUE SEA MÁS FÁCIL LA 
# IMPLEMENTACION DE PRUEBA => SIMPLEMENTE INSTALAR BIBLIOTECAS Y CORRER
repeticiones = 3

def rutina_secuencia1():
    import RPi.GPIO as GPIO # Import Raspberry Pi GPIO library
    from time import sleep # Import the sleep function from the time module
    pinUsar = 8
    GPIO.setwarnings(False) # Ignore warning for now
    GPIO.setmode(GPIO.BOARD) # Use physical pin numbering
    GPIO.setup(pinUsar, GPIO.OUT, initial=GPIO.LOW) # Set pin 8 to be an output pin and set initial value to low (off)
    for x in range(repeticiones):
        GPIO.output(pinUsar, GPIO.HIGH) # Turn on
        sleep(2) # Sleep for 1 second
        GPIO.output(pinUsar, GPIO.LOW) # Turn off
        sleep(1) # Sleep for 1 second

def rutina_secuencia2():
    import RPi.GPIO as GPIO # Import Raspberry Pi GPIO library
    from time import sleep # Import the sleep function from the time module
    pinUsar = 10
    GPIO.setwarnings(False) # Ignore warning for now
    GPIO.setmode(GPIO.BOARD) # Use physical pin numbering
    GPIO.setup(pinUsar, GPIO.OUT, initial=GPIO.LOW) # Set pin 8 to be an output pin and set initial value to low (off)
    for x in range(repeticiones):
        GPIO.output(pinUsar, GPIO.HIGH) # Turn on
        sleep(2) # Sleep for 1 second
        GPIO.output(pinUsar, GPIO.LOW) # Turn off
        sleep(1) # Sleep for 1 second

def rutina_secuencia3():
    import RPi.GPIO as GPIO # Import Raspberry Pi GPIO library
    from time import sleep # Import the sleep function from the time module
    pinUsar = 12
    GPIO.setwarnings(False) # Ignore warning for now
    GPIO.setmode(GPIO.BOARD) # Use physical pin numbering
    GPIO.setup(pinUsar, GPIO.OUT, initial=GPIO.LOW) # Set pin 8 to be an output pin and set initial value to low (off)
    for x in range(repeticiones):
        GPIO.output(pinUsar, GPIO.HIGH) # Turn on
        sleep(2) # Sleep for 1 second
        GPIO.output(pinUsar, GPIO.LOW) # Turn off
        sleep(1) # Sleep for 1 second

# rutinas = [rutina_secuencia1, rutina_secuencia2, rutina_secuencia3]
# i = 0
# while True:
# 	rutinas[i]()
# 	i +=1
# 	i= i%3


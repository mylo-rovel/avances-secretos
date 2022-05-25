import RPi.GPIO as GPIO
from time import sleep

GPIO.setwarnings(False)
GPIO.setmode(GPIO.BOARD)
GPIO.setup(7, GPIO.OUT, initial=GPIO.LOW)

while True:
	GPIO.output(7, GPIO.HIGH)
	print("encendido")
	sleep(1)
	GPIO.output(7, GPIO.LOW)
	print("apagando")
	sleep(1)